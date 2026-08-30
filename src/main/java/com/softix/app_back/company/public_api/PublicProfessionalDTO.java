package com.softix.app_back.company.public_api;

import com.softix.app_back.professional.Professional;
import lombok.Data;

@Data
public class PublicProfessionalDTO {

    private String id;
    private String name;

    public PublicProfessionalDTO(Professional professional) {

        this.id = professional.getId();

        if (professional.getPerson() != null) {
            this.name = professional.getPerson().getName();
        }

    }

}