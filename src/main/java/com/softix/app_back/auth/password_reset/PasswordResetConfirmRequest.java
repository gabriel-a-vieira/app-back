package com.softix.app_back.auth.password_reset;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PasswordResetConfirmRequest(@NotBlank(message = "E-mail é obrigatório")
                                          String email,

                                          @NotBlank(message = "Código é obrigatório")
                                          @Pattern(regexp = "\\d{6}", message = "O código deve ter 6 dígitos")
                                          String code,

                                          @NotBlank(message = "Nova senha é obrigatória")
                                          @Size(min = 6, message = "A nova senha deve ter pelo menos 6 caracteres")
                                          String newPassword) {
}
