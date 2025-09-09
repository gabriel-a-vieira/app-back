package com.softix.app_back.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("client")
public class ClientController {

    @Autowired
    ClientService clientService;

    @GetMapping
    public String getAll() {
        return "GET";
    }

    @GetMapping(path = "{id}")
    public String getById(@PathVariable("id") String id) {
        return id;
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
