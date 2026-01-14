package com.softix.app_back.auth.request;

import jakarta.validation.constraints.NotEmpty;

public record RegisterUserRequest(@NotEmpty(message = "Nome é obrigatório") String name,
                                  @NotEmpty(message = "E-mail é obrigatório") String email,
                                  @NotEmpty(message = "Empresa é obrigatória") String companyId,
                                  @NotEmpty(message = "Função é obrigatória") String role,
                                  @NotEmpty(message = "Senha é obrigatória") String password) {
}
