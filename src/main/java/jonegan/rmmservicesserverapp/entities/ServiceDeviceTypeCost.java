package jonegan.rmmservicesserverapp.entities;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Data
@Entity
public class ServiceDeviceTypeCost {
    @EmbeddedId
    ServiceDeviceTypeCostId serviceDeviceTypeCostId;
    BigDecimal monthlyCost;
}
