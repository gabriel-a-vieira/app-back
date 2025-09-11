package com.softix.app_back.country;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

@Data
@Entity
@Table(name = "country")
public class Country {

    @Id
    @GeneratedValue
    @Column(name = "id", length = 38)
    private UUID id;

    @Column(name = "name", length = 100)
    private String name;

    public Country() {}

    public Country(CountryDTO dto) {
        BeanUtils.copyProperties(dto, this);
    }

}
