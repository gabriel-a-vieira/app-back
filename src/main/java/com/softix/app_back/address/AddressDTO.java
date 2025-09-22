package com.softix.app_back.address;

import com.softix.app_back.city.City;
import com.softix.app_back.city.CityDTO;
import com.softix.app_back.country.Country;
import com.softix.app_back.country.CountryDTO;
import com.softix.app_back.state.State;
import com.softix.app_back.state.StateDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Embeddable
public class AddressDTO {

    private String street;
    private String number;
    private String postalCode;
    private String complement;
    private String neighborhood;
    private Double latitude;
    private Double longitude;

    private String idCity;
    private String city;

    private String idState;
    private String state;

    private String idCountry;
    private String country;

}