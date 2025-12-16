package com.softix.app_back.client;

import com.softix.app_back.address.Address;
import com.softix.app_back.address.AddressDTO;
import com.softix.app_back.city.City;
import com.softix.app_back.city.CityRepository;
import com.softix.app_back.person.Person;
import com.softix.app_back.person.PersonRepository;
import com.softix.app_back.person.PersonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PersonService personService;

    @Transactional
    public Client save(ClientDTO dto) {

        Client client = new Client();

        client.setPerson(setPerson(client, dto));
        client.setPreferredPaymentMethod(dto.getPrefferedPaymentMethod());
        client.setAdditionalNotes(dto.getAdditionalNotes());

        clientRepository.save(client);

        return client;

    }

    private Person setPerson(Client client, ClientDTO dto) {

        Person person = personRepository.findFirstByCpfCnpj(dto.getCpfCnpj());

        if (person != null) {
            client.setPerson(person);
        } else {

            person = new Person();

            person.setCpfCnpj(dto.getCpfCnpj());
            person.setName(dto.getName());
            person.setPhone(dto.getPhone());
            person.setGender(dto.getGender());
            person.setBirthDate(dto.getBirthDate());
            person.setAddress(getAddress(dto.getAddress()));

            personService.save(person);

        }

        return person;

    }

    public Address getAddress(AddressDTO addressDTO) {

        Address address = new Address();

        if (addressDTO != null) {

            address.setNumber(addressDTO.getNumber());
            address.setStreet(addressDTO.getStreet());
            address.setComplement(addressDTO.getComplement());
            address.setPostalCode(StringUtils.getDigits(addressDTO.getPostalCode()));
            address.setNeighborhood(addressDTO.getNeighborhood());
            address.setLatitude(addressDTO.getLatitude());
            address.setLongitude(addressDTO.getLongitude());

            City city = null;

            if (addressDTO.getIdCity() != null) {
                city = cityRepository.findById(UUID.fromString(addressDTO.getIdCity())).orElse(null);
            } else {
                city = cityRepository.findByNameAndStateAbbreviation(addressDTO.getCity(), addressDTO.getState());
            }

            if (city != null) {
                address.setCity(city);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cidade não encontrada!");
            }

        }

        return address;

    }

}
