package com.softix.app_back.professional;

import com.softix.app_back.address.Address;
import com.softix.app_back.person.Person;

import java.util.Date;

public record ProfessionalResponse(
        String id,
        String personId,
        String name,
        String cpfCnpj,
        String phone,
        Date birthDate,
        String gender,
        ProfessionalStatus status,
        String street,
        String number,
        String postalCode,
        String complement,
        String neighborhood,
        String city,
        String state
) {

    public static ProfessionalResponse fromEntity(Professional professional) {

        Person person = professional.getPerson();
        Address address = person != null ? person.getAddress() : null;

        String cityName = null;
        String stateAbbreviation = null;

        if (address != null && address.getCity() != null) {

            cityName = address.getCity().getName();

            if (address.getCity().getState() != null) {
                stateAbbreviation = address.getCity().getState().getAbbreviation();
            }

        }

        return new ProfessionalResponse(
                professional.getId(),
                professional.getPersonId(),
                person != null ? person.getName() : null,
                person != null ? person.getCpfCnpj() : null,
                person != null ? person.getPhone() : null,
                person != null ? person.getBirthDate() : null,
                person != null ? person.getGender() : null,
                professional.getStatus(),
                address != null ? address.getStreet() : null,
                address != null ? address.getNumber() : null,
                address != null ? address.getPostalCode() : null,
                address != null ? address.getComplement() : null,
                address != null ? address.getNeighborhood() : null,
                cityName,
                stateAbbreviation
        );

    }

}