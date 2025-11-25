package com.softix.app_back.config;

import lombok.Builder;

@Builder
public record JWTUserData(String userId, String email) {
}
