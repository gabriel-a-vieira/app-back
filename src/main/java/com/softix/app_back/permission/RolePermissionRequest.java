package com.softix.app_back.permission;

import com.softix.app_back.user.UserRole;

public record RolePermissionRequest(
        UserRole role,
        SystemModule module,
        boolean canCreate,
        boolean canUpdate,
        boolean canList,
        boolean canDelete
) {
}
