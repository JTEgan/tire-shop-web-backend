package jonegan.rmmservicesserverapp.controllers;

import jonegan.rmmservicesserverapp.billing.Bill;
import jonegan.rmmservicesserverapp.billing.BillingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static jonegan.rmmservicesserverapp.security.SecurityUtil.validCustomerUser;

@RestController
public class CustomerController {
    private final BillingService billingService;

    public CustomerController(BillingService billingService) {
        this.billingService = billingService;
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<Bill> getBill(Principal principal, @PathVariable String customerId) {
        if (validCustomerUser(principal, customerId)) {
            return new ResponseEntity<>(billingService.calculateBill(customerId), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
