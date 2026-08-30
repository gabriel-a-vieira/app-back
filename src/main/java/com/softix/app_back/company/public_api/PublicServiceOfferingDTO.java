package com.softix.app_back.company.public_api;

import com.softix.app_back.service_offering.ServiceOffering;
import lombok.Data;

@Data
public class PublicServiceOfferingDTO {

    private String id;
    private String name;
    private String description;
    private Integer durationMinutes;
    private Double price;

    public PublicServiceOfferingDTO(ServiceOffering service) {
        this.id = service.getId();
        this.name = service.getName();
        this.description = service.getDescription();
        this.durationMinutes = service.getDurationMinutes();
        this.price = service.getPrice();
    }

}