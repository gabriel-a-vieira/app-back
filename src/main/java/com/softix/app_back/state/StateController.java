package com.softix.app_back.state;

import lombok.RequiredArgsConstructor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softix.app_back.company.Company;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/state")
@RequiredArgsConstructor
public class StateController {

    private final StateService stateService;

    private final StateRepository stateRepository;

    @GetMapping
    public List<State> findAll() {
        return stateRepository.findAll();
    }

    @PostMapping
    public ResponseEntity post(@RequestBody String jsonBody) {

        if (jsonBody == null) return ResponseEntity.badRequest().body("Invalid JSON");

        ObjectMapper objectMapper = new ObjectMapper();
        StateDTO dto;

        try {
            dto = objectMapper.readValue(jsonBody, StateDTO.class);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error parsing JSON");
        }

        return ResponseEntity.ok(stateService.save(dto));

    }

}
