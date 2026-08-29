package com.softix.app_back.appointment;

import lombok.Data;

@Data
public class AppointmentServiceItemDTO {

    private String serviceOfferingId;
    private String name;
    private Integer durationMinutes;
    private Double price;
    private Integer executionOrder;

    public AppointmentServiceItemDTO() {}

    public AppointmentServiceItemDTO(AppointmentServiceItem item) {

        this.serviceOfferingId = item.getServiceOffering() != null ? item.getServiceOffering().getId() : item.getServiceOfferingId();

        if (item.getServiceOffering() != null) {
            this.name = item.getServiceOffering().getName();
        }

        this.durationMinutes = item.getDurationMinutes();

        this.price = item.getPrice();

        this.executionOrder = item.getExecutionOrder();

    }

}