package com.softix.app_back.profile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateMyProfileRequest(

        @NotBlank(message = "Nome obrigatorio")
        @Size(max = 150, message = "Nome deve possuir no maximo 150 caracteres")
        String name,

        @Size(max = 11, message = "CPF deve possuir no maximo 11 caracteres")
        String cpfCnpj,

        @Size(max = 11, message = "Telefone deve possuir no maximo 11 caracteres")
        String phone,

        LocalDate birthDate,

        @Size(max = 20, message = "Genero deve possuir no maximo 20 caracteres")
        String gender,

        @Size(max = 100, message = "Rua deve possuir no maximo 100 caracteres")
        String street,

        @Size(max = 15, message = "Numero deve possuir no maximo 15 caracteres")
        String number,

        @Size(max = 8, message = "CEP deve possuir no maximo 8 caracteres")
        String postalCode,

        @Size(max = 200,message = "Complemento deve possuir no maximo 200 caracteres")
        String complement,

        @Size(max = 100, message = "Bairro deve possuir no maximo 100 caracteres")
        String neighborhood,

        Double latitude,
        Double longitude,
        String city,
        String state

) {
}