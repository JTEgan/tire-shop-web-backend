package jonegan.rmmservicesserverapp.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Data
@Entity
public class Device {
    @Id
    String id;
    String systemName;
    DeviceType deviceType;
    @ManyToOne
    Customer customer;
}
