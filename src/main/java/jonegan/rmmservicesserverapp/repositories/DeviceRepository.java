package jonegan.rmmservicesserverapp.repositories;

import jonegan.rmmservicesserverapp.entities.Device;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DeviceRepository extends CrudRepository<Device, String> {
    List<Device> findDevicesByCustomer_Id(String customerId);
}
