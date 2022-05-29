package tireshop.security;

//import jonegan.rmmservicesserverapp.security.SecurityUtil;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tireshop.entity.Employee;
import tireshop.repo.EmployeeRepository;

import java.util.Optional;

@RestController
public class SignUpController {
    private final EmployeeRepository employeeRepository;

    public SignUpController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<SignUpRequest> setupNewUser(@RequestBody SignUpRequest request) {
        System.out.println("Setting up a new User");
        final Optional<Employee> existing = employeeRepository.findEmployeeByUserName(request.getUsername());
        if (existing.isPresent()) {
            return new ResponseEntity<>(request, HttpStatus.CONFLICT);
        }
        Employee employee = new Employee();
        employee.setUserName(request.getUsername());
        employee.setFullName(request.getFullName());
        employee.setHashedPass(SecurityUtil.hashPassword(request.getPassword()));
        employeeRepository.save(employee);
        return new ResponseEntity<>(request, HttpStatus.CREATED);
    }
}
