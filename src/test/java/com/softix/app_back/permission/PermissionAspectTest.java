package com.softix.app_back.permission;

import com.softix.app_back.config.JWTUserData;
import com.softix.app_back.shared.exception.BusinessException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Method;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Covers PermissionAspect's gate: MASTER_ADMIN always bypasses, a role
 * outside {COMPANY_ADMIN, PROFESSIONAL} is always denied, and a configurable
 * role is allowed unless an explicit UserPermission row (keyed by the
 * caller's own userId) denies that specific action -- a missing row means
 * "not restricted yet" (default allow).
 */
@ExtendWith(MockitoExtension.class)
class PermissionAspectTest {

    private static final String USER_ID = "user-1";

    @Mock
    private UserPermissionRepository userPermissionRepository;

    @InjectMocks
    private PermissionAspect permissionAspect;

    @RequiresPermission(module = SystemModule.CLIENT, action = CrudAction.CREATE)
    private void annotatedCreateClient() {
    }

    private RequiresPermission requiresPermission(String methodName) throws NoSuchMethodException {
        Method method = PermissionAspectTest.class.getDeclaredMethod(methodName);
        return method.getAnnotation(RequiresPermission.class);
    }

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    private void authenticateAs(String role) {
        JWTUserData user = JWTUserData.builder().userId(USER_ID).companyId("company-1").role(role).email("user@softix.com").build();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null));
    }

    @Test
    void checkPermission_letsThroughWhenThereIsNoAuthenticatedUser() throws NoSuchMethodException {
        assertThatCode(() -> permissionAspect.checkPermission(requiresPermission("annotatedCreateClient")))
                .doesNotThrowAnyException();

        verify(userPermissionRepository, never()).findByUserIdAndModule(any(), any());
    }

    @Test
    void checkPermission_alwaysAllowsMasterAdmin() throws NoSuchMethodException {
        authenticateAs("MASTER_ADMIN");

        assertThatCode(() -> permissionAspect.checkPermission(requiresPermission("annotatedCreateClient")))
                .doesNotThrowAnyException();

        verify(userPermissionRepository, never()).findByUserIdAndModule(any(), any());
    }

    @Test
    void checkPermission_deniesRolesOutsideConfigurableSet() throws NoSuchMethodException {
        authenticateAs("CLIENT");

        assertThatThrownBy(() -> permissionAspect.checkPermission(requiresPermission("annotatedCreateClient")))
                .isInstanceOf(BusinessException.class)
                .extracting(ex -> ((BusinessException) ex).getStatus())
                .isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void checkPermission_allowsWhenNoRowExistsYet() throws NoSuchMethodException {
        authenticateAs("COMPANY_ADMIN");

        when(userPermissionRepository.findByUserIdAndModule(USER_ID, SystemModule.CLIENT))
                .thenReturn(Optional.empty());

        assertThatCode(() -> permissionAspect.checkPermission(requiresPermission("annotatedCreateClient")))
                .doesNotThrowAnyException();
    }

    @Test
    void checkPermission_deniesWhenTheConfiguredRowForbidsTheAction() throws NoSuchMethodException {
        authenticateAs("PROFESSIONAL");

        UserPermission permission = new UserPermission();
        permission.setUserId(USER_ID);
        permission.setModule(SystemModule.CLIENT);
        permission.setCanCreate(false);
        permission.setCanUpdate(true);
        permission.setCanList(true);
        permission.setCanDelete(true);

        when(userPermissionRepository.findByUserIdAndModule(USER_ID, SystemModule.CLIENT))
                .thenReturn(Optional.of(permission));

        assertThatThrownBy(() -> permissionAspect.checkPermission(requiresPermission("annotatedCreateClient")))
                .isInstanceOf(BusinessException.class)
                .extracting(ex -> ((BusinessException) ex).getStatus())
                .isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void checkPermission_allowsWhenTheConfiguredRowGrantsTheAction() throws NoSuchMethodException {
        authenticateAs("PROFESSIONAL");

        UserPermission permission = new UserPermission();
        permission.setUserId(USER_ID);
        permission.setModule(SystemModule.CLIENT);
        permission.setCanCreate(true);
        permission.setCanUpdate(false);
        permission.setCanList(false);
        permission.setCanDelete(false);

        when(userPermissionRepository.findByUserIdAndModule(USER_ID, SystemModule.CLIENT))
                .thenReturn(Optional.of(permission));

        assertThatCode(() -> permissionAspect.checkPermission(requiresPermission("annotatedCreateClient")))
                .doesNotThrowAnyException();
    }

    @Test
    void checkPermission_deniesAnUnparseableRole() throws NoSuchMethodException {
        authenticateAs("NOT_A_REAL_ROLE");

        assertThatThrownBy(() -> permissionAspect.checkPermission(requiresPermission("annotatedCreateClient")))
                .isInstanceOf(BusinessException.class)
                .extracting(ex -> ((BusinessException) ex).getStatus())
                .isEqualTo(HttpStatus.FORBIDDEN);
    }
}
