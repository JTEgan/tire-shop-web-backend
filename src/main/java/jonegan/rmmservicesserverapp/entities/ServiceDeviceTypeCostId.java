package jonegan.rmmservicesserverapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ServiceDeviceTypeCostId implements Serializable {
    String serviceId;
    @Enumerated(EnumType.STRING)
    DeviceType deviceType;
}
