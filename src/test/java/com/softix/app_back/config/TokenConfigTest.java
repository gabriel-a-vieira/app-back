package com.softix.app_back.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.softix.app_back.user.User;
import com.softix.app_back.user.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Covers TokenConfig's JWT round-trip and, more importantly, that
 * validateToken never throws on a bad token (expired, tampered, garbage)
 * but always degrades to Optional.empty(). TokenConfig backs the whole
 * authentication mechanism, so a regression here is a security bug, not
 * just a broken feature.
 */
class TokenConfigTest {

    private static final String SECRET = "test-secret-value";

    private TokenConfig tokenConfig;

    @BeforeEach
    void setUp() {
        tokenConfig = new TokenConfig();
        ReflectionTestUtils.setField(tokenConfig, "secret", SECRET);
    }

    private User buildUser() {

        User user = new User();
        user.setId("user-1");
        user.setCompanyId("company-1");
        user.setEmail("user@softix.com");
        user.setRole(UserRole.COMPANY_ADMIN);

        return user;

    }

    @Test
    void generateAndValidateToken_roundTripsAllClaims() {

        String token = tokenConfig.generateToken(buildUser());

        Optional<JWTUserData> result = tokenConfig.validateToken(token);

        assertThat(result).isPresent();
        assertThat(result.get().userId()).isEqualTo("user-1");
        assertThat(result.get().companyId()).isEqualTo("company-1");
        assertThat(result.get().email()).isEqualTo("user@softix.com");
        assertThat(result.get().role()).isEqualTo("COMPANY_ADMIN");
    }

    @Test
    void validateToken_returnsEmptyForGarbageInput() {
        assertThat(tokenConfig.validateToken("not-a-jwt")).isEmpty();
    }

    @Test
    void validateToken_returnsEmptyWhenSignedWithADifferentSecret() {

        TokenConfig otherIssuer = new TokenConfig();
        ReflectionTestUtils.setField(otherIssuer, "secret", "a-different-secret");

        String token = otherIssuer.generateToken(buildUser());

        assertThat(tokenConfig.validateToken(token)).isEmpty();
    }

    @Test
    void validateToken_returnsEmptyWhenExpired() {

        Algorithm algorithm = Algorithm.HMAC256(SECRET);

        String expiredToken = JWT.create()
                .withClaim("userId", "user-1")
                .withClaim("companyId", "company-1")
                .withClaim("role", "COMPANY_ADMIN")
                .withSubject("user@softix.com")
                .withExpiresAt(Instant.now().minusSeconds(10))
                .sign(algorithm);

        assertThat(tokenConfig.validateToken(expiredToken)).isEmpty();
    }

}
