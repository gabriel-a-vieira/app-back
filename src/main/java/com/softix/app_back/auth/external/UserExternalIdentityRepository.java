package com.softix.app_back.auth.external;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserExternalIdentityRepository extends JpaRepository<UserExternalIdentity, String> {
    Optional<UserExternalIdentity> findByProviderAndProviderUserId(AuthProvider provider, String providerUserId);
}