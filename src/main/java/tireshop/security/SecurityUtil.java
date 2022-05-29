package tireshop.security;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;

public class SecurityUtil {
    public static boolean validCustomerUser(Principal principal, String customerId) {
        return customerId.equals(principal.getName());
    }

    static String hashPassword(String rawPassword) {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return encoder.encode(rawPassword);
    }
}
