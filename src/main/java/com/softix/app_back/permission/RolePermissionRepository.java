package com.softix.app_back.permission;

import com.softix.app_back.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolePermissionRepository extends JpaRepository<RolePermission, String> {

    Optional<RolePermission> findByRoleAndModule(UserRole role, SystemModule module);

}
