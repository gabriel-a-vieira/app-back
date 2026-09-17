package com.softix.app_back.permission;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import utils.model.tenant.TenantEntity;

/**
 * Per-company, per-user config: which CRUD actions a specific user can
 * perform on a module. Tenant-scoped (each company parametrizes its own
 * COMPANY_ADMIN/PROFESSIONAL users independently). A missing row for a given
 * (user, module) means "not restricted yet" and is treated as fully allowed
 * — see PermissionAspect/PermissionService.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "user_permission", uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_permission_user_module", columnNames = {"user_id", "module"})
})
public class UserPermission extends TenantEntity {

    @Column(name = "user_id", nullable = false, length = 38)
    private String userId;

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
