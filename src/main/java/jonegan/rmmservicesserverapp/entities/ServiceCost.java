package jonegan.rmmservicesserverapp.entities;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Data
@Entity
public class ServiceCost {
    @EmbeddedId
    ServiceCostId serviceCostId;
    BigDecimal monthlyCost;
}
