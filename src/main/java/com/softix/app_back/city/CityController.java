package com.softix.app_back.city;

import lombok.RequiredArgsConstructor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/city")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    private final CityRepository cityRepository;

    @GetMapping
    public List<City> findAll() {
        return cityRepository.findAll();
    }

    @GetMapping(path = "{id}")
    public City findById(@PathVariable("id") String id) {
        return cityRepository.findById(id).orElse(null);
    }

    @PostMapping
    public ResponseEntity post(@RequestBody String jsonBody) {

        if (jsonBody == null) return ResponseEntity.badRequest().body("Invalid JSON");

        ObjectMapper objectMapper = new ObjectMapper();
        CityDTO dto;

        try {
            dto = objectMapper.readValue(jsonBody, CityDTO.class);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error parsing JSON");
        }

        return ResponseEntity.ok(cityService.save(dto));

    }

    @GetMapping(path = "/state")
    public List<CityResponse> findByState(@RequestParam String state) {
        return cityService.findByState(state);
    }

}
