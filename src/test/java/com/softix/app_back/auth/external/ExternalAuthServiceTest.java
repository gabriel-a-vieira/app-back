package com.softix.app_back.auth.external;

import com.softix.app_back.auth.response.LoginResponse;
import com.softix.app_back.config.TokenConfig;
import com.softix.app_back.shared.exception.BusinessException;
import com.softix.app_back.user.User;
import com.softix.app_back.user.UserRepository;
import com.softix.app_back.user.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Covers ExternalAuthService's account-linking decision: reuse the linked
 * user when the external identity is already registered, create a brand
 * new CLIENT account on first login, and refuse to silently take over a
 * pre-existing local (email/password) account with the same email.
 */
@ExtendWith(MockitoExtension.class)
class ExternalAuthServiceTest {

    @Mock
    private ExternalAuthStrategyResolver strategyResolver;

    @Mock
    private ExternalAuthStrategy strategy;

    @Mock
    private UserExternalIdentityRepository externalIdentityRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenConfig tokenConfig;

    @InjectMocks
    private ExternalAuthService externalAuthService;

    private static final String PROVIDER_USER_ID = "google-sub-123";
    private static final String EMAIL = "cliente@gmail.com";
    private static final String TOKEN = "jwt-token";

    private ExternalIdentity googleIdentity(String name) {
        return new ExternalIdentity(AuthProvider.GOOGLE, PROVIDER_USER_ID, EMAIL, name, "https://picture.url", true);
    }

    @Test
    void authenticate_reusesLinkedUser_whenExternalIdentityAlreadyRegistered() {
        User linkedUser = new User();
        linkedUser.setId("user-1");
        linkedUser.setName("Cliente Existente");
        linkedUser.setEmail(EMAIL);
        linkedUser.setRole(UserRole.CLIENT);

        UserExternalIdentity registeredIdentity = new UserExternalIdentity();
        registeredIdentity.setUser(linkedUser);

        when(strategyResolver.resolve(AuthProvider.GOOGLE)).thenReturn(strategy);
        when(strategy.authenticate("credential")).thenReturn(googleIdentity("Cliente Existente"));
        when(externalIdentityRepository.findByProviderAndProviderUserId(AuthProvider.GOOGLE, PROVIDER_USER_ID))
                .thenReturn(Optional.of(registeredIdentity));
        when(tokenConfig.generateToken(linkedUser)).thenReturn(TOKEN);

        LoginResponse response = externalAuthService.authenticate(AuthProvider.GOOGLE, "credential");

        assertThat(response.token()).isEqualTo(TOKEN);
        assertThat(response.name()).isEqualTo("Cliente Existente");
        assertThat(response.email()).isEqualTo(EMAIL);
        verify(userRepository, never()).save(any());
        verify(externalIdentityRepository, never()).save(any());
    }

    @Test
    void authenticate_createsNewClientUser_whenNoIdentityAndNoExistingUser() {
        when(strategyResolver.resolve(AuthProvider.GOOGLE)).thenReturn(strategy);
        when(strategy.authenticate("credential")).thenReturn(googleIdentity(""));
        when(externalIdentityRepository.findByProviderAndProviderUserId(AuthProvider.GOOGLE, PROVIDER_USER_ID))
                .thenReturn(Optional.empty());
        when(userRepository.findByEmailIgnoreCase(EMAIL)).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(tokenConfig.generateToken(any(User.class))).thenReturn(TOKEN);

        LoginResponse response = externalAuthService.authenticate(AuthProvider.GOOGLE, "credential");

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User createdUser = userCaptor.getValue();

        assertThat(createdUser.getName()).isEqualTo("cliente");
        assertThat(createdUser.getEmail()).isEqualTo(EMAIL);
        assertThat(createdUser.getPassword()).isNull();
        assertThat(createdUser.getRole()).isEqualTo(UserRole.CLIENT);
        assertThat(createdUser.getCompanyId()).isNull();

        ArgumentCaptor<UserExternalIdentity> identityCaptor = ArgumentCaptor.forClass(UserExternalIdentity.class);
        verify(externalIdentityRepository).save(identityCaptor.capture());
        UserExternalIdentity savedIdentity = identityCaptor.getValue();
        assertThat(savedIdentity.getUser()).isEqualTo(createdUser);
        assertThat(savedIdentity.getProvider()).isEqualTo(AuthProvider.GOOGLE);
        assertThat(savedIdentity.getProviderUserId()).isEqualTo(PROVIDER_USER_ID);
        assertThat(savedIdentity.getProviderEmail()).isEqualTo(EMAIL);

        assertThat(response.token()).isEqualTo(TOKEN);
    }

    @Test
    void authenticate_throwsConflict_whenLocalAccountAlreadyExistsWithSameEmail() {
        User existingLocalUser = new User();
        existingLocalUser.setId("user-2");
        existingLocalUser.setEmail(EMAIL);

        when(strategyResolver.resolve(AuthProvider.GOOGLE)).thenReturn(strategy);
        when(strategy.authenticate("credential")).thenReturn(googleIdentity("Cliente"));
        when(externalIdentityRepository.findByProviderAndProviderUserId(AuthProvider.GOOGLE, PROVIDER_USER_ID))
                .thenReturn(Optional.empty());
        when(userRepository.findByEmailIgnoreCase(EMAIL)).thenReturn(Optional.of(existingLocalUser));

        assertThatThrownBy(() -> externalAuthService.authenticate(AuthProvider.GOOGLE, "credential"))
                .isInstanceOf(BusinessException.class)
                .extracting(ex -> ((BusinessException) ex).getStatus())
                .isEqualTo(HttpStatus.CONFLICT);

        verify(userRepository, never()).save(any());
        verify(externalIdentityRepository, never()).save(any());
    }
}
