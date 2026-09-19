package com.softix.app_back.user;

public record UserResponse(String id,
                           String name,
                           String email,
                           UserRole role,
                           String companyId,
                           String clientId,
                           String clientName,
                           String professionalId,
                           String professionalName) {

    public static UserResponse fromEntity(User user) {
        return fromEntity(user, null, null, null, null);
    }

    public static UserResponse fromEntity(User user, String clientId, String clientName,
                                          String professionalId, String professionalName) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole(), user.getCompanyId(),
                clientId, clientName, professionalId, professionalName);
    }

}
