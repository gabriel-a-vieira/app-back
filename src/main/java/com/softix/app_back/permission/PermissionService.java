package com.softix.app_back.permission;

import com.softix.app_back.config.JWTUserData;
import com.softix.app_back.shared.exception.BusinessException;
import com.softix.app_back.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.security.SecurityUtils;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private static final List<UserRole> CONFIGURABLE_ROLES = List.of(UserRole.COMPANY_ADMIN, UserRole.PROFESSIONAL);

    private final RolePermissionRepository rolePermissionRepository;

    public List<RolePermissionResponse> findMatrix() {

        requireMasterAdmin();

        Map<String, RolePermission> existing = new HashMap<>();

        for (RolePermission permission : rolePermissionRepository.findAll()) {
            existing.put(key(permission.getRole(), permission.getModule()), permission);
        }

        List<RolePermissionResponse> matrix = new ArrayList<>();

        for (UserRole role : CONFIGURABLE_ROLES) {
            for (SystemModule module : SystemModule.values()) {
                RolePermission permission = existing.get(key(role, module));
                matrix.add(toResponse(role, module, permission));
            }
        }

        return matrix;

    }

    @Transactional
    public void updateMatrix(List<RolePermissionRequest> requests) {

        requireMasterAdmin();

        for (RolePermissionRequest request : requests) {

            RolePermission permission = rolePermissionRepository
                    .findByRoleAndModule(request.role(), request.module())
                    .orElseGet(RolePermission::new);

            permission.setRole(request.role());
            permission.setModule(request.module());
            permission.setCanCreate(request.canCreate());
            permission.setCanUpdate(request.canUpdate());
            permission.setCanList(request.canList());
            permission.setCanDelete(request.canDelete());

            rolePermissionRepository.save(permission);

        }

    }

    public Map<SystemModule, ModulePermissionResponse> findMyPermissions() {

        Map<SystemModule, ModulePermissionResponse> result = new EnumMap<>(SystemModule.class);

        JWTUserData user = SecurityUtils.currentUser();

        if (user == null) {
            return result;
        }

        UserRole role = parseRole(user.role());

        boolean fullAccess = role == UserRole.MASTER_ADMIN;

        for (SystemModule module : SystemModule.values()) {

            if (fullAccess) {
                result.put(module, ModulePermissionResponse.fullAccess());
                continue;
            }

            if (!CONFIGURABLE_ROLES.contains(role)) {
                result.put(module, ModulePermissionResponse.noAccess());
                continue;
            }

            RolePermission permission = rolePermissionRepository.findByRoleAndModule(role, module).orElse(null);

            result.put(module, permission == null ? ModulePermissionResponse.fullAccess() : toModuleResponse(permission));

        }

        return result;

    }

    private void requireMasterAdmin() {
        if (!SecurityUtils.isMasterAdmin()) {
            throw new BusinessException(HttpStatus.FORBIDDEN, "Apenas o administrador da plataforma pode gerenciar permissoes.");
        }
    }

    private RolePermissionResponse toResponse(UserRole role, SystemModule module, RolePermission permission) {

        if (permission == null) {
            return new RolePermissionResponse(role, module, true, true, true, true);
        }

        return new RolePermissionResponse(
                role,
                module,
                permission.isCanCreate(),
                permission.isCanUpdate(),
                permission.isCanList(),
                permission.isCanDelete()
        );

    }

    private ModulePermissionResponse toModuleResponse(RolePermission permission) {
        return new ModulePermissionResponse(
                permission.isCanCreate(),
                permission.isCanUpdate(),
                permission.isCanList(),
                permission.isCanDelete()
        );
    }

    private String key(UserRole role, SystemModule module) {
        return role.name() + ":" + module.name();
    }

    private UserRole parseRole(String role) {
        try {
            return UserRole.valueOf(role);
        } catch (IllegalArgumentException | NullPointerException e) {
            return null;
        }
    }

}
