package com.softix.app_back.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StateService {

    @Autowired
    StateRepository stateRepository;

    @Transactional
    public State save(StateDTO dto) {

        State state = new State();

        state.setName(dto.getName());
        state.setAbbreviation(dto.getAbbreviation());

        stateRepository.save(state);

        return state;

    }

}
