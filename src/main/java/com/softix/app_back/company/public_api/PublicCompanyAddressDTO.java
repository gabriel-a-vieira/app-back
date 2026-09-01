package com.softix.app_back.company.public_api;

public record PublicCompanyAddressDTO(

        String formattedAddress,

        Double latitude,
        Double longitude

) {
}