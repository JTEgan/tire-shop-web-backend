package jonegan.rmmservicesserverapp.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class Customer {
    @Id
    String id;
    String hashedPass;

    @ManyToMany(targetEntity = Service.class, fetch = FetchType.LAZY)
    Set<Service> subscribedServices;
}
