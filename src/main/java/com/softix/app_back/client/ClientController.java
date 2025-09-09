package com.softix.app_back.client;

import org.springframework.beans.factory.annotation.Autowired;
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
    public String post() {
        return "POST";
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
