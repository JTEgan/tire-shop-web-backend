package jonegan.rmmservicesserverapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Service {
    @Id
    String id;
    String serviceName;
    @ToString.Exclude
    BigDecimal monthlyCost;
}
