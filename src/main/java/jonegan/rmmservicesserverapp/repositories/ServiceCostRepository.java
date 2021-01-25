package jonegan.rmmservicesserverapp.repositories;

import jonegan.rmmservicesserverapp.entities.ServiceDeviceTypeCost;
import jonegan.rmmservicesserverapp.entities.ServiceDeviceTypeCostId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceCostRepository extends JpaRepository<ServiceDeviceTypeCost, ServiceDeviceTypeCostId> {
}
