package com.softix.app_back.person;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findFirstByCpfCnpj(String cpfCnpj);

}
