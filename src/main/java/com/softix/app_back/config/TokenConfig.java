package com.softix.app_back.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.softix.app_back.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public class TokenConfig {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(User user) {

        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withClaim("userId", user.getId())
                .withClaim("companyId", user.getCompanyId())
                .withClaim("role", user.getRole().name())
                .withSubject(user.getEmail())
                .withExpiresAt(Instant.now().plusSeconds(14400))
                .sign(algorithm);

    }

    public Optional<JWTUserData> validateToken(String token) {

        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);

            DecodedJWT decode = JWT.require(algorithm).build().verify(token);

            return Optional.of(JWTUserData.builder()
                    .userId(decode.getClaim("userId").asString())
                    .companyId(decode.getClaim("companyId").asString())
                    .email(decode.getSubject())
                    .role(decode.getClaim("role").asString())
                    .build());

        } catch (JWTVerificationException e) {
            return Optional.empty();
        }

    }

}
