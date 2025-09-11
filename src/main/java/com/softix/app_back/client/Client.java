package com.softix.app_back.client;

import com.softix.app_back.address.Address;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

@Data
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue
    @Column(name = "id", length = 38)
    private UUID id;

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
