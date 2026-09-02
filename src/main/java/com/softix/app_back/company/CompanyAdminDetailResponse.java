package com.softix.app_back.company;

import com.softix.app_back.payment.PaymentMethod;

import java.util.List;
import java.util.Set;

public record CompanyAdminDetailResponse(
        String id,
        String legalName,
        String tradeName,
        String cnpj,
        CompanyType type,
        CompanyStatus status,
        String imageUrl,
        String zipCode,
        String street,
        String number,
        String district,
        String city,
        String state,
        String complement,
        String instagramUrl,
        String facebookUrl,
        String websiteUrl,
        String tiktokUrl,
        Set<PaymentMethod> paymentMethods,
        Set<CompanyAmenity> amenities,
        List<CompanyOpeningHourResponse> openingHours
) {

    public static CompanyAdminDetailResponse fromEntity(Company company) {

        String zipCode = "";
        String street = "";
        String number = "";
        String district = "";
        String city = "";
        String state = "";
        String complement = "";

        if (company.getAddress() != null) {

            zipCode = value(company.getAddress().getPostalCode());
            street = value(company.getAddress().getStreet());
            number = value(company.getAddress().getNumber());
            district = value(company.getAddress().getNeighborhood());
            complement = value(company.getAddress().getComplement());

            if (company.getAddress().getCity() != null) {

                city = value(company.getAddress().getCity().getName());

                if (company.getAddress().getCity().getState() != null) {
                    state = value(company.getAddress().getCity().getState().getAbbreviation());
                }

            }

        }

        List<CompanyOpeningHourResponse> openingHours = company.getOpeningHours().stream().map(CompanyOpeningHourResponse::fromEntity).toList();

        return new CompanyAdminDetailResponse(
                company.getId(),
                company.getLegalName(),
                company.getTradeName(),
                company.getCnpj(),
                company.getType(),
                company.getStatus(),
                company.getImageUrl(),
                zipCode,
                street,
                number,
                district,
                city,
                state,
                complement,
                company.getInstagramUrl(),
                company.getFacebookUrl(),
                company.getWebsiteUrl(),
                company.getTiktokUrl(),
                company.getPaymentMethods(),
                company.getAmenities(),
                openingHours);
    }

    private static String value(String value) {
        return value == null ? "" : value;
    }

}