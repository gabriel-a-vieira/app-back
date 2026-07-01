package com.softix.app_back.client;

import com.softix.app_back.address.Address;
import com.softix.app_back.payment.PaymentMethod;
import com.softix.app_back.person.Person;

import java.util.Date;

public record ClientResponse(
        String id,
        String personId,
        String name,
        String cpfCnpj,
        String phone,
        Date birthDate,
        String gender,
        PaymentMethod preferredPaymentMethod,
        String additionalNotes,
        ClientStatus status,
        String companyId,
        String street,
        String number,
        String postalCode,
        String complement,
        String neighborhood,
        String cityId,
        String city,
        String state
) {

    public static ClientResponse fromEntity(Client client) {
        Person person = client.getPerson();
        Address address = person != null ? person.getAddress() : null;

        String cityId = null;
        String cityName = null;
        String stateAbbreviation = null;

        if (address != null && address.getCity() != null) {
            cityId = address.getCity().getId();
            cityName = address.getCity().getName();

            if (address.getCity().getState() != null) {
                stateAbbreviation = address.getCity().getState().getAbbreviation();
            }
        }

        return new ClientResponse(
                client.getId(),
                client.getPersonId(),
                person != null ? person.getName() : null,
                person != null ? person.getCpfCnpj() : null,
                person != null ? person.getPhone() : null,
                person != null ? person.getBirthDate() : null,
                person != null ? person.getGender() : null,
                client.getPreferredPaymentMethod(),
                client.getAdditionalNotes(),
                client.getStatus(),
                client.getCompanyId(),
                address != null ? address.getStreet() : null,
                address != null ? address.getNumber() : null,
                address != null ? address.getPostalCode() : null,
                address != null ? address.getComplement() : null,
                address != null ? address.getNeighborhood() : null,
                cityId,
                cityName,
                stateAbbreviation
        );
    }
}
