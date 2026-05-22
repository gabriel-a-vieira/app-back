package com.softix.app_back.professional;

import com.softix.app_back.address.Address;
import com.softix.app_back.address.AddressDTO;
import com.softix.app_back.city.City;
import com.softix.app_back.city.CityRepository;
import com.softix.app_back.person.Person;
import com.softix.app_back.person.PersonRepository;
import com.softix.app_back.person.PersonService;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.softix.app_back.professional.ProfessionalStatus.ACTIVE;

@Service
public class ProfessionalService {

    @Autowired
    ProfessionalRepository professionalRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PersonService personService;

    @Autowired
    CityRepository cityRepository;

    public Page<ProfessionalResponse> findAll(String search, Pageable pageable) {

        Page<Professional> professionals;

        if (StringUtils.isBlank(search)) {
            professionals = professionalRepository.findByStatus(ACTIVE, pageable);
        } else {
            professionals = professionalRepository.findByStatusAndPersonNameContainingIgnoreCase(ACTIVE,search.trim(),pageable);
        }

        return professionals.map(ProfessionalResponse::fromEntity);

    }

    public ProfessionalResponse findById(String id) {

        Professional professional = professionalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profissional nao encontrado"));

        return ProfessionalResponse.fromEntity(professional);

    }

    @Transactional
    public Professional save(ProfessionalDTO dto) {

        Professional professional = new Professional();

        if (dto.getPersonId() != null) {

            if (BooleanUtils.isTrue(personRepository.existsById(dto.getPersonId()))) {

                Person person = personRepository.findById(dto.getPersonId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pessoa nao encontrada"));

                professional.setPerson(person);

            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pessoa nao encontrada");
            }

        } else {
            professional.setPerson(setPerson(dto));
        }

        professional.setStatus(dto.getStatus() != null ? dto.getStatus() : ACTIVE);

        return professionalRepository.save(professional);

    }

    @Transactional
    public Professional update(String id, ProfessionalDTO dto) {

        Professional professional = professionalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profissional nao encontrado"));

        Person person = professional.getPerson();

        if (person == null) {
            person = new Person();
        }

        person.setCpfCnpj(StringUtils.getDigits(dto.getCpfCnpj()));
        person.setName(dto.getName());
        person.setPhone(StringUtils.getDigits(dto.getPhone()));
        person.setGender(dto.getGender());
        person.setBirthDate(dto.getBirthDate());
        person.setAddress(getAddress(dto.getAddress()));

        personService.save(person);

        professional.setPerson(person);
        professional.setStatus(dto.getStatus() != null ? dto.getStatus() : ACTIVE);

        return professionalRepository.save(professional);

    }

    @Transactional
    public void deleteMany(List<String> ids) {

        if (ids == null || ids.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhum profissional informado");
        }

        List<Professional> professionals = professionalRepository.findByIdIn(ids);

        for (Professional professional : professionals) {
            professional.setStatus(ProfessionalStatus.INACTIVE);
        }

        professionalRepository.saveAll(professionals);

    }

    private Person setPerson(ProfessionalDTO dto) {

        String cpfCnpj = StringUtils.getDigits(dto.getCpfCnpj());

        Person person = personRepository.findFirstByCpfCnpj(cpfCnpj);

        if (person == null) {
            person = new Person();

            person.setCpfCnpj(cpfCnpj);
            person.setName(dto.getName());
            person.setPhone(StringUtils.getDigits(dto.getPhone()));
            person.setGender(dto.getGender());
            person.setBirthDate(dto.getBirthDate());
            person.setAddress(getAddress(dto.getAddress()));

            personService.save(person);
        }

        return person;

    }

    private Address getAddress(AddressDTO addressDTO) {

        if (addressDTO == null) {
            return null;
        }

        Address address = new Address();

        address.setNumber(addressDTO.getNumber());
        address.setStreet(addressDTO.getStreet());
        address.setComplement(addressDTO.getComplement());
        address.setPostalCode(StringUtils.getDigits(addressDTO.getPostalCode()));
        address.setNeighborhood(addressDTO.getNeighborhood());
        address.setLatitude(addressDTO.getLatitude());
        address.setLongitude(addressDTO.getLongitude());

        City city;

        if (addressDTO.getIdCity() != null) {
            city = cityRepository.findById(addressDTO.getIdCity()).orElse(null);
        } else {
            city = cityRepository.findByNameAndStateAbbreviation(addressDTO.getCity(),addressDTO.getState());
        }

        if (city != null) {
            address.setCity(city);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cidade nao encontrada");
        }

        return address;

    }

}