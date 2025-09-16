package com.softix.app_back.client;

import com.softix.app_back.address.Address;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;
import utils.model.RootEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "client")
public class Client extends RootEntity {

    @Column(name = "name", length = 150)
    private String name;

    @Column(name = "email", length = 200)
    private String email;

    @Column(name = "phone", length = 11)
    private String phone;

    @Embedded
    private Address address;

    public Client() {}

    public Client(ClientDTO dto) {
        BeanUtils.copyProperties(dto, this);
    }

}
