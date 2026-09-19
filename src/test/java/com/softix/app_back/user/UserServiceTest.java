package com.softix.app_back.user;

import com.softix.app_back.client.ClientRepository;
import com.softix.app_back.person.PersonService;
import com.softix.app_back.professional.ProfessionalRepository;
import com.softix.app_back.shared.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Covers UserService's CRUD logic: duplicate-email guards on create/update,
 * that update() leaves the password untouched when none is supplied, and
 * that findById/update throw a clear 404 for an unknown id (findById is
 * already tenant-scoped for free via TenantAspect, nothing extra to test
 * here).
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ProfessionalRepository professionalRepository;

    @Mock
    private PersonService personService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private static final String USER_ID = "user-1";

    private User buildUser() {
        User user = new User();
        user.setId(USER_ID);
        user.setName("Ana Souza");
        user.setEmail("ana@softix.com");
        user.setPassword("hashed-old-password");
        user.setRole(UserRole.PROFESSIONAL);
        return user;
    }

    @Test
    void createUser_throwsBadRequest_whenEmailAlreadyExists() {
        CreateUserRequest request = new CreateUserRequest("Ana", "ana@softix.com", "123456", UserRole.PROFESSIONAL, null, null, null, null);

        when(userRepository.existsByEmailIgnoreCase("ana@softix.com")).thenReturn(true);

        assertThatThrownBy(() -> userService.createUser(request))
                .isInstanceOf(BusinessException.class)
                .extracting(ex -> ((BusinessException) ex).getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST);

        verify(userRepository, never()).save(any());
    }

    @Test
    void createUser_encodesPasswordAndSavesTheNewUser() {
        CreateUserRequest request = new CreateUserRequest("Ana", "ana@softix.com", "123456", UserRole.PROFESSIONAL, null, null, null, null);

        when(userRepository.existsByEmailIgnoreCase("ana@softix.com")).thenReturn(false);
        when(passwordEncoder.encode("123456")).thenReturn("hashed-password");

        userService.createUser(request);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());

        assertThat(captor.getValue().getEmail()).isEqualTo("ana@softix.com");
        assertThat(captor.getValue().getPassword()).isEqualTo("hashed-password");
        assertThat(captor.getValue().getRole()).isEqualTo(UserRole.PROFESSIONAL);
    }

    @Test
    void findById_throwsNotFound_whenUserDoesNotExist() {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findById(USER_ID))
                .isInstanceOf(BusinessException.class)
                .extracting(ex -> ((BusinessException) ex).getStatus())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void findAll_mapsThePageOfEntitiesToResponses() {
        User user = buildUser();

        when(userRepository.findAdvanced(any(), any(), any()))
                .thenReturn(new PageImpl<>(List.of(user)));

        var result = userService.findAll("ana", null, PageRequest.of(0, 10));

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).id()).isEqualTo(USER_ID);
    }

    @Test
    void update_throwsBadRequest_whenNewEmailBelongsToAnotherUser() {
        UpdateUserRequest request = new UpdateUserRequest("Ana Souza", "outra@softix.com", UserRole.PROFESSIONAL, null, null, null, null);

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(buildUser()));
        when(userRepository.existsByEmailIgnoreCase("outra@softix.com")).thenReturn(true);

        assertThatThrownBy(() -> userService.update(USER_ID, request))
                .isInstanceOf(BusinessException.class)
                .extracting(ex -> ((BusinessException) ex).getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST);

        verify(userRepository, never()).save(any());
    }

    @Test
    void update_keepsThePreviousPassword_whenNoneIsProvided() {
        UpdateUserRequest request = new UpdateUserRequest("Ana Souza", "ana@softix.com", UserRole.COMPANY_ADMIN, "  ", null, null, null);

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(buildUser()));

        userService.update(USER_ID, request);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());

        assertThat(captor.getValue().getPassword()).isEqualTo("hashed-old-password");
        assertThat(captor.getValue().getRole()).isEqualTo(UserRole.COMPANY_ADMIN);

        verify(passwordEncoder, never()).encode(any());
    }

    @Test
    void update_encodesAndReplacesThePassword_whenOneIsProvided() {
        UpdateUserRequest request = new UpdateUserRequest("Ana Souza", "ana@softix.com", UserRole.PROFESSIONAL, "novaSenha123", null, null, null);

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(buildUser()));
        when(passwordEncoder.encode("novaSenha123")).thenReturn("hashed-new-password");

        userService.update(USER_ID, request);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());

        assertThat(captor.getValue().getPassword()).isEqualTo("hashed-new-password");
    }

    @Test
    void deleteMany_delegatesToTheRepository() {
        userService.deleteMany(List.of(USER_ID));

        verify(userRepository).deleteAllById(List.of(USER_ID));
    }
}
