package com.softix.app_back.profile;

import com.softix.app_back.auth.external.AuthProvider;
import com.softix.app_back.auth.external.UserExternalIdentityRepository;
import com.softix.app_back.person.Person;
import com.softix.app_back.person.PersonRepository;
import com.softix.app_back.user.User;
import com.softix.app_back.user.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import utils.security.SecurityUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
public class ProfileService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    UserExternalIdentityRepository externalIdentityRepository;

    @Transactional(readOnly = true)
    public MyProfileDTO findMyProfile() {

        String userId = getCurrentUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario nao encontrado"));

        return toDTO(user);

    }


    @Transactional
    public MyProfileDTO updateMyProfile(UpdateMyProfileRequest request) {

        String userId = getCurrentUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario nao encontrado"));
        String name = StringUtils.trimToNull(request.name());

        if (name == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome obrigatorio");
        }

        String cpfCnpj = onlyNumbers(request.cpfCnpj());

        if (cpfCnpj != null && !cpfCnpj.isEmpty() && cpfCnpj.length() != 11) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF deve possuir 11 digitos");
        }

        String phone = onlyNumbers(request.phone());

        if (phone != null && !phone.isEmpty() && phone.length() < 10) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Telefone invalido");
        }

        if (request.birthDate() != null && request.birthDate().isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data de nascimento invalida");
        }

        user.setName(name);

        Person person = user.getPerson();

        if (person == null) {

            person = new Person();
            person.setCompanyId(user.getCompanyId());
        }

        person.setName(name);
        person.setCpfCnpj(StringUtils.trimToNull(cpfCnpj));
        person.setPhone(StringUtils.trimToNull(phone));
        person.setBirthDate(toDate(request.birthDate()));
        person.setGender(StringUtils.trimToNull(request.gender()));
        person = personRepository.save(person);

        if (user.getPerson() == null || !person.getId().equals(user.getPersonId())) {
            user.setPerson(person);
        }

        userRepository.save(user);

        return toDTO(user);
    }


    private MyProfileDTO toDTO(User user) {

        Person person = user.getPerson();

        boolean googleLinked = externalIdentityRepository.existsByUserIdAndProvider(user.getId(), AuthProvider.GOOGLE);

        boolean completed = isPersonalDataCompleted(user, person);

        return new MyProfileDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole() != null ? user.getRole().name() : null,
                person != null ? person.getId() : null,
                person != null ? person.getCpfCnpj() : null,
                person != null ? person.getPhone() : null,
                person != null ? toLocalDate(person.getBirthDate()) : null,
                person != null ? person.getGender() : null,
                googleLinked,
                completed
        );

    }


    private boolean isPersonalDataCompleted(User user, Person person) {

        if (user.getName() == null || user.getName().isBlank()) {
            return false;
        }


        if (person == null) {
            return false;
        }


        return person.getCpfCnpj() != null && !person.getCpfCnpj().isBlank() && person.getPhone() != null && !person.getPhone().isBlank() && person.getBirthDate() != null;

    }


    private String getCurrentUserId() {

        String userId = SecurityUtils.userId();

        if (userId == null || userId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario nao autenticado");
        }

        return userId;
    }


    private String onlyNumbers(String value) {

        if (value == null || value.isBlank()) {
            return null;
        }

        return value.replaceAll("\\D", "");

    }


    private Date toDate(LocalDate value) {

        if (value == null) {
            return null;
        }

        return Date.from(value.atStartOfDay(ZoneId.systemDefault()).toInstant());

    }


    private LocalDate toLocalDate(Date value) {

        if (value == null) {
            return null;
        }

        return Instant.ofEpochMilli(value.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();

    }

}