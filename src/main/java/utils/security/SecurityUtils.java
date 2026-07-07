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

    public static boolean isMasterAdmin() {
        JWTUserData user = currentUser();
        return user != null && "MASTER_ADMIN".equalsIgnoreCase(user.role());
    }

    public static String resolveCompanyId(String requestedCompanyId) {

        JWTUserData user = currentUser();

        if (user == null) {
            return requestedCompanyId;
        }

        if (isMasterAdmin()) {
            return requestedCompanyId;
        }

        return user.companyId();

    }

}

