package jonegan.rmmservicesserverapp.entities;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity
public class Service {
    @Id
    String id;
    String serviceName;
    @ToString.Exclude
    BigDecimal monthlyCost;
}
