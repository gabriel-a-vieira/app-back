package com.softix.app_back.person;

import com.softix.app_back.address.Address;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import utils.model.RootEntity;

import java.util.Date;

@Entity
@Data
@Table(name = "person")
public class Person extends RootEntity {

    @Column(name = "name", length = 150)
    private String name;

    @Column(name = "cpfCnpj", length = 11)
    private String cpfCnpj;

    @Column(name = "phone", length = 11)
    private String phone;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "gender", length = 20)
    private String gender;

//    @Column(name = "profile_picture") todo verify how to add a picture and if its necessary to use another tool to storage it, like S3 from amazon, or it can be saved on db?
//    private String profilePicture;

    @Embedded
    private Address address;

}