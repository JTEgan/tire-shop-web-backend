package jonegan.rmmservicesserverapp.entities;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class ServiceCostId implements Serializable {
    String serviceId;
    String deviceTypeId;
}
