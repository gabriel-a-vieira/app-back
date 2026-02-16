package utils.security;

import com.softix.app_back.config.JWTUserData;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

    private SecurityUtils() {}

    public static JWTUserData currentUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof JWTUserData)) {
            return null;
        }

        return (JWTUserData) auth.getPrincipal();

    }

    public static String userId() {
        return currentUser().userId();
    }

    public static String companyId() {
        return currentUser().companyId();
    }

    public static String role() {
        return currentUser().role();
    }

}

