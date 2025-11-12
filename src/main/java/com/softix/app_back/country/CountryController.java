package com.softix.app_back.country;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/country")
public class CountryController {

    @Autowired
    CountryService countryService;

    @Autowired
    CountryRepository countryRepository;

    @PostMapping
    public ResponseEntity post(@RequestBody String jsonBody) {

        if (jsonBody == null) return ResponseEntity.badRequest().body("Invalid/Null JSON");

        ObjectMapper objectMapper = new ObjectMapper();
        CountryDTO dto;

        try {
            dto = objectMapper.readValue(jsonBody, CountryDTO.class);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error parsing JSON");
        }

        return ResponseEntity.ok(countryService.save(dto));

    }

}
