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
        boolean googleLinked,
        boolean personalDataCompleted
) {
}