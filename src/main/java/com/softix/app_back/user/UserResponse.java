package com.softix.app_back.user;

public record UserResponse(String id,
                           String name,
                           String email,
                           UserRole role,
                           String companyId) {

    public static UserResponse fromEntity(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole(), user.getCompanyId());
    }

}
