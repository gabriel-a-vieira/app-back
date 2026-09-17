package com.softix.app_back.permission;

public record ModulePermissionResponse(
        boolean canCreate,
        boolean canUpdate,
        boolean canList,
        boolean canDelete
) {

    public static ModulePermissionResponse fullAccess() {
        return new ModulePermissionResponse(true, true, true, true);
    }

    public static ModulePermissionResponse noAccess() {
        return new ModulePermissionResponse(false, false, false, false);
    }

}
