package com.softix.app_back.permission;

import com.softix.app_back.user.UserRole;

public record UserPermissionProfileResponse(
        String userId,
        String name,
        String email,
        UserRole role
) {
}
