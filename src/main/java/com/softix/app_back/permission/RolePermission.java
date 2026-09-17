package com.softix.app_back.permission;

import com.softix.app_back.user.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import utils.model.RootEntity;

/**
 * Global (not tenant-scoped) config: which CRUD actions a role can perform on
 * a module. A missing row for a given (role, module) means "not restricted
 * yet" and is treated as fully allowed — see PermissionAspect/PermissionService.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "role_permission", uniqueConstraints = {
        @UniqueConstraint(name = "uk_role_permission_role_module", columnNames = {"role", "module"})
})
public class RolePermission extends RootEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 30)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "module", nullable = false, length = 30)
    private SystemModule module;

    @Column(name = "can_create", nullable = false)
    private boolean canCreate;

    @Column(name = "can_update", nullable = false)
    private boolean canUpdate;

    @Column(name = "can_list", nullable = false)
    private boolean canList;

    @Column(name = "can_delete", nullable = false)
    private boolean canDelete;

}
