package com.softix.app_back.city;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CityRepository extends JpaRepository<City, UUID> {

    @Query(value = "SELECT c FROM City c JOIN State s ON c.idState = s.id WHERE c.name = :name AND s.abbreviation = :abbreviation")
    City findByNameAndStateAbbreviation(@Param("name") String cityName, @Param("abbreviation") String stateAbbreviation);

}
