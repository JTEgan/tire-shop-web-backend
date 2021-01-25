package jonegan.rmmservicesserverapp.billing;

import jonegan.rmmservicesserverapp.entities.DeviceType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ServiceChargeDetail {
    String serviceId;
    String serviceName;
    DeviceType deviceType;
    long deviceCount;
    BigDecimal monthlyCost;
}
