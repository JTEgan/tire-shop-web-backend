package jonegan.rmmservicesserverapp.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;

@Data
@Entity
public class Service {
    @Id
    String id;
    String serviceName;
    @ManyToMany(targetEntity = Customer.class, mappedBy = "subscribedServices")
    Set<Customer> customers;
}
