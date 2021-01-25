package jonegan.rmmservicesserverapp.repositories;

import jonegan.rmmservicesserverapp.entities.Device;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends CrudRepository<Device, String> {
    List<Device> findDevicesByCustomer_Id(String customerId);
    Optional<Device> findDeviceByCustomer_IdAndId(String customerId, String deviceId);
}
