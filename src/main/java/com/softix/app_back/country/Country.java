package com.softix.app_back.country;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;
import utils.model.RootEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "country")
public class Country extends RootEntity {

    @Column(name = "name", length = 100)
    private String name;

    public Country() {}

    public Country(CountryDTO dto) {
        BeanUtils.copyProperties(dto, this);
    }

}
