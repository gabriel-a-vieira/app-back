package com.softix.app_back.city;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface CityRepository extends JpaRepository<City, UUID> {

    City findByNameAndStateAbbreviation(String cityName, String stateAbbreviation);

}
