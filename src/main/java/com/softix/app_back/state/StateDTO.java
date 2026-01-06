package com.softix.app_back.state;

import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class StateDTO {

    private String id;
    private String name;
    private String abbreviation;

    public StateDTO() {}

    public StateDTO(State state) {
        BeanUtils.copyProperties(state, this);
    }

}
