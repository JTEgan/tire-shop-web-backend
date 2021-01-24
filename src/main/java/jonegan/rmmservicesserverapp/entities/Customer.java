package jonegan.rmmservicesserverapp.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Set;

@Data
@Entity
public class Customer {
    @Id
    String id;
    String hashedPass;

    @ManyToMany(targetEntity = Service.class)
    Set<Service> subscribedServices;
}
