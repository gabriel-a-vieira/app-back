package com.softix.app_back.service_offering;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ServiceOfferingDTO {

    private String id;

    @NotBlank(message = "Nome e obrigatorio")
    private String name;

    private String description;

    @NotNull(message = "Duracao e obrigatoria")
    @Min(value = 1, message = "Duracao deve ser maior que zero")
    private Integer durationMinutes;

    @NotNull(message = "Preco e obrigatorio")
    @DecimalMin(
            value = "0.0",
            inclusive = true,
            message = "Preco nao pode ser negativo"
    )
    private Double price;

    private ServiceOfferingStatus status;

    private String companyId;

    public ServiceOfferingDTO() {}

    public ServiceOfferingDTO(ServiceOffering serviceOffering) {

        this.id = serviceOffering.getId();
        this.name = serviceOffering.getName();
        this.description = serviceOffering.getDescription();
        this.durationMinutes = serviceOffering.getDurationMinutes();
        this.price = serviceOffering.getPrice();
        this.status = serviceOffering.getStatus();
        this.companyId = serviceOffering.getCompanyId();

    }

}