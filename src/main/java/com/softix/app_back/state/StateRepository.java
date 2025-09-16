package com.softix.app_back.state;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface StateRepository extends JpaRepository<State, UUID> {

    @Query("SELECT s.id FROM State s WHERE s.name = :name AND s.abbreviation = :abbreviation")
    UUID findIdByNameAndAbbreviation(@Param("name") String name, @Param("abbreviation") String abbreviation);

}

