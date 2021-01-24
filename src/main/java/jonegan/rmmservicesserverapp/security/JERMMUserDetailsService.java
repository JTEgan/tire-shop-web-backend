package jonegan.rmmservicesserverapp.security;

import jonegan.rmmservicesserverapp.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
public class JERMMUserDetailsService implements UserDetailsService {
    private final CustomerRepository customerRepository;

    public JERMMUserDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return new User(username, SecurityUtil.hashPassword("rawPass"), Collections.singleton(new SimpleGrantedAuthority("ROLE_CUSTOMER")));
        log.info("Reached loadUserByUsername");
        return customerRepository.findById(username)
                .map(customer -> {
                    log.info("found user in loadUserByUsername");
                    return new User(customer.getId(), customer.getHashedPass(), Collections.singleton(new SimpleGrantedAuthority("ROLE_CUSTOMER")));
                })
                .orElse(new User(username, SecurityUtil.hashPassword("rawPass"), Collections.singleton(new SimpleGrantedAuthority("ROLE_NON_CUST_USER"))));
    }
}
