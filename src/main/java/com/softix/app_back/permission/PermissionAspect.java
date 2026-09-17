package com.softix.app_back.permission;

import com.softix.app_back.config.JWTUserData;
import com.softix.app_back.shared.exception.BusinessException;
import com.softix.app_back.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import utils.security.SecurityUtils;

/**
 * Enforces {@link RequiresPermission} on controller endpoints. Mirrors the
 * annotation + @Before-advice + SecurityUtils pattern already used by
 * TenantAspect/@IgnoreTenantFilter, rather than @PreAuthorize SpEL (unused
 * elsewhere in this codebase).
 */
@Aspect
@Component
@RequiredArgsConstructor
public class PermissionAspect {

    private static final String ACCESS_DENIED_MESSAGE = "Voce nao tem permissao para executar esta acao.";

    private final RolePermissionRepository rolePermissionRepository;

    @Before("@annotation(requiresPermission)")
    public void checkPermission(RequiresPermission requiresPermission) {

        JWTUserData user = SecurityUtils.currentUser();

        if (user == null) {
            return;
        }

        UserRole role;

        try {
            role = UserRole.valueOf(user.role());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new BusinessException(HttpStatus.FORBIDDEN, ACCESS_DENIED_MESSAGE);
        }

        if (role == UserRole.MASTER_ADMIN) {
            return;
        }

        if (role != UserRole.COMPANY_ADMIN && role != UserRole.PROFESSIONAL) {
            throw new BusinessException(HttpStatus.FORBIDDEN, ACCESS_DENIED_MESSAGE);
        }

        RolePermission permission = rolePermissionRepository
                .findByRoleAndModule(role, requiresPermission.module())
                .orElse(null);

        if (permission != null && !isAllowed(permission, requiresPermission.action())) {
            throw new BusinessException(HttpStatus.FORBIDDEN, ACCESS_DENIED_MESSAGE);
        }

    }

    private boolean isAllowed(RolePermission permission, CrudAction action) {
        return switch (action) {
            case CREATE -> permission.isCanCreate();
            case UPDATE -> permission.isCanUpdate();
            case LIST -> permission.isCanList();
            case DELETE -> permission.isCanDelete();
        };
    }

}
