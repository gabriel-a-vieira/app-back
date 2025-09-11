package com.softix.app_back.city;

import com.softix.app_back.country.Country;
import com.softix.app_back.state.State;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "city")
public class City {

    @Id
    @GeneratedValue
    @Column(name = "id", length = 38)
    private UUID id;

    @Column(name = "name", length = 100)
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_country")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "id_state")
    private State state;

    public City() {}

}
