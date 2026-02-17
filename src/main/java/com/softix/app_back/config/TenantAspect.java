package com.softix.app_back.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import utils.model.tenant.IgnoreTenantFilter;
import utils.security.SecurityUtils;

import java.lang.reflect.Method;

@Aspect
@Component
public class TenantAspect {

    @PersistenceContext
    private EntityManager entityManager;

    @Pointcut("execution(* com.softix.app_back..*Repository.*(..)) " +
            "|| execution(* com.softix.app_back..*Service.*(..)) " +
            "|| execution(* com.softix.app_back..*Controller.*(..))")
    public void appLayers() {}

    @Before("appLayers()")
    public void manageTenantFilter(JoinPoint joinPoint) {

        Session session = entityManager.unwrap(Session.class);

        boolean ignore = checkIgnoreAnnotation(joinPoint);

        if (ignore) {
            session.disableFilter("tenantFilter");
        } else {

            if (SecurityUtils.currentUser() != null) {

                String companyId = SecurityUtils.companyId();

                if (companyId != null) {
                    session.enableFilter("tenantFilter").setParameter("companyId", companyId);
                }

            }

        }

    }

    private boolean checkIgnoreAnnotation(JoinPoint joinPoint) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        if (method.isAnnotationPresent(IgnoreTenantFilter.class)) return true;

        if (joinPoint.getTarget().getClass().isAnnotationPresent(IgnoreTenantFilter.class)) return true;

        for (Class<?> iface : joinPoint.getTarget().getClass().getInterfaces()) {
            if (iface.isAnnotationPresent(IgnoreTenantFilter.class)) return true;
        }

        return false;

    }

}