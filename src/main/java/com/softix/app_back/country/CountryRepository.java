package com.softix.app_back.country;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CountryRepository extends JpaRepository<Country, String> {

    @Query("SELECT c.id FROM Country c WHERE c.name = :name")
    String findIdByName(@Param("name") String name);

}
