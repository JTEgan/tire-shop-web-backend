package jonegan.rmmservicesserverapp.entities;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
public class Device {
    @Id
    String id;
    String systemName;
    @Enumerated(EnumType.STRING)
    DeviceType deviceType;
    @ManyToOne
    Customer customer;
}
