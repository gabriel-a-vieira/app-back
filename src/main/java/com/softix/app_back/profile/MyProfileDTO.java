package com.softix.app_back.profile;

import java.time.LocalDate;

public record MyProfileDTO(
        String userId,
        String name,
        String email,
        String role,
        String personId,
        String cpfCnpj,
        String phone,
        LocalDate birthDate,
        String gender,
        String street,
        String number,
        String postalCode,
        String complement,
        String neighborhood,
        Double latitude,
        Double longitude,
        String city,
        String state,
        boolean googleLinked,
        boolean personalDataCompleted
) {
}