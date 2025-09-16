package com.softix.app_back.country;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CountryRepository extends JpaRepository<Country, UUID> {

    @Query("SELECT c.id FROM Country c WHERE c.name = :name")
    UUID findIdByName(@Param("name") String name);

}
