package com.softix.app_back.permission;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @GetMapping
    public Page<UserPermissionProfileResponse> findConfiguredProfiles(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));
        return permissionService.findConfiguredProfiles(search, pageable);
    }

    @GetMapping("/me")
    public Map<SystemModule, ModulePermissionResponse> findMyPermissions() {
        return permissionService.findMyPermissions();
    }

    @GetMapping("/{userId}")
    public List<ModulePermissionEntry> findUserMatrix(@PathVariable String userId) {
        return permissionService.findUserMatrix(userId);
    }

    @PutMapping("/{userId}")
    public void updateUserMatrix(@PathVariable String userId, @RequestBody List<ModulePermissionEntry> entries) {
        permissionService.updateUserMatrix(userId, entries);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfiles(@RequestBody List<String> userIds) {
        permissionService.deleteProfiles(userIds);
    }

}
