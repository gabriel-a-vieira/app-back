package com.softix.app_back.auth.external;

import com.softix.app_back.auth.response.LoginResponse;
import com.softix.app_back.config.TokenConfig;
import com.softix.app_back.user.User;
import com.softix.app_back.user.UserRepository;
import com.softix.app_back.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.softix.app_back.shared.exception.BusinessException;

@Service
public class ExternalAuthService {

    @Autowired
    ExternalAuthStrategyResolver strategyResolver;

    @Autowired
    UserExternalIdentityRepository externalIdentityRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenConfig tokenConfig;


    @Transactional
    public LoginResponse authenticate(AuthProvider provider, String credential) {

        ExternalAuthStrategy strategy = strategyResolver.resolve(provider);
        ExternalIdentity externalIdentity = strategy.authenticate(credential);
        UserExternalIdentity registeredIdentity = externalIdentityRepository.findByProviderAndProviderUserId(provider, externalIdentity.providerUserId()).orElse(null);

        User user;

        if (registeredIdentity != null) {
            user = registeredIdentity.getUser();
        } else {

            User existingUser = userRepository.findByEmailIgnoreCase(externalIdentity.email()).orElse(null);

            if (existingUser != null) {
                throw new BusinessException(HttpStatus.CONFLICT, "Ja existe uma conta cadastrada com este email. Entre com email e senha para vincular sua conta Google.");
            }

            user = createUser(externalIdentity);
            createExternalIdentity(user, externalIdentity);

        }


        String token = tokenConfig.generateToken(user);

        return new LoginResponse(token, user.getName(), user.getEmail(), user.getRole().name());

    }


    private User createUser(ExternalIdentity identity) {

        User user = new User();
        String name = identity.name();

        if (name == null || name.isBlank()) {
            name = identity.email().split("@")[0];
        }

        user.setName(name);
        user.setEmail(identity.email().trim().toLowerCase());
        user.setPassword(null);
        user.setRole(UserRole.CLIENT);
        user.setCompanyId(null);

        return userRepository.save(user);

    }


    private void createExternalIdentity(User user, ExternalIdentity identity) {

        UserExternalIdentity external = new UserExternalIdentity();

        external.setUser(user);
        external.setProvider(identity.provider());
        external.setProviderUserId(identity.providerUserId());
        external.setProviderEmail(identity.email());

        externalIdentityRepository.save(external);

    }

}