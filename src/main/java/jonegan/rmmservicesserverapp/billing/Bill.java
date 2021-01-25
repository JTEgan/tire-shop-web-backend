package jonegan.rmmservicesserverapp.billing;

import jonegan.rmmservicesserverapp.entities.Service;
import lombok.Value;

import java.math.BigDecimal;
import java.util.Map;

@Value
public class Bill {
    BigDecimal totalCharge;
    BigDecimal devicesCharge;
    Map<Service, BigDecimal> serviceChargeDetails;
}
