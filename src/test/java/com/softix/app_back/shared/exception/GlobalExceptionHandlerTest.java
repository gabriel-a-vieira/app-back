package com.softix.app_back.shared.exception;

import com.softix.app_back.config.TokenConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Covers GlobalExceptionHandler's four @ExceptionHandler branches: the
 * status code, the fixed PT-BR message where one is hardcoded, and the
 * response body shape (status/error/message/path). Uses a dummy controller
 * scoped to this test so it doesn't need to wire the app's security filter
 * chain or any real controller's dependencies.
 */
@WebMvcTest(controllers = GlobalExceptionHandlerTest.DummyController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import({GlobalExceptionHandler.class, GlobalExceptionHandlerTest.DummyController.class})
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TokenConfig tokenConfig;

    @RestController
    static class DummyController {

        @GetMapping("/dummy/business")
        void business() {
            throw new BusinessException(HttpStatus.CONFLICT, "Ja existe uma conta cadastrada com este email.");
        }

        @GetMapping("/dummy/authentication")
        void authentication() {
            throw new BadCredentialsException("bad credentials");
        }

        @GetMapping("/dummy/data-integrity")
        void dataIntegrity() {
            throw new DataIntegrityViolationException("constraint violated");
        }

        @GetMapping("/dummy/unexpected")
        void unexpected() {
            throw new RuntimeException("boom");
        }
    }

    @Test
    void businessException_returnsItsOwnStatusAndMessage() throws Exception {
        mockMvc.perform(get("/dummy/business"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.error").value("Conflict"))
                .andExpect(jsonPath("$.message").value("Ja existe uma conta cadastrada com este email."))
                .andExpect(jsonPath("$.path").value("/dummy/business"));
    }

    @Test
    void authenticationException_returnsFixedUnauthorizedMessage() throws Exception {
        mockMvc.perform(get("/dummy/authentication"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.error").value("Unauthorized"))
                .andExpect(jsonPath("$.message").value("Usuario ou senha invalidos, tente novamente."))
                .andExpect(jsonPath("$.path").value("/dummy/authentication"));
    }

    @Test
    void dataIntegrityViolation_returnsFixedBadRequestMessage() throws Exception {
        mockMvc.perform(get("/dummy/data-integrity"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value(
                        "Nao foi possivel salvar os dados informados. Verifique se algum campo, como um link de imagem, esta em um formato invalido ou muito grande."))
                .andExpect(jsonPath("$.path").value("/dummy/data-integrity"));
    }

    @Test
    void unexpectedException_returnsGenericInternalServerErrorMessage() throws Exception {
        mockMvc.perform(get("/dummy/unexpected"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.error").value("Internal Server Error"))
                .andExpect(jsonPath("$.message").value("Ocorreu um erro inesperado. Tente novamente em instantes."))
                .andExpect(jsonPath("$.path").value("/dummy/unexpected"));
    }
}
