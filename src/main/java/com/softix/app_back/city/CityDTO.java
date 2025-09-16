package com.softix.app_back.city;

import com.softix.app_back.country.Country;
import com.softix.app_back.state.State;
import lombok.Data;

import java.util.UUID;

@Data
public class CityDTO {

    private UUID id;
    private String name;

    private UUID idCountry;
    private Country country;

    private UUID idState;
    private State state;

    public CityDTO() {}

}
