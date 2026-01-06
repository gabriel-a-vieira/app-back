package com.softix.app_back.country;

import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class CountryDTO {

    private String id;
    private String name;

    public CountryDTO() {}

    public CountryDTO(Country country) {
        BeanUtils.copyProperties(country, this);
    }

}
