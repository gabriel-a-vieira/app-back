package com.softix.app_back.client;

import com.softix.app_back.address.Address;
import com.softix.app_back.address.AddressDTO;
import com.softix.app_back.city.CityRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

                if (addressDTO.getCity().getId() != null) {
                    address.setIdCity(addressDTO.getCity().getId());
                } else {

                    UUID idCity = cityRepository.findIdByCityNameAndStateAbbreviation(addressDTO.getCity().getName(), addressDTO.getState().getAbbreviation());

                    if (idCity != null) {
                        address.setIdCity(idCity);
                    }

                }

            }

        }

        return address;

    }

}
