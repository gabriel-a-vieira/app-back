package com.softix.app_back.professional;

import com.softix.app_back.address.AddressDTO;
import com.softix.app_back.payment.PaymentMethod;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
public class ProfessionalDTO {

    private String id;
    private String personId;
    private ProfessionalStatus status;

    // Person
    private String name;
    private String cpfCnpj;
    private String email;
    private String phone;
    private String gender;
    private Date birthDate;
    private AddressDTO address;
    private PaymentMethod prefferedPaymentMethod;
    private String additionalNotes;


    public ProfessionalDTO() {}

    public ProfessionalDTO(Professional professional) {
        BeanUtils.copyProperties(professional, this);
    }

}
