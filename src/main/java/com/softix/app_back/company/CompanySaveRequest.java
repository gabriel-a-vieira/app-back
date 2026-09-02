package com.softix.app_back.company;

import com.softix.app_back.payment.PaymentMethod;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Data
public class CompanySaveRequest {

    private String legalName;
    private String tradeName;
    private String cnpj;

    private CompanyType type;
    private CompanyStatus status;

    private String imageUrl;

    private String zipCode;
    private String street;
    private String number;
    private String district;
    private String city;
    private String state;
    private String complement;

    private String instagramUrl;
    private String facebookUrl;
    private String websiteUrl;
    private String tiktokUrl;

    private Set<PaymentMethod> paymentMethods =
            new LinkedHashSet<>();

    private Set<CompanyAmenity> amenities =
            new LinkedHashSet<>();

    private List<CompanyOpeningHourRequest> openingHours =
            new ArrayList<>();

}