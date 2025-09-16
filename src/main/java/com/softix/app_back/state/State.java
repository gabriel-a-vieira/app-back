package com.softix.app_back.state;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;
import utils.model.RootEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "state")
public class State extends RootEntity {

    @Column(name = "name", length = 38)
    private String name;

    @Column(name = "abbreviation", length = 5)
    private String abbreviation;

    public State() {}

    public State(StateDTO dto) {
        BeanUtils.copyProperties(dto, this);
    }

}
