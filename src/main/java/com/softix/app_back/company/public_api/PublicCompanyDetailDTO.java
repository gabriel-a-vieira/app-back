package com.softix.app_back.company.public_api;

import com.softix.app_back.company.CompanyAmenity;
import com.softix.app_back.payment.PaymentMethod;

import java.util.List;
import java.util.Set;

public record PublicCompanyDetailDTO(

        String id,

        String legalName,
        String tradeName,

        String imageUrl,

        PublicCompanyAddressDTO address,

        List<PublicCompanyOpeningDayDTO> openingHours,

        Set<PaymentMethod> paymentMethods,

        Set<CompanyAmenity> amenities,

        String instagramUrl,
        String facebookUrl,
        String websiteUrl,
        String tiktokUrl

) {
}