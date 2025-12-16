package com.softix.app_back.client;

import com.softix.app_back.address.AddressDTO;
import com.softix.app_back.payment.PaymentMethod;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.UUID;

@Data
public class ClientDTO {

    private UUID id;
    private String name;
    private String cpfCnpj;
    private String email;
    private String phone;
    private String gender;
    private Date birthDate;
    private AddressDTO address;
    private PaymentMethod prefferedPaymentMethod;
    private String additionalNotes;

    public ClientDTO() {}

    public ClientDTO(Client client) {
        BeanUtils.copyProperties(client, this);
    }

}
