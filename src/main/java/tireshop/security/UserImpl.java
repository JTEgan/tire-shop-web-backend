package tireshop.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserImpl extends User {
    private final String fullName;

    public UserImpl(String username, String password, String fullName,
                    Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}
