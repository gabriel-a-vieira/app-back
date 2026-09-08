package com.softix.app_back.auth.external;

public record ExternalIdentity(
        AuthProvider provider,
        String providerUserId,
        String email,
        String name,
        String pictureUrl,
        boolean emailVerified
) {
}