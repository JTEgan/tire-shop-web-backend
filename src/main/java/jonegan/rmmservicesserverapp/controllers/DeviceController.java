package jonegan.rmmservicesserverapp.controllers;

import jonegan.rmmservicesserverapp.entities.Device;
import jonegan.rmmservicesserverapp.repositories.DeviceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/device")
public class DeviceController {
    private final DeviceRepository deviceRepository;

    public DeviceController(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @PostMapping
    public ResponseEntity<Device> createDevice(@RequestBody Device device) {
        if (validForCreate(device)) {
            return new ResponseEntity<>(deviceRepository.save(device), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<List<Device>> getAllDevices() {
        String customerId = "hello";  // todo, get cust from auth
        return new ResponseEntity<>(deviceRepository.findDevicesByCustomer_Id(customerId), HttpStatus.OK);
    }

    @GetMapping(value = "/{deviceId}")
    public ResponseEntity<Device> getDevice(@PathVariable String deviceId) {
        return deviceRepository.findById(deviceId)
                .map(device -> new ResponseEntity<>(device, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(value = "/{deviceId}")
    public ResponseEntity<Device> updateDevice(@PathVariable String deviceId, @RequestBody Device device) {
        if (validForUpdate(deviceId, device)) {
            return new ResponseEntity<>(deviceRepository.save(device), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // todo, make a service that does these, and wraps the repo
    private boolean validForCreate(Device device) {
        return !StringUtils.hasLength(device.getId());
        // and all required fields populated, and customer matches auth'd customer
    }

    private boolean validForUpdate(String deviceId, Device device) {
        return deviceRepository.existsById(deviceId) &&
                StringUtils.hasLength(device.getId()) &&
                device.getId().equals(deviceId);
        // and all required fields populated, and customer matches auth'd customer
    }
}
