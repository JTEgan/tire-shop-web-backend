package jonegan.rmmservicesserverapp.controllers.dtos;

import jonegan.rmmservicesserverapp.entities.DeviceType;
import lombok.Value;

@Value
public class DeviceDto {
    String id;
    String systemName;
    DeviceType deviceType;
}
