package jonegan.rmmservicesserverapp.security;

import lombok.Data;
import lombok.Value;

@Data
public class SignUpRequest {
    String username;
    String password;
}
