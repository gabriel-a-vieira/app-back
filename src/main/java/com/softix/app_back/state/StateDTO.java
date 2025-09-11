package com.softix.app_back.state;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

@Data
public class StateDTO {

    private UUID id;
    private String name;
    private String abbreviation;

    public StateDTO() {}

    public StateDTO(State state) {
        BeanUtils.copyProperties(state, this);
    }

}
