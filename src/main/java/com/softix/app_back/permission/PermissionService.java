package com.softix.app_back.permission;

import com.softix.app_back.config.JWTUserData;
import com.softix.app_back.shared.exception.BusinessException;
import com.softix.app_back.user.User;
import com.softix.app_back.user.UserRepository;
import com.softix.app_back.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    private final UserPermissionRepository userPermissionRepository;

    private final UserRepository userRepository;

    public Page<UserPermissionProfileResponse> findConfiguredProfiles(String search, Pageable pageable) {

        requireCompanyAdmin();

        List<String> userIds = userPermissionRepository.findDistinctUserIds();

        if (userIds.isEmpty()) {
            return Page.empty(pageable);
        }

        return userRepository.findByIdInAndSearch(userIds, search, pageable)
                .map(user -> new UserPermissionProfileResponse(user.getId(), user.getName(), user.getEmail(), user.getRole()));

    }

    public List<ModulePermissionEntry> findUserMatrix(String userId) {

        requireCompanyAdmin();

        requireConfigurableUser(userId);

        Map<SystemModule, UserPermission> existing = new HashMap<>();

        for (UserPermission permission : userPermissionRepository.findByUserId(userId)) {
            existing.put(permission.getModule(), permission);
        }

        List<ModulePermissionEntry> matrix = new ArrayList<>();

        for (SystemModule module : SystemModule.values()) {
            matrix.add(toEntry(module, existing.get(module)));
        }

        return matrix;

    }

    @Transactional
    public void updateUserMatrix(String userId, List<ModulePermissionEntry> entries) {

        requireCompanyAdmin();

        requireConfigurableUser(userId);

        for (ModulePermissionEntry entry : entries) {

            UserPermission permission = userPermissionRepository
                    .findByUserIdAndModule(userId, entry.module())
                    .orElseGet(UserPermission::new);

            permission.setUserId(userId);
            permission.setModule(entry.module());
            permission.setCanCreate(entry.canCreate());
            permission.setCanUpdate(entry.canUpdate());
            permission.setCanList(entry.canList());
            permission.setCanDelete(entry.canDelete());

            userPermissionRepository.save(permission);

        }

    }

    @Transactional
    public void deleteProfiles(List<String> userIds) {
        requireCompanyAdmin();
        userPermissionRepository.deleteByUserIdIn(userIds);
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

            UserPermission permission = userPermissionRepository.findByUserIdAndModule(user.userId(), module).orElse(null);

            result.put(module, permission == null ? ModulePermissionResponse.fullAccess() : toModuleResponse(permission));

        }

        return result;

    }

    private void requireCompanyAdmin() {
        JWTUserData user = SecurityUtils.currentUser();

        if (user == null || !"COMPANY_ADMIN".equalsIgnoreCase(user.role())) {
            throw new BusinessException(HttpStatus.FORBIDDEN, "Apenas o administrador da empresa pode gerenciar permissoes.");
        }
    }

    private User requireConfigurableUser(String userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(HttpStatus.BAD_REQUEST, "Usuario nao encontrado"));

        if (!CONFIGURABLE_ROLES.contains(user.getRole())) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Este usuario nao pode ter permissoes configuradas.");
        }

        return user;

    }

    private ModulePermissionEntry toEntry(SystemModule module, UserPermission permission) {

        if (permission == null) {
            return new ModulePermissionEntry(module, true, true, true, true);
        }

        return new ModulePermissionEntry(
                module,
                permission.isCanCreate(),
                permission.isCanUpdate(),
                permission.isCanList(),
                permission.isCanDelete()
        );

    }

    private ModulePermissionResponse toModuleResponse(UserPermission permission) {
        return new ModulePermissionResponse(
                permission.isCanCreate(),
                permission.isCanUpdate(),
                permission.isCanList(),
                permission.isCanDelete()
        );
    }

    private UserRole parseRole(String role) {
        try {
            return UserRole.valueOf(role);
        } catch (IllegalArgumentException | NullPointerException e) {
            return null;
        }
    }

}
