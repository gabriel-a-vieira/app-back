package com.softix.app_back.person;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.softix.app_back.shared.exception.BusinessException;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public Person save(Person person) {

        validatePerson(person);
        personRepository.save(person);

        return person;

    }

    private void validatePerson(Person person) {

//TODO Verify if its necessary tto validate this field, because, it's not been asked on user registration
//        if (StringUtils.isBlank(person.getCpfCnpj())) {
//            throw new BusinessException(HttpStatus.BAD_REQUEST, "CPF ou CNPJ são obrigatórios!");
//        }

    }

}
