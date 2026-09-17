package com.softix.app_back.user;

import lombok.RequiredArgsConstructor;
import com.softix.app_back.permission.CrudAction;
import com.softix.app_back.permission.RequiresPermission;
import com.softix.app_back.permission.SystemModule;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @RequiresPermission(module = SystemModule.USER, action = CrudAction.LIST)
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
