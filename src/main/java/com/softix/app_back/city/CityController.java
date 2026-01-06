package com.softix.app_back.city;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/city")
public class CityController {

    @Autowired
    CityService cityService;

    @Autowired
    CityRepository cityRepository;

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

}
