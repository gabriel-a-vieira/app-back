package com.softix.app_back.person;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, String> {

    Person findFirstByCpfCnpj(String cpfCnpj);

}
