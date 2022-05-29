package tireshop.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tireshop.entity.Employee;
import tireshop.repo.EmployeeRepository;

import java.util.*;

@Slf4j
@Service
public class EmployeeUserDetailsService implements UserDetailsService {
    private final EmployeeRepository employeeRepository;

    public EmployeeUserDetailsService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Reached loadUserByUsername");
        final Optional<Employee> employeeOptional = employeeRepository.findEmployeeByUserName(username);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            log.info("found employee by username '{}'", username);
            List<GrantedAuthority> roles = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
            if (null == employee.getFullName()) {
                roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }
            return new UserImpl(employee.getUserName(), employee.getHashedPass(), employee.getFullName(), roles);
        } else {
            throw new UsernameNotFoundException("Invalid Username / Password");
        }
    }
}
