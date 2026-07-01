package com.softix.app_back.client;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.softix.app_back.address.AddressDTO;
import com.softix.app_back.payment.PaymentMethod;

import java.util.Date;

public record ClientRequest(
        String name,
        String cpfCnpj,
        String phone,

        @JsonFormat(pattern = "yyyy-MM-dd")
        Date birthDate,

        String gender,
        PaymentMethod preferredPaymentMethod,
        String additionalNotes,
        ClientStatus status,
        String companyId,
        AddressDTO address
) {
}
