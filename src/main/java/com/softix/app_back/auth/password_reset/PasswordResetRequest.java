package com.softix.app_back.auth.password_reset;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PasswordResetRequest(@NotBlank(message = "E-mail é obrigatório")
                                   @Email(message = "E-mail inválido")
                                   String email) {
}
