package com.softix.app_back.permission;

import com.softix.app_back.config.JWTUserData;
import com.softix.app_back.shared.exception.BusinessException;
import com.softix.app_back.user.UserRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Covers PermissionService: the MASTER_ADMIN-only guard on the matrix
 * read/write endpoints, that a never-configured (role, module) combo shows
 * up as fully allowed (matches PermissionAspect's default-allow behavior),
 * the upsert semantics of updateMatrix, and how /me computes the effective
 * permission map per role (full access for MASTER_ADMIN, empty/false for a
 * role that isn't configurable at all, like CLIENT).
 */
@ExtendWith(MockitoExtension.class)
class PermissionServiceTest {

    @Mock
    private RolePermissionRepository rolePermissionRepository;

    @InjectMocks
    private PermissionService permissionService;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    private void authenticateAs(String role) {
        JWTUserData user = JWTUserData.builder().userId("user-1").companyId("company-1").role(role).email("user@softix.com").build();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null));
    }

    @Test
    void findMatrix_throwsForbidden_whenCallerIsNotMasterAdmin() {
        authenticateAs("COMPANY_ADMIN");

        assertThatThrownBy(() -> permissionService.findMatrix())
                .isInstanceOf(BusinessException.class)
                .extracting(ex -> ((BusinessException) ex).getStatus())
                .isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void findMatrix_defaultsUnconfiguredCombinationsToFullyAllowed() {
        authenticateAs("MASTER_ADMIN");

        when(rolePermissionRepository.findAll()).thenReturn(List.of());

        List<RolePermissionResponse> matrix = permissionService.findMatrix();

        assertThat(matrix).hasSize(2 * SystemModule.values().length);
        assertThat(matrix).allSatisfy(entry -> {
            assertThat(entry.canCreate()).isTrue();
            assertThat(entry.canUpdate()).isTrue();
            assertThat(entry.canList()).isTrue();
            assertThat(entry.canDelete()).isTrue();
        });
    }

    @Test
    void findMatrix_reflectsAnExistingRowInsteadOfTheDefault() {
        authenticateAs("MASTER_ADMIN");

        RolePermission restricted = new RolePermission();
        restricted.setRole(UserRole.PROFESSIONAL);
        restricted.setModule(SystemModule.PRODUCT);
        restricted.setCanCreate(false);
        restricted.setCanUpdate(false);
        restricted.setCanList(true);
        restricted.setCanDelete(false);

        when(rolePermissionRepository.findAll()).thenReturn(List.of(restricted));

        List<RolePermissionResponse> matrix = permissionService.findMatrix();

        RolePermissionResponse entry = matrix.stream()
                .filter(r -> r.role() == UserRole.PROFESSIONAL && r.module() == SystemModule.PRODUCT)
                .findFirst()
                .orElseThrow();

        assertThat(entry.canCreate()).isFalse();
        assertThat(entry.canList()).isTrue();
    }

    @Test
    void updateMatrix_throwsForbidden_whenCallerIsNotMasterAdmin() {
        authenticateAs("PROFESSIONAL");

        assertThatThrownBy(() -> permissionService.updateMatrix(List.of()))
                .isInstanceOf(BusinessException.class)
                .extracting(ex -> ((BusinessException) ex).getStatus())
                .isEqualTo(HttpStatus.FORBIDDEN);

        verify(rolePermissionRepository, never()).save(any());
    }

    @Test
    void updateMatrix_createsANewRow_whenNoneExistsYet() {
        authenticateAs("MASTER_ADMIN");

        when(rolePermissionRepository.findByRoleAndModule(UserRole.COMPANY_ADMIN, SystemModule.USER))
                .thenReturn(Optional.empty());

        RolePermissionRequest request = new RolePermissionRequest(UserRole.COMPANY_ADMIN, SystemModule.USER, true, false, true, false);

        permissionService.updateMatrix(List.of(request));

        ArgumentCaptor<RolePermission> captor = ArgumentCaptor.forClass(RolePermission.class);
        verify(rolePermissionRepository).save(captor.capture());

        RolePermission saved = captor.getValue();
        assertThat(saved.getRole()).isEqualTo(UserRole.COMPANY_ADMIN);
        assertThat(saved.getModule()).isEqualTo(SystemModule.USER);
        assertThat(saved.isCanCreate()).isTrue();
        assertThat(saved.isCanUpdate()).isFalse();
        assertThat(saved.isCanList()).isTrue();
        assertThat(saved.isCanDelete()).isFalse();
    }

    @Test
    void updateMatrix_updatesTheExistingRow_insteadOfDuplicatingIt() {
        authenticateAs("MASTER_ADMIN");

        RolePermission existing = new RolePermission();
        existing.setId("existing-id");
        existing.setRole(UserRole.COMPANY_ADMIN);
        existing.setModule(SystemModule.USER);
        existing.setCanCreate(true);
        existing.setCanUpdate(true);
        existing.setCanList(true);
        existing.setCanDelete(true);

        when(rolePermissionRepository.findByRoleAndModule(UserRole.COMPANY_ADMIN, SystemModule.USER))
                .thenReturn(Optional.of(existing));

        RolePermissionRequest request = new RolePermissionRequest(UserRole.COMPANY_ADMIN, SystemModule.USER, false, false, false, false);

        permissionService.updateMatrix(List.of(request));

        ArgumentCaptor<RolePermission> captor = ArgumentCaptor.forClass(RolePermission.class);
        verify(rolePermissionRepository).save(captor.capture());

        assertThat(captor.getValue().getId()).isEqualTo("existing-id");
        assertThat(captor.getValue().isCanCreate()).isFalse();
    }

    @Test
    void findMyPermissions_grantsFullAccessToMasterAdmin() {
        authenticateAs("MASTER_ADMIN");

        Map<SystemModule, ModulePermissionResponse> permissions = permissionService.findMyPermissions();

        assertThat(permissions).hasSize(SystemModule.values().length);
        assertThat(permissions.values()).allSatisfy(p -> assertThat(p.canCreate()).isTrue());

        verify(rolePermissionRepository, never()).findByRoleAndModule(any(), any());
    }

    @Test
    void findMyPermissions_returnsNoAccessForARoleOutsideTheConfigurableSet() {
        authenticateAs("CLIENT");

        Map<SystemModule, ModulePermissionResponse> permissions = permissionService.findMyPermissions();

        assertThat(permissions.values()).allSatisfy(p -> {
            assertThat(p.canCreate()).isFalse();
            assertThat(p.canUpdate()).isFalse();
            assertThat(p.canList()).isFalse();
            assertThat(p.canDelete()).isFalse();
        });
    }

    @Test
    void findMyPermissions_defaultsToFullAccessForAConfigurableRoleWithNoRowYet() {
        authenticateAs("PROFESSIONAL");

        when(rolePermissionRepository.findByRoleAndModule(any(), any())).thenReturn(Optional.empty());

        Map<SystemModule, ModulePermissionResponse> permissions = permissionService.findMyPermissions();

        assertThat(permissions.get(SystemModule.CLIENT).canDelete()).isTrue();
    }

    @Test
    void findMyPermissions_returnsEmptyMapWhenThereIsNoAuthenticatedUser() {
        Map<SystemModule, ModulePermissionResponse> permissions = permissionService.findMyPermissions();

        assertThat(permissions).isEmpty();
    }
}
