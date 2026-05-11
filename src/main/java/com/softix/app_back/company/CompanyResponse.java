package com.softix.app_back.company;

public record CompanyResponse(
        String id,
        String legalName,
        String tradeName,
        CompanyType type,
        String typeLabel,
        String status
) {

    public static CompanyResponse fromEntity(Company company) {
        return new CompanyResponse(
                company.getId(),
                company.getLegalName(),
                company.getTradeName(),
                company.getType(),
                formatType(company.getType()),
                company.getStatus().name()
        );
    }

    private static String formatType(CompanyType type) {

        if (type == null) {
            return "";
        }

        return switch (type) {
            case BARBERSHOP -> "Barbearia";
            case BEAUTY_SALON -> "Salao de beleza";
            case HAIR_SALON -> "Salao de cabelo";
            case AESTHETIC_CLINIC -> "Clinica estetica";
            case MEDICAL_CLINIC -> "Clinica medica";
            case DENTAL_CLINIC -> "Clinica odontologica";
            case PSYCHOLOGY_CLINIC -> "Clinica de psicologia";
            case PHYSIOTHERAPY -> "Fisioterapia";
            case NUTRITION -> "Nutricao";
            case VETERINARY -> "Veterinaria";
            case PET_SHOP -> "Pet shop";
            case SPA -> "Spa";
            case FITNESS -> "Fitness";
            case CONSULTING -> "Consultoria";
            case EDUCATION -> "Educacao";
            case COACHING -> "Coaching";
            case EVENT_SERVICE -> "Eventos";
            case TECH_SUPPORT -> "Suporte tecnico";
        };

    }

}