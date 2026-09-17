package com.softix.app_back.permission;

import com.softix.app_back.user.UserRole;

public record RolePermissionResponse(
        UserRole role,
        SystemModule module,
        boolean canCreate,
        boolean canUpdate,
        boolean canList,
        boolean canDelete
) {
}
