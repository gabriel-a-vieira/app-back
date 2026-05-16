package com.softix.app_back.auth.response;

public record LoginResponse(
        String token,
        String name,
        String email,
        String role
) {
}