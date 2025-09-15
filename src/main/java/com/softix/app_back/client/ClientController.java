package com.softix.app_back.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("client")
public class ClientController {

    @Autowired
    ClientService clientService;

    @Autowired
    ClientRepository clientRepository;

    @GetMapping
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @GetMapping(path = "{id}")
    public Optional<Client> getById(@PathVariable("id") UUID id) {
        return clientRepository.findById(id);
    }

    @PostMapping
    public ResponseEntity post(@RequestBody String jsonBody) {

        if (jsonBody == null) return ResponseEntity.badRequest().body("Invalid JSON");

        ObjectMapper objectMapper = new ObjectMapper();
        ClientDTO dto = null;

        try {
            dto = objectMapper.readValue(jsonBody, ClientDTO.class);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error parsing JSON");
        }

        return ResponseEntity.ok(clientService.save(dto));

    }

    @PutMapping
    public String put() {
        return "PUT";
    }

    @DeleteMapping
    public String delete() {
        return "DELETE";
    }

}
