package com.softix.app_back.config;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Garante que as origens de CORS vêm da configuração (env CORS_ALLOWED_ORIGINS)
 * e não de um valor fixo, já que o front web em produção roda em outro domínio.
 */
class WebConfigTest {

    @Test
    @SuppressWarnings("unchecked")
    void addCorsMappings_usesConfiguredOrigins() {
        WebConfig webConfig = new WebConfig();
        ReflectionTestUtils.setField(webConfig, "allowedOrigins",
                new String[]{"http://localhost:45587", "https://front.onrender.com"});

        CorsRegistry registry = new CorsRegistry();
        webConfig.addCorsMappings(registry);

        Map<String, CorsConfiguration> configs =
                (Map<String, CorsConfiguration>) ReflectionTestUtils.invokeMethod(registry, "getCorsConfigurations");

        assertThat(configs.get("/**").getAllowedOrigins())
                .isEqualTo(List.of("http://localhost:45587", "https://front.onrender.com"));
    }
}
