package com.softix.app_back.health;

import com.softix.app_back.config.JwtAuthenticationEntryPoint;
import com.softix.app_back.config.SecurityConfig;
import com.softix.app_back.config.SecurityFilter;
import com.softix.app_back.config.TokenConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Garante que /health responde 200 sem token, com a cadeia de segurança real
 * carregada — se ele passar a exigir autenticação, o monitor de uptime para de
 * manter o serviço acordado no Render sem ninguém perceber.
 */
@WebMvcTest(controllers = HealthController.class)
@Import({SecurityConfig.class, SecurityFilter.class, JwtAuthenticationEntryPoint.class})
class HealthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TokenConfig tokenConfig;

    @Test
    void health_isPublicAndReturnsUp() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
    }

    @Test
    void health_acceptsHeadRequests() throws Exception {
        mockMvc.perform(head("/health"))
                .andExpect(status().isOk());
    }
}
