package com.softix.app_back.auth.external;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import com.softix.app_back.shared.exception.BusinessException;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class ExternalAuthStrategyResolver {

    private final Map<AuthProvider, ExternalAuthStrategy> strategies;

    public ExternalAuthStrategyResolver(List<ExternalAuthStrategy> strategies) {

        this.strategies = new EnumMap<>(AuthProvider.class);

        for (ExternalAuthStrategy strategy : strategies) {
            this.strategies.put(strategy.getProvider(), strategy);
        }

    }

    public ExternalAuthStrategy resolve(AuthProvider provider) {

        ExternalAuthStrategy strategy = strategies.get(provider);

        if (strategy == null) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Provedor de autenticacao nao suportado");
        }

        return strategy;

    }

}