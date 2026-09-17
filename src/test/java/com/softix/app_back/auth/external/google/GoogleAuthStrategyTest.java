package com.softix.app_back.auth.external.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.softix.app_back.auth.external.AuthProvider;
import com.softix.app_back.auth.external.ExternalIdentity;
import com.softix.app_back.shared.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Covers GoogleAuthStrategy's validation of the Google ID token payload.
 * GoogleIdTokenVerifier is built inline in the constructor (not injected),
 * so it's swapped for a mock via ReflectionTestUtils after construction --
 * this only exercises token-payload validation, no real network call to
 * Google is ever made.
 */
class GoogleAuthStrategyTest {

    private GoogleAuthStrategy strategy;
    private GoogleIdTokenVerifier verifier;

    @BeforeEach
    void setUp() throws GeneralSecurityException, IOException {
        strategy = new GoogleAuthStrategy("dummy-client-id");
        verifier = mock(GoogleIdTokenVerifier.class);
        ReflectionTestUtils.setField(strategy, "verifier", verifier);
    }

    private GoogleIdToken mockIdToken(String subject, String email, Boolean emailVerified, String name, String picture) {
        // GenericJson#get(Object) is final, so the payload can't be a Mockito mock -- use a real
        // instance (it's a plain data holder) and just populate the fields we need.
        GoogleIdToken.Payload payload = new GoogleIdToken.Payload();
        payload.setSubject(subject);
        payload.setEmail(email);
        payload.setEmailVerified(emailVerified);
        payload.set("name", name);
        payload.set("picture", picture);

        GoogleIdToken idToken = mock(GoogleIdToken.class);
        when(idToken.getPayload()).thenReturn(payload);
        return idToken;
    }

    @Test
    void authenticate_throwsBadRequest_whenCredentialIsBlank() {
        assertThatThrownBy(() -> strategy.authenticate("  "))
                .isInstanceOf(BusinessException.class)
                .extracting(ex -> ((BusinessException) ex).getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void authenticate_throwsUnauthorized_whenVerifierReturnsNull() throws GeneralSecurityException, IOException {
        when(verifier.verify("credential")).thenReturn(null);

        assertThatThrownBy(() -> strategy.authenticate("credential"))
                .isInstanceOf(BusinessException.class)
                .extracting(ex -> ((BusinessException) ex).getStatus())
                .isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void authenticate_throwsUnauthorized_whenSubjectIsBlank() throws GeneralSecurityException, IOException {
        GoogleIdToken idToken = mockIdToken("  ", "user@gmail.com", true, "Nome", "pic");
        when(verifier.verify("credential")).thenReturn(idToken);

        assertThatThrownBy(() -> strategy.authenticate("credential"))
                .isInstanceOf(BusinessException.class)
                .extracting(ex -> ((BusinessException) ex).getStatus())
                .isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void authenticate_throwsUnauthorized_whenEmailIsBlank() throws GeneralSecurityException, IOException {
        GoogleIdToken idToken = mockIdToken("sub-1", "  ", true, "Nome", "pic");
        when(verifier.verify("credential")).thenReturn(idToken);

        assertThatThrownBy(() -> strategy.authenticate("credential"))
                .isInstanceOf(BusinessException.class)
                .extracting(ex -> ((BusinessException) ex).getStatus())
                .isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void authenticate_throwsUnauthorized_whenEmailNotVerified() throws GeneralSecurityException, IOException {
        GoogleIdToken idToken = mockIdToken("sub-1", "user@gmail.com", false, "Nome", "pic");
        when(verifier.verify("credential")).thenReturn(idToken);

        assertThatThrownBy(() -> strategy.authenticate("credential"))
                .isInstanceOf(BusinessException.class)
                .extracting(ex -> ((BusinessException) ex).getStatus())
                .isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void authenticate_throwsUnauthorized_whenVerifierThrows() throws GeneralSecurityException, IOException {
        when(verifier.verify("credential")).thenThrow(new GeneralSecurityException("boom"));

        assertThatThrownBy(() -> strategy.authenticate("credential"))
                .isInstanceOf(BusinessException.class)
                .extracting(ex -> ((BusinessException) ex).getStatus())
                .isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void authenticate_returnsExternalIdentity_onHappyPath() throws GeneralSecurityException, IOException {
        GoogleIdToken idToken = mockIdToken("sub-1", "user@gmail.com", true, "Nome Sobrenome", "https://pic.url");
        when(verifier.verify("credential")).thenReturn(idToken);

        ExternalIdentity identity = strategy.authenticate("credential");

        assertThat(identity.provider()).isEqualTo(AuthProvider.GOOGLE);
        assertThat(identity.providerUserId()).isEqualTo("sub-1");
        assertThat(identity.email()).isEqualTo("user@gmail.com");
        assertThat(identity.name()).isEqualTo("Nome Sobrenome");
        assertThat(identity.pictureUrl()).isEqualTo("https://pic.url");
        assertThat(identity.emailVerified()).isTrue();
    }
}
