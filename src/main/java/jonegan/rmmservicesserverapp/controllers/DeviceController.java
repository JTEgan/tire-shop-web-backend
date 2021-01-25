package jonegan.rmmservicesserverapp.controllers;

import jonegan.rmmservicesserverapp.controllers.dtos.DeviceDto;
import jonegan.rmmservicesserverapp.entities.Device;
import jonegan.rmmservicesserverapp.repositories.CustomerRepository;
import jonegan.rmmservicesserverapp.repositories.DeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static jonegan.rmmservicesserverapp.security.SecurityUtil.validCustomerUser;

@Slf4j
@RestController
public class DeviceController {
    private final DeviceRepository deviceRepository;
    private final CustomerRepository customerRepository;

    public DeviceController(DeviceRepository deviceRepository, CustomerRepository customerRepository) {
        this.deviceRepository = deviceRepository;
        this.customerRepository = customerRepository;
    }

    @GetMapping("/customers/{customerId}/devices")
    public ResponseEntity<List<DeviceDto>> getAllDevices(Principal principal, @PathVariable String customerId) {
        if (validCustomerUser(principal, customerId)) {
            List<DeviceDto> customerDeviceDtos = deviceRepository.findDevicesByCustomer_Id(customerId).stream()
                    .map(this::mapDeviceEntityToDto)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(customerDeviceDtos, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/customers/{customerId}/devices/{deviceId}")
    public ResponseEntity<DeviceDto> getDevice(Principal principal, @PathVariable String customerId, @PathVariable String deviceId) {
        if (validCustomerUser(principal, customerId)) {
            return deviceRepository.findDeviceByCustomer_IdAndId(customerId, deviceId)
                    .map(this::mapDeviceEntityToDto)
                    .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/customers/{customerId}/devices")
    public ResponseEntity<DeviceDto> createDevice(Principal principal, @PathVariable String customerId, @RequestBody DeviceDto deviceDto) {
        if (validCustomerUser(principal, customerId) && validForCreate(deviceDto)) {
            Device device = mapDeviceDtoToEntity(customerId, deviceDto);
            device.setId(UUID.randomUUID().toString());
            device = deviceRepository.save(device);
            log.info("saved successfully, device is: " + device);
            DeviceDto newDto = mapDeviceEntityToDto(device);
            return new ResponseEntity<>(newDto, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/customers/{customerId}/devices/{deviceId}")
    public ResponseEntity<DeviceDto> updateDevice(Principal principal, @PathVariable String customerId, @PathVariable String deviceId, @RequestBody DeviceDto deviceDto) {
        if (validCustomerUser(principal, customerId) && validForUpdate(deviceId, deviceDto)) {
            Device device = mapDeviceDtoToEntity(customerId, deviceDto);
            device = deviceRepository.save(device);
            log.info("saved successfully, device is: " + device);
            DeviceDto newDto = mapDeviceEntityToDto(device);
            return new ResponseEntity<>(newDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private boolean validForCreate(DeviceDto deviceDto) {
        return deviceDto.getDeviceType() != null &&
                StringUtils.hasLength(deviceDto.getSystemName()) &&
                !StringUtils.hasLength(deviceDto.getId());
    }

    private boolean validForUpdate(String deviceId, DeviceDto deviceDto) {
        return StringUtils.hasLength(deviceDto.getId()) &&
                deviceDto.getId().equals(deviceId) &&
                deviceDto.getDeviceType() != null &&
                StringUtils.hasLength(deviceDto.getSystemName()) &&
                deviceRepository.existsById(deviceId);
    }

    private DeviceDto mapDeviceEntityToDto(Device device) {
        return new DeviceDto(device.getId(), device.getSystemName(), device.getDeviceType());
    }

    private Device mapDeviceDtoToEntity(String customerId, DeviceDto deviceDto) {
        Device device = new Device();
        device.setDeviceType(deviceDto.getDeviceType());
        device.setSystemName(deviceDto.getSystemName());
        device.setCustomer(customerRepository.getOne(customerId));
        return device;
    }

}
