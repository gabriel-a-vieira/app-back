package com.softix.app_back.permission;

import com.softix.app_back.config.JWTUserData;
import com.softix.app_back.shared.exception.BusinessException;
import com.softix.app_back.user.User;
import com.softix.app_back.user.UserRepository;
import com.softix.app_back.user.UserRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Covers PermissionService's per-user, per-company model: only a
 * COMPANY_ADMIN can manage the cadastro, a target user must belong to the
 * caller's company and be COMPANY_ADMIN/PROFESSIONAL, a never-configured
 * module defaults to fully allowed, the upsert semantics of
 * updateUserMatrix, and how /me computes the effective permission map for
 * the logged-in user (full access for MASTER_ADMIN, no access for a role
 * outside the configurable set, like CLIENT).
 */
@ExtendWith(MockitoExtension.class)
class PermissionServiceTest {

    private static final String TARGET_USER_ID = "target-user-1";

    @Mock
    private UserPermissionRepository userPermissionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PermissionService permissionService;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    private void authenticateAs(String role) {
        JWTUserData user = JWTUserData.builder().userId("caller-1").companyId("company-1").role(role).email("caller@softix.com").build();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null));
    }

    private User buildTargetUser(UserRole role) {
        User user = new User();
        user.setId(TARGET_USER_ID);
        user.setName("Profissional Teste");
        user.setEmail("profissional@softix.com");
        user.setRole(role);
        return user;
    }

    @Test
    void findConfiguredProfiles_throwsForbidden_whenCallerIsNotCompanyAdmin() {
        authenticateAs("PROFESSIONAL");

        Pageable pageable = PageRequest.of(0, 10);

        assertThatThrownBy(() -> permissionService.findConfiguredProfiles(null, pageable))
                .isInstanceOf(BusinessException.class)
                .extracting(ex -> ((BusinessException) ex).getStatus())
                .isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void findConfiguredProfiles_returnsEmptyPage_whenNoUserHasAnyRowConfigured() {
        authenticateAs("COMPANY_ADMIN");

        when(userPermissionRepository.findDistinctUserIds()).thenReturn(List.of());

        var result = permissionService.findConfiguredProfiles(null, PageRequest.of(0, 10));

        assertThat(result.getContent()).isEmpty();
        verify(userRepository, never()).findByIdInAndSearch(any(), any(), any());
    }

    @Test
    void findConfiguredProfiles_mapsConfiguredUsersToProfiles() {
        authenticateAs("COMPANY_ADMIN");

        when(userPermissionRepository.findDistinctUserIds()).thenReturn(List.of(TARGET_USER_ID));

        User user = buildTargetUser(UserRole.PROFESSIONAL);
        Pageable pageable = PageRequest.of(0, 10);

        when(userRepository.findByIdInAndSearch(List.of(TARGET_USER_ID), null, pageable))
                .thenReturn(new PageImpl<>(List.of(user)));

        var result = permissionService.findConfiguredProfiles(null, pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).userId()).isEqualTo(TARGET_USER_ID);
        assertThat(result.getContent().get(0).role()).isEqualTo(UserRole.PROFESSIONAL);
    }

    @Test
    void findUserMatrix_throwsForbidden_whenCallerIsNotCompanyAdmin() {
        authenticateAs("PROFESSIONAL");

        assertThatThrownBy(() -> permissionService.findUserMatrix(TARGET_USER_ID))
                .isInstanceOf(BusinessException.class)
                .extracting(ex -> ((BusinessException) ex).getStatus())
                .isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void findUserMatrix_throwsBadRequest_whenUserDoesNotExist() {
        authenticateAs("COMPANY_ADMIN");

        when(userRepository.findById(TARGET_USER_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> permissionService.findUserMatrix(TARGET_USER_ID))
                .isInstanceOf(BusinessException.class)
                .extracting(ex -> ((BusinessException) ex).getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void findUserMatrix_throwsBadRequest_whenUserRoleIsNotConfigurable() {
        authenticateAs("COMPANY_ADMIN");

        when(userRepository.findById(TARGET_USER_ID)).thenReturn(Optional.of(buildTargetUser(UserRole.CLIENT)));

        assertThatThrownBy(() -> permissionService.findUserMatrix(TARGET_USER_ID))
                .isInstanceOf(BusinessException.class)
                .extracting(ex -> ((BusinessException) ex).getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void findUserMatrix_defaultsUnconfiguredModulesToFullyAllowed() {
        authenticateAs("COMPANY_ADMIN");

        when(userRepository.findById(TARGET_USER_ID)).thenReturn(Optional.of(buildTargetUser(UserRole.PROFESSIONAL)));
        when(userPermissionRepository.findByUserId(TARGET_USER_ID)).thenReturn(List.of());

        List<ModulePermissionEntry> matrix = permissionService.findUserMatrix(TARGET_USER_ID);

        assertThat(matrix).hasSize(SystemModule.values().length);
        assertThat(matrix).allSatisfy(entry -> {
            assertThat(entry.canCreate()).isTrue();
            assertThat(entry.canUpdate()).isTrue();
            assertThat(entry.canList()).isTrue();
            assertThat(entry.canDelete()).isTrue();
        });
    }

    @Test
    void findUserMatrix_reflectsAnExistingRowInsteadOfTheDefault() {
        authenticateAs("COMPANY_ADMIN");

        when(userRepository.findById(TARGET_USER_ID)).thenReturn(Optional.of(buildTargetUser(UserRole.PROFESSIONAL)));

        UserPermission restricted = new UserPermission();
        restricted.setUserId(TARGET_USER_ID);
        restricted.setModule(SystemModule.PRODUCT);
        restricted.setCanCreate(false);
        restricted.setCanUpdate(false);
        restricted.setCanList(true);
        restricted.setCanDelete(false);

        when(userPermissionRepository.findByUserId(TARGET_USER_ID)).thenReturn(List.of(restricted));

        List<ModulePermissionEntry> matrix = permissionService.findUserMatrix(TARGET_USER_ID);

        ModulePermissionEntry entry = matrix.stream()
                .filter(e -> e.module() == SystemModule.PRODUCT)
                .findFirst()
                .orElseThrow();

        assertThat(entry.canCreate()).isFalse();
        assertThat(entry.canList()).isTrue();
    }

    @Test
    void updateUserMatrix_throwsForbidden_whenCallerIsNotCompanyAdmin() {
        authenticateAs("MASTER_ADMIN");

        assertThatThrownBy(() -> permissionService.updateUserMatrix(TARGET_USER_ID, List.of()))
                .isInstanceOf(BusinessException.class)
                .extracting(ex -> ((BusinessException) ex).getStatus())
                .isEqualTo(HttpStatus.FORBIDDEN);

        verify(userPermissionRepository, never()).save(any());
    }

    @Test
    void updateUserMatrix_createsANewRow_whenNoneExistsYet() {
        authenticateAs("COMPANY_ADMIN");

        when(userRepository.findById(TARGET_USER_ID)).thenReturn(Optional.of(buildTargetUser(UserRole.PROFESSIONAL)));
        when(userPermissionRepository.findByUserIdAndModule(TARGET_USER_ID, SystemModule.USER))
                .thenReturn(Optional.empty());

        ModulePermissionEntry entry = new ModulePermissionEntry(SystemModule.USER, true, false, true, false);

        permissionService.updateUserMatrix(TARGET_USER_ID, List.of(entry));

        ArgumentCaptor<UserPermission> captor = ArgumentCaptor.forClass(UserPermission.class);
        verify(userPermissionRepository).save(captor.capture());

        UserPermission saved = captor.getValue();
        assertThat(saved.getUserId()).isEqualTo(TARGET_USER_ID);
        assertThat(saved.getModule()).isEqualTo(SystemModule.USER);
        assertThat(saved.isCanCreate()).isTrue();
        assertThat(saved.isCanUpdate()).isFalse();
        assertThat(saved.isCanList()).isTrue();
        assertThat(saved.isCanDelete()).isFalse();
    }

    @Test
    void updateUserMatrix_updatesTheExistingRow_insteadOfDuplicatingIt() {
        authenticateAs("COMPANY_ADMIN");

        when(userRepository.findById(TARGET_USER_ID)).thenReturn(Optional.of(buildTargetUser(UserRole.PROFESSIONAL)));

        UserPermission existing = new UserPermission();
        existing.setId("existing-id");
        existing.setUserId(TARGET_USER_ID);
        existing.setModule(SystemModule.USER);
        existing.setCanCreate(true);
        existing.setCanUpdate(true);
        existing.setCanList(true);
        existing.setCanDelete(true);

        when(userPermissionRepository.findByUserIdAndModule(TARGET_USER_ID, SystemModule.USER))
                .thenReturn(Optional.of(existing));

        ModulePermissionEntry entry = new ModulePermissionEntry(SystemModule.USER, false, false, false, false);

        permissionService.updateUserMatrix(TARGET_USER_ID, List.of(entry));

        ArgumentCaptor<UserPermission> captor = ArgumentCaptor.forClass(UserPermission.class);
        verify(userPermissionRepository).save(captor.capture());

        assertThat(captor.getValue().getId()).isEqualTo("existing-id");
        assertThat(captor.getValue().isCanCreate()).isFalse();
    }

    @Test
    void deleteProfiles_throwsForbidden_whenCallerIsNotCompanyAdmin() {
        authenticateAs("MASTER_ADMIN");

        assertThatThrownBy(() -> permissionService.deleteProfiles(List.of(TARGET_USER_ID)))
                .isInstanceOf(BusinessException.class)
                .extracting(ex -> ((BusinessException) ex).getStatus())
                .isEqualTo(HttpStatus.FORBIDDEN);

        verify(userPermissionRepository, never()).deleteByUserIdIn(any());
    }

    @Test
    void deleteProfiles_deletesEveryRowForTheGivenUsers() {
        authenticateAs("COMPANY_ADMIN");

        permissionService.deleteProfiles(List.of(TARGET_USER_ID));

        verify(userPermissionRepository).deleteByUserIdIn(List.of(TARGET_USER_ID));
    }

    @Test
    void findMyPermissions_grantsFullAccessToMasterAdmin() {
        authenticateAs("MASTER_ADMIN");

        Map<SystemModule, ModulePermissionResponse> permissions = permissionService.findMyPermissions();

        assertThat(permissions).hasSize(SystemModule.values().length);
        assertThat(permissions.values()).allSatisfy(p -> assertThat(p.canCreate()).isTrue());

        verify(userPermissionRepository, never()).findByUserIdAndModule(any(), any());
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

        when(userPermissionRepository.findByUserIdAndModule(anyString(), any())).thenReturn(Optional.empty());

        Map<SystemModule, ModulePermissionResponse> permissions = permissionService.findMyPermissions();

        assertThat(permissions.get(SystemModule.CLIENT).canDelete()).isTrue();
    }

    @Test
    void findMyPermissions_returnsEmptyMapWhenThereIsNoAuthenticatedUser() {
        Map<SystemModule, ModulePermissionResponse> permissions = permissionService.findMyPermissions();

        assertThat(permissions).isEmpty();
    }
}
