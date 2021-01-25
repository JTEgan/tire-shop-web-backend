package jonegan.rmmservicesserverapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ServiceDeviceTypeCostId implements Serializable {
    String serviceId;
    DeviceType deviceType;
}
