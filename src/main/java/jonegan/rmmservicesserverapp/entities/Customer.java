package jonegan.rmmservicesserverapp.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Data
@Entity
public class Customer {
    @Id
    String id;
    String hashedPass;

    @OneToMany(fetch = FetchType.LAZY)
    Set<Service> subscribedServices;
}
