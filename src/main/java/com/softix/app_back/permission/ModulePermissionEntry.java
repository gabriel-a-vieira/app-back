package com.softix.app_back.permission;

/**
 * One row of a specific user's permission matrix: a module and the four CRUD
 * flags. Used both to read (GET /permission/{userId}) and write
 * (PUT /permission/{userId}) that user's permissions.
 */
public record ModulePermissionEntry(
        SystemModule module,
        boolean canCreate,
        boolean canUpdate,
        boolean canList,
        boolean canDelete
) {
}
