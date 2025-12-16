package com.softix.app_back.person;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public Person save(Person person) {

        validatePerson(person);
        personRepository.save(person);

        return person;

    }

    private void validatePerson(Person person) {

        if (StringUtils.isBlank(person.getCpfCnpj())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF ou CNPJ são obrigatórios!");
        }

    }

}
