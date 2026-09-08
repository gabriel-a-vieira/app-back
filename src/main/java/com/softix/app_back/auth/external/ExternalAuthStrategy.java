package com.softix.app_back.auth.external;

public interface ExternalAuthStrategy {

    AuthProvider getProvider();

    ExternalIdentity authenticate(
            String credential
    );

}