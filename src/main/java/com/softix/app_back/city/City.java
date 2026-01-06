package com.softix.app_back.city;

import com.softix.app_back.country.Country;
import com.softix.app_back.state.State;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import utils.model.RootEntity;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "city")
public class City extends RootEntity {

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "id_country", length = 38)
    private String idCountry;

    @ManyToOne
    @JoinColumn(name = "id_country", insertable = false, updatable = false)
    private Country country;

    @Column(name = "id_state", length = 38)
    private String idState;

    @ManyToOne
    @JoinColumn(name = "id_state", insertable = false, updatable = false)
    private State state;

    public City() {}

    @PrePersist
    public void prePersist() {

        if (getCountry() != null) {
            setIdCountry(idCountry);
        }

        if (getState() != null) {
            setIdState(getIdState());
        }

        if (getCreatedAt() == null) { //TODO Verify why its not working only in this table to call @PrePersist from RootEntity, i have to put the createdAt logic here, because its non-null field
            setCreatedAt(new Date());
        }

    }

}
