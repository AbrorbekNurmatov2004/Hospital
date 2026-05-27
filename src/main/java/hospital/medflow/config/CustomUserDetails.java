package hospital.medflow.config;

import hospital.medflow.model.Permission;
import hospital.medflow.model.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private String id;
    private String username;
    private String password;
    private Role role;
    private Boolean superAdmin;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (Boolean.TRUE.equals(superAdmin)) {
            authorities.add(new SimpleGrantedAuthority("super"));
        }

        if (role == null) {
            return authorities;
        }

        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getCode()));
        List<Permission> permissions = role.getPermissions();
        permissions.forEach(p -> authorities.add(new SimpleGrantedAuthority(p.getCode())));

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

}
