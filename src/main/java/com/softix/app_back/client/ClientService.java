package com.softix.app_back.client;

import com.softix.app_back.address.Address;
import com.softix.app_back.address.AddressDTO;
import com.softix.app_back.city.City;
import com.softix.app_back.city.CityRepository;
import com.softix.app_back.company.Company;
import com.softix.app_back.company.CompanyRepository;
import com.softix.app_back.payment.PaymentMethod;
import com.softix.app_back.person.Person;
import com.softix.app_back.person.PersonRepository;
import com.softix.app_back.user.User;
import com.softix.app_back.user.UserRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import utils.security.SecurityUtils;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    CompanyRepository companyRepository;

    public Page<ClientResponse> findAll(
            String search,
            String name,
            String cpfCnpj,
            String phone,
            String city,
            String state,
            String status,
            String preferredPaymentMethod,
            String companyId,
            Pageable pageable
    ) {

        String resolvedCompanyId = SecurityUtils.resolveCompanyId(companyId);

        ClientStatus clientStatus = null;

        if (status != null && !status.isBlank() && !"ALL".equalsIgnoreCase(status)) {
            clientStatus = ClientStatus.valueOf(status.toUpperCase());
        }

        PaymentMethod paymentMethod = null;

        if (preferredPaymentMethod != null && !preferredPaymentMethod.isBlank()) {
            paymentMethod = PaymentMethod.valueOf(preferredPaymentMethod.toUpperCase());
        }

        return clientRepository.findAdvanced(
                resolvedCompanyId,
                search,
                name,
                cpfCnpj,
                phone,
                city,
                state,
                clientStatus,
                paymentMethod,
                pageable
        ).map(ClientResponse::fromEntity);
    }

    public ClientResponse findById(String id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cliente nao encontrado"
                ));

        return ClientResponse.fromEntity(client);
    }

    @Transactional
    public ClientResponse save(ClientRequest request) {

        String companyId = SecurityUtils.resolveCompanyId(request.companyId());

        if (
                request.cpfCnpj() != null &&
                        !request.cpfCnpj().isBlank() &&
                        clientRepository.existsByCompanyIdAndPerson_CpfCnpj(companyId, onlyNumbers(request.cpfCnpj()))
        ) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Cliente ja cadastrado para esta empresa"
            );
        }

        Client client = new Client();

        client.setCompanyId(companyId);
        client.setPerson(resolvePerson(request));
        client.setPreferredPaymentMethod(request.preferredPaymentMethod());
        client.setAdditionalNotes(request.additionalNotes());
        client.setStatus(request.status() != null ? request.status() : ClientStatus.ACTIVE);

        clientRepository.save(client);

        return ClientResponse.fromEntity(client);
    }

    @Transactional
    public ClientResponse update(String id, ClientRequest request) {

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cliente nao encontrado"
                ));

        String cpfCnpj = onlyNumbers(request.cpfCnpj());

        if (
                cpfCnpj != null &&
                        !cpfCnpj.isBlank() &&
                        clientRepository.existsByCompanyIdAndPerson_CpfCnpjAndIdNot(
                                client.getCompanyId(),
                                cpfCnpj,
                                client.getId()
                        )
        ) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "CPF/CNPJ ja utilizado por outro cliente nesta empresa"
            );
        }

        Person person = client.getPerson();

        if (person == null) {
            person = new Person();
        }

        fillPerson(person, request);

        personRepository.save(person);

        client.setPerson(person);
        client.setPreferredPaymentMethod(request.preferredPaymentMethod());
        client.setAdditionalNotes(request.additionalNotes());

        if (request.status() != null) {
            client.setStatus(request.status());
        }

        clientRepository.save(client);

        return ClientResponse.fromEntity(client);
    }

    @Transactional
    public void deleteMany(List<String> ids) {

        if (ids == null || ids.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Nenhum cliente informado"
            );
        }

        List<Client> clients = clientRepository.findByIdIn(ids);

        for (Client client : clients) {
            client.setStatus(ClientStatus.INACTIVE);
        }

        clientRepository.saveAll(clients);
    }

    private Person resolvePerson(ClientRequest request) {
        String cpfCnpj = onlyNumbers(request.cpfCnpj());

        Person person = null;

        if (cpfCnpj != null && !cpfCnpj.isBlank()) {
            person = personRepository.findFirstByCpfCnpj(cpfCnpj);
        }

        if (person == null) {
            person = new Person();
        }

        fillPerson(person, request);

        personRepository.save(person);

        return person;
    }

    private void fillPerson(Person person, ClientRequest request) {
        person.setCpfCnpj(onlyNumbers(request.cpfCnpj()));
        person.setName(request.name());
        person.setPhone(onlyNumbers(request.phone()));
        person.setGender(request.gender());
        person.setBirthDate(request.birthDate());
        person.setAddress(getAddress(request.address()));
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

        if (addressDTO.getIdCity() != null && !addressDTO.getIdCity().isBlank()) {
            city = cityRepository.findById(addressDTO.getIdCity()).orElse(null);
        } else {
            city = cityRepository.findByNameAndStateAbbreviation(
                    addressDTO.getCity(),
                    addressDTO.getState()
            );
        }

        if (city == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Cidade nao encontrada"
            );
        }

        address.setCity(city);

        return address;
    }

    private String onlyNumbers(String value) {
        if (value == null) {
            return null;
        }

        return value.replaceAll("[^0-9]", "");
    }
}