package com.softix.app_back.professional;

import lombok.RequiredArgsConstructor;
import com.softix.app_back.address.Address;
import com.softix.app_back.address.AddressDTO;
import com.softix.app_back.city.City;
import com.softix.app_back.city.CityRepository;
import com.softix.app_back.person.Person;
import com.softix.app_back.person.PersonRepository;
import com.softix.app_back.person.PersonService;
import com.softix.app_back.user.User;
import com.softix.app_back.user.UserRepository;
import com.softix.app_back.user.UserRole;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.softix.app_back.shared.exception.BusinessException;
import utils.security.SecurityUtils;

import java.util.List;

import static com.softix.app_back.professional.ProfessionalStatus.ACTIVE;

@Service
@RequiredArgsConstructor
public class ProfessionalService {

    private final ProfessionalRepository professionalRepository;

    private final PersonRepository personRepository;

    private final PersonService personService;

    private final CityRepository cityRepository;

    private final UserRepository userRepository;

    public Page<ProfessionalResponse> findAll(String search, String name, String cpfCnpj,
                                              String phone, String city, String state,
                                              String status, Pageable pageable) {

        ProfessionalStatus professionalStatus = null;

        if (status != null && !status.isBlank() && !"ALL".equalsIgnoreCase(status)) {
            professionalStatus = ProfessionalStatus.valueOf(status.toUpperCase());
        }

        return professionalRepository.findAdvanced(search, name, cpfCnpj, phone, city, state, professionalStatus, pageable)
                .map(this::toResponse);

    }

    public ProfessionalResponse findById(String id) {

        Professional professional = professionalRepository.findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Profissional nao encontrado"));

        return toResponse(professional);

    }

    private ProfessionalResponse toResponse(Professional professional) {
        User linkedUser = professional.getUserId() != null
                ? userRepository.findById(professional.getUserId()).orElse(null)
                : null;

        return ProfessionalResponse.fromEntity(professional, linkedUser);
    }

    private String resolveUserId(String userId, String companyId, String currentProfessionalId) {

        if (userId == null || userId.isBlank()) {
            return null;
        }

        User user = userRepository.findByIdAndCompanyId(userId, companyId)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Usuario nao encontrado"));

        if (user.getRole() != UserRole.PROFESSIONAL) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Usuario selecionado nao e do tipo Profissional");
        }

        boolean alreadyLinkedToAnother = currentProfessionalId != null
                ? professionalRepository.existsByCompanyIdAndUserIdAndIdNot(companyId, userId, currentProfessionalId)
                : professionalRepository.findByCompanyIdAndUserId(companyId, userId).isPresent();

        if (alreadyLinkedToAnother) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Usuario ja vinculado a outro profissional");
        }

        return user.getId();
    }

    @Transactional
    public ProfessionalResponse save(ProfessionalDTO dto) {

        Professional professional = new Professional();

        if (dto.getPersonId() != null) {

            if (BooleanUtils.isTrue(personRepository.existsById(dto.getPersonId()))) {

                Person person = personRepository.findById(dto.getPersonId())
                        .orElseThrow(() -> new BusinessException(HttpStatus.BAD_REQUEST, "Pessoa nao encontrada"));

                professional.setPerson(person);

            } else {
                throw new BusinessException(HttpStatus.BAD_REQUEST, "Pessoa nao encontrada");
            }

        } else {
            professional.setPerson(setPerson(dto));
        }

        professional.setStatus(dto.getStatus() != null ? dto.getStatus() : ACTIVE);
        professional.setUserId(resolveUserId(dto.getUserId(), SecurityUtils.resolveCompanyId(null), null));

        professionalRepository.save(professional);

        return toResponse(professional);

    }

    @Transactional
    public ProfessionalResponse update(String id, ProfessionalDTO dto) {

        Professional professional = professionalRepository.findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Profissional nao encontrado"));

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
        professional.setUserId(resolveUserId(dto.getUserId(), professional.getCompanyId(), professional.getId()));

        professionalRepository.save(professional);

        return toResponse(professional);

    }

    @Transactional
    public void deleteMany(List<String> ids) {

        if (ids == null || ids.isEmpty()) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Nenhum profissional informado");
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
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Cidade nao encontrada");
        }

        return address;

    }

}