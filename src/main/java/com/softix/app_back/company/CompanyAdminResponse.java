package com.softix.app_back.company;

public record CompanyAdminResponse(
        String id,
        String legalName,
        String tradeName,
        String cnpj,
        CompanyType type,
        String typeLabel,
        CompanyStatus status,
        String city,
        String state
) {

    public static CompanyAdminResponse fromEntity(Company company) {

        String city = "";
        String state = "";

        if (company.getAddress() != null && company.getAddress().getCity() != null) {

            city = company.getAddress().getCity().getName();

            if (company.getAddress().getCity().getState() != null) {
                state = company.getAddress().getCity().getState().getAbbreviation();
            }

        }

        return new CompanyAdminResponse(
                company.getId(),
                company.getLegalName(),
                company.getTradeName(),
                company.getCnpj(),
                company.getType(),
                company.getType() != null ? CompanyTypeResponse.fromEnum(company.getType()).label() : "",
                company.getStatus(),
                city,
                state);
    }

}