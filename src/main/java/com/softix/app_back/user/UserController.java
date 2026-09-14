package com.softix.app_back.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
    public List<User> getAll() {
        return userRepository.findAllUnfiltered();
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
