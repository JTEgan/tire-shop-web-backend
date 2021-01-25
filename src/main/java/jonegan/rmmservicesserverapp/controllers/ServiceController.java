package jonegan.rmmservicesserverapp.controllers;

import jonegan.rmmservicesserverapp.entities.Customer;
import jonegan.rmmservicesserverapp.entities.Service;
import jonegan.rmmservicesserverapp.repositories.CustomerRepository;
import jonegan.rmmservicesserverapp.repositories.ServiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import static jonegan.rmmservicesserverapp.security.SecurityUtil.validCustomerUser;

@Slf4j
@RestController
public class ServiceController {
    private final ServiceRepository serviceRepository;
    private final CustomerRepository customerRepository;

    public ServiceController(ServiceRepository serviceRepository, CustomerRepository customerRepository) {
        this.serviceRepository = serviceRepository;
        this.customerRepository = customerRepository;
    }

    @GetMapping("/services")
    public ResponseEntity<List<Service>> getAllOfferedServices() {
        List<Service> allOfferedServices = serviceRepository.findAll();
        return new ResponseEntity<>(allOfferedServices, HttpStatus.OK);
    }

    @GetMapping("/customers/{customerId}/services")
    public ResponseEntity<Set<Service>> getSubscribedServices(Principal principal, @PathVariable String customerId) {
        if (validCustomerUser(principal, customerId)) {
            Set<Service> subscribedServices = customerRepository.findById(customerId).orElseThrow(IllegalArgumentException::new)
                    .getSubscribedServices();
            return new ResponseEntity<>(subscribedServices, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/customers/{customerId}/services/{serviceId}")
    public ResponseEntity<Service> addService(Principal principal, @PathVariable String customerId, @PathVariable String serviceId) {
        if (validCustomerUser(principal, customerId) && validService(serviceId)) {
            Customer customer = customerRepository.findById(customerId).orElseThrow(IllegalArgumentException::new);
            Set<Service> subscribedServices = customer.getSubscribedServices();
            Service serviceToAdd = serviceRepository.findById(serviceId).orElseThrow(IllegalArgumentException::new);

            if (subscribedServices.add(serviceToAdd)) {
                customerRepository.save(customer);
                return new ResponseEntity<>(serviceToAdd, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping(value = "/customers/{customerId}/services/{serviceId}")
    public ResponseEntity<Service> removeService(Principal principal, @PathVariable String customerId, @PathVariable String serviceId) {
        if (validCustomerUser(principal, customerId)) {
            Customer customer = customerRepository.findById(customerId).orElseThrow(IllegalArgumentException::new);
            Set<Service> subscribedServices = customer.getSubscribedServices();
            Service serviceToRemove = serviceRepository.findById(serviceId).orElseThrow(IllegalArgumentException::new);
            if (subscribedServices.remove(serviceToRemove)) {
                customerRepository.save(customer);
                return new ResponseEntity<>(serviceToRemove, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    private boolean validService(String serviceId) {
        return serviceRepository.existsById(serviceId);
    }
}
