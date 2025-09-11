package com.softix.app_back.country;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

@Data
public class CountryDTO {

    private UUID id;
    private String name;

    public CountryDTO() {}

    public CountryDTO(Country country) {
        BeanUtils.copyProperties(country, this);
    }

}
