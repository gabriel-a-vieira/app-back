package com.softix.app_back.auth.external.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.softix.app_back.auth.external.AuthProvider;
import com.softix.app_back.auth.external.ExternalAuthStrategy;
import com.softix.app_back.auth.external.ExternalIdentity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Component
public class GoogleAuthStrategy implements ExternalAuthStrategy {

    private final GoogleIdTokenVerifier verifier;

    public GoogleAuthStrategy(@Value("${google.oauth.client-id}") String clientId) throws GeneralSecurityException, IOException {
        this.verifier = new GoogleIdTokenVerifier.Builder(GoogleNetHttpTransport.newTrustedTransport(), GsonFactory.getDefaultInstance()).setAudience(List.of(clientId)).build();
    }

    @Override
    public AuthProvider getProvider() {
        return AuthProvider.GOOGLE;
    }

    @Override
    public ExternalIdentity authenticate(String credential) {

        if (credential == null || credential.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Credencial Google nao informada");
        }

        try {

            GoogleIdToken idToken = verifier.verify(credential);

            if (idToken == null) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token Google invalido");
            }

            GoogleIdToken.Payload payload = idToken.getPayload();

            String providerUserId = payload.getSubject();
            String email = payload.getEmail();
            Boolean emailVerified = payload.getEmailVerified();
            String name = payload.get("name") != null ? payload.get("name").toString() : null;
            String picture = payload.get("picture") != null ? payload.get("picture").toString() : null;

            if (providerUserId == null || providerUserId.isBlank()) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Identificador Google invalido");
            }

            if (email == null || email.isBlank()) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Conta Google sem email");
            }

            if (!Boolean.TRUE.equals(emailVerified)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email Google nao verificado");
            }

            return new ExternalIdentity(AuthProvider.GOOGLE, providerUserId, email, name, picture, true);

        } catch (GeneralSecurityException | IOException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Nao foi possivel validar o token Google", e);
        }

    }

}