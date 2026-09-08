package com.softix.app_back.auth.request;

import jakarta.validation.constraints.NotBlank;

public record ExternalAuthRequest(
        @NotBlank String credential
) {
}