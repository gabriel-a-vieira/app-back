package com.softix.app_back.company;

public record CompanyTypeResponse(
        String code,
        String label
) {

    public static CompanyTypeResponse fromEnum(CompanyType type) {
        return new CompanyTypeResponse(type.name(), formatType(type));
    }

    private static String formatType(CompanyType type) {

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