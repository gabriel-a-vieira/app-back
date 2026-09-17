package com.softix.app_back.permission;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @GetMapping
    public List<RolePermissionResponse> findMatrix() {
        return permissionService.findMatrix();
    }

    @PutMapping
    public void updateMatrix(@RequestBody List<RolePermissionRequest> requests) {
        permissionService.updateMatrix(requests);
    }

    @GetMapping("/me")
    public Map<SystemModule, ModulePermissionResponse> findMyPermissions() {
        return permissionService.findMyPermissions();
    }

}
