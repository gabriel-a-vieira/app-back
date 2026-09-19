package com.softix.app_back.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * password is optional — blank/null keeps the current password unchanged.
 */
public record UpdateUserRequest(@NotBlank String name,
                                @NotBlank @Email String email,
                                @NotNull UserRole role,
                                String password,
                                String clientId, String professionalId,
                                Boolean autoCreateLinkedRecord) {
}
