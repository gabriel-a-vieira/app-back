package com.softix.app_back.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import utils.security.SecurityUtils;

@Aspect
@Component
public class TenantAspect {

    @PersistenceContext
    private EntityManager entityManager;

    @Before("execution(* com.softix.app_back..*Repository.*(..)) || execution(* com.softix.app_back..*Service.*(..))")
    public void enableTenantFilter() {

        if (SecurityUtils.currentUser() != null) {

            String companyId = SecurityUtils.companyId();

            if (companyId != null) {

                Session session = entityManager.unwrap(Session.class);
                session.enableFilter("tenantFilter").setParameter("companyId", companyId);

            }

        }

    }

}