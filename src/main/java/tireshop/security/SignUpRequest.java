package tireshop.security;

import lombok.Data;

@Data
public class SignUpRequest {
    String username;
    String password;
    String fullName;
}
