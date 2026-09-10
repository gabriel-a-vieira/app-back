package com.softix.app_back.auth.external;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserExternalIdentityRepository extends JpaRepository<UserExternalIdentity, String> {

    Optional<UserExternalIdentity> findByProviderAndProviderUserId(AuthProvider provider, String providerUserId);

    @Query("""
            SELECT CASE
                       WHEN COUNT(identity) > 0 THEN true
                       ELSE false
                   END
              FROM UserExternalIdentity identity
             WHERE identity.user.id = :userId
               AND identity.provider = :provider
            """)
    boolean existsByUserIdAndProvider(@Param("userId") String userId,
                                      @Param("provider") AuthProvider provider);
}