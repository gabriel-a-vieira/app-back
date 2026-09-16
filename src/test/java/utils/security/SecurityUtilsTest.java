package utils.security;

import com.softix.app_back.config.JWTUserData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Covers SecurityUtils.resolveCompanyId, the single choke point almost
 * every service uses to decide whose companyId scope applies. If this is
 * wrong, a non-master user could read/write another company's data - it's
 * the application-level half of the multi-tenancy boundary (the other
 * half being TenantAspect's Hibernate filter).
 */
class SecurityUtilsTest {

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    private void authenticateAs(String role, String companyId) {

        JWTUserData user = JWTUserData.builder()
                .userId("user-1")
                .companyId(companyId)
                .email("user@softix.com")
                .role(role)
                .build();

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null));

    }

    @Test
    void resolveCompanyId_returnsRequestedCompanyWhenThereIsNoAuthenticatedUser() {
        assertThat(SecurityUtils.resolveCompanyId("requested-company")).isEqualTo("requested-company");
    }

    @Test
    void resolveCompanyId_masterAdminCanRequestAnyCompany() {

        authenticateAs("MASTER_ADMIN", "own-company");

        assertThat(SecurityUtils.resolveCompanyId("other-company")).isEqualTo("other-company");
    }

    @Test
    void resolveCompanyId_nonMasterUserIsPinnedToTheirOwnCompanyRegardlessOfWhatIsRequested() {

        authenticateAs("COMPANY_ADMIN", "own-company");

        assertThat(SecurityUtils.resolveCompanyId("other-company")).isEqualTo("own-company");
    }

    @Test
    void isMasterAdmin_isTrueOnlyForMasterAdminRole() {

        authenticateAs("MASTER_ADMIN", "own-company");
        assertThat(SecurityUtils.isMasterAdmin()).isTrue();

        authenticateAs("COMPANY_ADMIN", "own-company");
        assertThat(SecurityUtils.isMasterAdmin()).isFalse();
    }

    @Test
    void userIdAndCompanyIdAndRole_delegateToTheAuthenticatedPrincipal() {

        authenticateAs("PROFESSIONAL", "own-company");

        assertThat(SecurityUtils.userId()).isEqualTo("user-1");
        assertThat(SecurityUtils.companyId()).isEqualTo("own-company");
        assertThat(SecurityUtils.role()).isEqualTo("PROFESSIONAL");
    }

}
