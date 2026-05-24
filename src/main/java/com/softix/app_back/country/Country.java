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

    @Column(name = "abbreviation", length = 5)
    private String abbreviation;

    @Column(name = "country_code", length = 5)
    private String countryCode;

    @Column(name = "iso_n3_code", length = 5)
    private String isoN3Code;

    @Column(name = "iso_a3_code", length = 5)
    private String isoA3Code;

    @Column(name = "name_en", length = 100)
    private String nameEn;

    @Column(name = "name_es", length = 100)
    private String nameEs;

    public Country() {}

    public Country(CountryDTO dto) {
        BeanUtils.copyProperties(dto, this);
    }

}