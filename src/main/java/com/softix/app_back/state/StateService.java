package com.softix.app_back.state;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StateService {

    private final StateRepository stateRepository;

    @Transactional
    public State save(StateDTO dto) {

        State state = new State();

        state.setName(dto.getName());
        state.setAbbreviation(dto.getAbbreviation());

        stateRepository.save(state);

        return state;

    }

}
