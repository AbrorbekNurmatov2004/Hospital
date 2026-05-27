package hospital.medflow.utils;

import hospital.medflow.config.CustomUserDetails;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component("securityUtils")
public class SecurityUtils {
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final String ACCESS_TOKEN = "access token";
    public static final String TYPE = "type";

    public static String[] WHITE_LIST = {
            "/api/v1/auth/login",
            "/api/v1/auth/refresh-token",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/api-docs/**"
    };

    public static CustomUserDetails sessionUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return (CustomUserDetails) authentication.getPrincipal();
        }
        throw new AuthenticationCredentialsNotFoundException("User not found");
    }

    public boolean superAdmin() {
        try {
            return sessionUser().getSuperAdmin();
        } catch (Exception e) {
            return false;
        }
    }

}
