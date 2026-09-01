package com.softix.app_back.company;

import com.softix.app_back.address.Address;
import com.softix.app_back.payment.PaymentMethod;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import utils.model.RootEntity;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "company")
public class Company extends RootEntity {

    @Column(name = "legal_name")
    private String legalName;

    @Column(name = "trade_name")
    private String tradeName;

    @Column(name = "cnpj", length = 14)
    private String cnpj;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private CompanyType type;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CompanyStatus status = CompanyStatus.ACTIVE;

    @Embedded
    private Address address;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "instagram_url")
    private String instagramUrl;

    @Column(name = "facebook_url")
    private String facebookUrl;

    @Column(name = "website_url")
    private String websiteUrl;

    @Column(name = "tiktok_url")
    private String tiktokUrl;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "company_payment_method", joinColumns = @JoinColumn(name = "company_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private Set<PaymentMethod> paymentMethods = new LinkedHashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "company_amenity", joinColumns = @JoinColumn(name = "company_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "amenity", nullable = false)
    private Set<CompanyAmenity> amenities = new LinkedHashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "company_opening_hour", joinColumns = @JoinColumn(name = "company_id"))
    private List<CompanyOpeningHour> openingHours = new ArrayList<>();

}