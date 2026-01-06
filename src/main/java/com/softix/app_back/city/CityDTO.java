package com.softix.app_back.city;

import com.softix.app_back.country.Country;
import com.softix.app_back.state.State;
import lombok.Data;

@Data
public class CityDTO {

    private String id;
    private String name;

    private String idCountry;
    private Country country;

    private String idState;
    private State state;

    public CityDTO() {}

}
