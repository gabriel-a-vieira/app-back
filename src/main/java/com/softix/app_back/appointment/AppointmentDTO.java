package com.softix.app_back.appointment;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AppointmentDTO {

    private String id;
    private String clientId;
    private String professionalId;
    private Date startAt;
    private List<String> serviceIds;
    private String companyId;
    private String userId;

}
