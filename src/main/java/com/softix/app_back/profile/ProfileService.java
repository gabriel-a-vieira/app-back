package com.softix.app_back.profile;

import com.softix.app_back.address.Address;
import com.softix.app_back.auth.external.AuthProvider;
import com.softix.app_back.auth.external.UserExternalIdentityRepository;
import com.softix.app_back.city.City;
import com.softix.app_back.city.CityRepository;
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
    CityRepository cityRepository;
;
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

        if (phone != null && !phone.isEmpty() && (phone.length() < 10 || phone.length() > 11)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Telefone invalido");
        }

        if (request.birthDate() != null && request.birthDate().isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data de nascimento invalida");
        }

        String postalCode = onlyNumbers(request.postalCode());

        if (postalCode != null && !postalCode.isEmpty() && postalCode.length() != 8) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CEP deve possuir 8 digitos");
        }

        validateCoordinates(request.latitude(), request.longitude());

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
        person.setAddress(buildAddress(request, postalCode));
        boolean newPerson = user.getPerson() == null;

        person = personRepository.save(person);

        if (newPerson) {
            user.setPerson(person);
        }

        userRepository.save(user);

        return toDTO(user);

    }


    private Address buildAddress(UpdateMyProfileRequest request, String postalCode) {

        String street = StringUtils.trimToNull(request.street());
        String number = StringUtils.trimToNull(request.number());
        String complement = StringUtils.trimToNull(request.complement());
        String neighborhood = StringUtils.trimToNull(request.neighborhood());
        String cityName = StringUtils.trimToNull(request.city());
        String state = StringUtils.trimToNull(request.state());

        boolean hasAddressData = street != null || number != null || postalCode != null || complement != null || neighborhood != null || cityName != null || state != null || request.latitude() != null || request.longitude() != null;

        if (!hasAddressData) {
            return null;
        }

        City city = resolveCity(cityName, state);

        Address address = new Address();

        address.setStreet(street);
        address.setNumber(number);
        address.setPostalCode(StringUtils.trimToNull(postalCode));
        address.setComplement(complement);
        address.setNeighborhood(neighborhood);
        address.setLatitude(request.latitude());
        address.setLongitude(request.longitude());
        address.setCity(city);

        return address;
    }


    private City resolveCity(String cityName, String state) {

        if (cityName == null && state == null) {
            return null;
        }

        if (cityName == null || state == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cidade e UF devem ser informadas juntas");
        }

        City city = cityRepository.findByNameAndStateAbbreviation(cityName, state);

        if (city == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cidade nao encontrada");
        }

        return city;

    }


    private MyProfileDTO toDTO(User user) {

        Person person = user.getPerson();
        Address address = person != null ? person.getAddress() : null;
        City city = address != null ? address.getCity() : null;

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
                address != null ? address.getStreet() : null,
                address != null ? address.getNumber() : null,
                address != null ? address.getPostalCode() : null,
                address != null ? address.getComplement() : null,
                address != null ? address.getNeighborhood() : null,
                address != null ? address.getLatitude() : null,
                address != null ? address.getLongitude() : null,
                city != null ? city.getName() : null,
                city != null && city.getState() != null ? city.getState().getAbbreviation() : null,
                googleLinked,
                completed);
    }


    private boolean isPersonalDataCompleted(User user, Person person) {

        if (user.getName() == null || user.getName().isBlank()) {
            return false;
        }


        if (person == null) {
            return false;
        }


        if (person.getCpfCnpj() == null || person.getCpfCnpj().isBlank() || person.getPhone() == null || person.getPhone().isBlank() || person.getBirthDate() == null) {
            return false;
        }

        Address address = person.getAddress();

        if (address == null) {
            return false;
        }


        return address.getPostalCode() != null && !address.getPostalCode().isBlank() && address.getStreet() != null && !address.getStreet().isBlank() && address.getNumber() != null && !address.getNumber().isBlank() && address.getNeighborhood() != null && !address.getNeighborhood().isBlank() && address.getCity() != null;

    }

    private void validateCoordinates(Double latitude, Double longitude) {

        if (latitude != null && (latitude < -90 || latitude > 90)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Latitude invalida");
        }

        if (longitude != null && (longitude < -180 || longitude > 180)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Longitude invalida");
        }

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