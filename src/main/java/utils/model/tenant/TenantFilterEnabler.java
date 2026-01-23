package utils.model.tenant;

import com.softix.app_back.config.JWTUserData;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import utils.model.SecurityUtils;

import java.lang.reflect.Method;

@Component
public class TenantFilterEnabler {

    @PersistenceContext
    private EntityManager entityManager;

    public void enable(Method method) {

        if (method != null && method.isAnnotationPresent(NoTenantFilter.class)) {
            return; // if annotation is present in the method, the companyId filter will not be applied in the query
        }

        JWTUserData user = SecurityUtils.currentUser();

        if (user == null || user.companyId() == null) {
            return;
        }

        Session session = entityManager.unwrap(Session.class);

        // Activates the companyId filter
        session.enableFilter("tenantFilter")
                .setParameter("companyId", user.companyId());

    }

    public void disable() {

        // Disable the filter in the end of the request
        Session session = entityManager.unwrap(Session.class);
        session.disableFilter("tenantFilter");

    }

}