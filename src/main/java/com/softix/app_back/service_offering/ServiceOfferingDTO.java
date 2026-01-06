package com.softix.app_back.service_offering;

import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

@Data
public class ServiceOfferingDTO {

    private UUID id;
    private String name;
    private String description;
    private Integer durationMinutes;
    private Double price;

    public ServiceOfferingDTO() {}

    public ServiceOfferingDTO(ServiceOffering serviceOffering) {
        BeanUtils.copyProperties(serviceOffering, this);
    }

}
