package com.softix.app_back.state;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

@Data
@Entity
@Table(name = "state")
public class State {

    @Id
    @GeneratedValue
    @Column(name = "id", length = 38)
    private UUID id;

    @Column(name = "name", length = 38)
    private String name;

    @Column(name = "abbreviation", length = 5)
    private String abbreviation;

    public State() {}

    public State(StateDTO dto) {
        BeanUtils.copyProperties(dto, this);
    }

}
