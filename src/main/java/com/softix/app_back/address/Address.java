package com.softix.app_back.address;

import com.softix.app_back.city.City;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Embeddable
public class Address {

    @Column(name = "street", length = 100)
    private String street;

    @Column(name = "number", length = 15)
    private String number;

    @Column(name = "postalCode", length = 8)
    private String postalCode;

    @Column(name = "complement", length = 200)
    private String complement;

    @Column(name = "neighborhood", length = 100)
    private String neighborhood;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @ManyToOne
    @JoinColumn(name = "id_city")
    private City city;

}
