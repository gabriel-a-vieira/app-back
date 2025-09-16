package com.softix.app_back.client;

import com.softix.app_back.address.Address;
import com.softix.app_back.address.AddressDTO;
import com.softix.app_back.city.City;
import com.softix.app_back.city.CityRepository;
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

    @Transactional
    public Client save(ClientDTO dto) {

        Client client = new Client();

        client.setId(UUID.randomUUID());
        client.setName(dto.getName());
        client.setEmail(dto.getEmail());
        client.setPhone(dto.getPhone());
        client.setAddress(getAddress(dto.getAddress()));

        clientRepository.save(client);

        return client;

    }

    @Transactional
    public Address getAddress(AddressDTO addressDTO) {

        Address address = new Address();

        if (addressDTO != null) {

            address.setNumber(addressDTO.getNumber());
            address.setStreet(addressDTO.getStreet());
            address.setComplement(addressDTO.getComplement());
            address.setPostalCode(addressDTO.getPostalCode());
            address.setNeighborhood(addressDTO.getNeighborhood());
            address.setLatitude(addressDTO.getLatitude());
            address.setLongitude(addressDTO.getLongitude());

            if (addressDTO.getCity() != null) {

                City city = null;

                if (addressDTO.getCity().getId() != null) {
                    city = cityRepository.findById(addressDTO.getCity().getId()).orElse(null);
                } else {
                    city = cityRepository.findByNameAndStateAbbreviation(addressDTO.getCity().getName(), addressDTO.getState().getAbbreviation());
                }

                if (city != null) {
                    address.setCity(city);
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "City not found!");
                }

            }

        }

        return address;

    }

}
