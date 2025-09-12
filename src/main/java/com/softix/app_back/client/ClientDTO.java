package com.softix.app_back.client;

import com.softix.app_back.address.Address;
import com.softix.app_back.address.AddressDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

@Data
public class ClientDTO {

    private UUID id;
    private String name;
    private String email;
    private String phone;
    private AddressDTO address;

    public ClientDTO() {}

    public ClientDTO(Client client) {
        BeanUtils.copyProperties(client, this);
    }

}
