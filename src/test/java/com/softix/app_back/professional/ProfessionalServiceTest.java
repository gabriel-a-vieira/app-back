package com.softix.app_back.professional;

import com.softix.app_back.city.CityRepository;
import com.softix.app_back.config.JWTUserData;
import com.softix.app_back.person.Person;
import com.softix.app_back.person.PersonRepository;
import com.softix.app_back.person.PersonService;
import com.softix.app_back.shared.exception.BusinessException;
import com.softix.app_back.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * MASTER_ADMIN has no companyId in the token, so a professional created by
 * them must take the company chosen in the form — otherwise it is persisted
 * without a tenant and disappears from every company-scoped query.
 */
@ExtendWith(MockitoExtension.class)
class ProfessionalServiceTest {

    @Mock
    private ProfessionalRepository professionalRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonService personService;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProfessionalService professionalService;

    private static final String CPF = "12345678901";

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    private void authenticateAs(String role, String companyId) {

        JWTUserData user = JWTUserData.builder()
                .userId("user-1")
                .companyId(companyId)
                .role(role)
                .build();

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user, null, List.of()));

    }

    private ProfessionalDTO buildDto(String companyId) {

        ProfessionalDTO dto = new ProfessionalDTO();
        dto.setCompanyId(companyId);
        dto.setName("Profissional");
        dto.setCpfCnpj(CPF);

        return dto;

    }

    @Test
    void save_masterAdminUsesCompanyFromRequest() {

        authenticateAs("MASTER_ADMIN", null);
        when(personRepository.findFirstByCpfCnpj(CPF)).thenReturn(new Person());

        professionalService.save(buildDto("company-1"));

        ArgumentCaptor<Professional> captor = ArgumentCaptor.forClass(Professional.class);
        verify(professionalRepository).save(captor.capture());
        assertThat(captor.getValue().getCompanyId()).isEqualTo("company-1");
    }

    @Test
    void save_masterAdminWithoutCompanyThrows() {

        authenticateAs("MASTER_ADMIN", null);

        assertThatThrownBy(() -> professionalService.save(buildDto(null)))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Empresa");

        verify(professionalRepository, never()).save(any());
    }

    @Test
    void save_companyAdminIgnoresCompanyFromRequest() {

        authenticateAs("COMPANY_ADMIN", "own-company");
        when(personRepository.findFirstByCpfCnpj(CPF)).thenReturn(new Person());

        professionalService.save(buildDto("other-company"));

        ArgumentCaptor<Professional> captor = ArgumentCaptor.forClass(Professional.class);
        verify(professionalRepository).save(captor.capture());
        assertThat(captor.getValue().getCompanyId()).isEqualTo("own-company");
    }

    @Test
    void findAll_masterAdminFiltersByRequestedCompany() {

        authenticateAs("MASTER_ADMIN", null);
        Pageable pageable = PageRequest.of(0, 10);
        when(professionalRepository.findAdvanced(eq("company-1"), any(), any(), any(), any(), any(), any(), isNull(), eq(pageable)))
                .thenReturn(Page.empty());

        professionalService.findAll(null, null, null, null, null, null, null, "company-1", pageable);

        verify(professionalRepository).findAdvanced(eq("company-1"), any(), any(), any(), any(), any(), any(), isNull(), eq(pageable));
    }

    @Test
    void findAll_companyAdminIgnoresRequestedCompany() {

        authenticateAs("COMPANY_ADMIN", "own-company");
        Pageable pageable = PageRequest.of(0, 10);
        when(professionalRepository.findAdvanced(eq("own-company"), any(), any(), any(), any(), any(), any(), any(), eq(pageable)))
                .thenReturn(Page.empty());

        professionalService.findAll(null, null, null, null, null, null, "ACTIVE", "other-company", pageable);

        verify(professionalRepository).findAdvanced(eq("own-company"), any(), any(), any(), any(), any(), any(), eq(ProfessionalStatus.ACTIVE), eq(pageable));
    }

    @Test
    void findAll_exposesCompanyIdInResponse() {

        authenticateAs("MASTER_ADMIN", null);
        Pageable pageable = PageRequest.of(0, 10);
        Professional professional = new Professional();
        professional.setCompanyId("company-1");
        when(professionalRepository.findAdvanced(any(), any(), any(), any(), any(), any(), any(), any(), eq(pageable)))
                .thenReturn(new PageImpl<>(List.of(professional)));

        Page<ProfessionalResponse> result = professionalService.findAll(null, null, null, null, null, null, null, "company-1", pageable);

        assertThat(result.getContent()).extracting(ProfessionalResponse::companyId).containsExactly("company-1");
    }

}
