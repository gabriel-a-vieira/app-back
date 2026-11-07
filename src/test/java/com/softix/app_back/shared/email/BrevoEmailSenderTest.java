package com.softix.app_back.shared.email;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Checks the HTTP contract with Brevo (URL, api-key header, JSON shape) and
 * that every failure surfaces as EmailSendException, which is what callers
 * catch and log. No real network: MockRestServiceServer stands in for Brevo.
 */
class BrevoEmailSenderTest {

    private static final String BASE_URL = "https://api.brevo.test/v3";

    private RestClient.Builder builder;

    private MockRestServiceServer server;

    @BeforeEach
    void setUp() {
        builder = RestClient.builder();
        server = MockRestServiceServer.bindTo(builder).build();
    }

    private BrevoEmailSender sender(String apiKey, String fromEmail) {
        return new BrevoEmailSender(builder, BASE_URL, apiKey, fromEmail, "Softix");
    }

    private EmailMessage message(String toName) {
        return new EmailMessage("cliente@teste.com", toName, "Assunto", "<p>Oi</p>");
    }

    @Test
    void send_postsTheExpectedRequestToBrevo() {

        server.expect(requestTo(BASE_URL + "/smtp/email"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header("api-key", "chave-teste"))
                .andExpect(content().json("""
                        {
                          "sender": {"email": "noreply@teste.com", "name": "Softix"},
                          "to": [{"email": "cliente@teste.com", "name": "Cliente Teste"}],
                          "subject": "Assunto",
                          "htmlContent": "<p>Oi</p>"
                        }
                        """, true))
                .andRespond(withSuccess("{\"messageId\":\"<abc@brevo>\"}", MediaType.APPLICATION_JSON));

        sender("chave-teste", "noreply@teste.com").send(message("Cliente Teste"));

        server.verify();
    }

    @Test
    void send_omitsTheRecipientNameWhenBlank() {

        // strict match: a "name" key (null or "") inside "to" would fail it
        server.expect(requestTo(BASE_URL + "/smtp/email"))
                .andExpect(content().json("""
                        {
                          "sender": {"email": "noreply@teste.com", "name": "Softix"},
                          "to": [{"email": "cliente@teste.com"}],
                          "subject": "Assunto",
                          "htmlContent": "<p>Oi</p>"
                        }
                        """, true))
                .andRespond(withSuccess("{\"messageId\":\"x\"}", MediaType.APPLICATION_JSON));

        sender("chave-teste", "noreply@teste.com").send(message(" "));

        server.verify();
    }

    @Test
    void send_wrapsAProviderRejectionInEmailSendException() {

        server.expect(requestTo(BASE_URL + "/smtp/email"))
                .andRespond(withStatus(HttpStatus.UNAUTHORIZED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("{\"code\":\"unauthorized\",\"message\":\"Key not found\"}"));

        BrevoEmailSender sender = sender("chave-errada", "noreply@teste.com");

        assertThatThrownBy(() -> sender.send(message("Cliente")))
                .isInstanceOf(EmailSendException.class)
                .hasMessageContaining("401")
                .hasMessageContaining("Key not found");
    }

    @Test
    void send_failsWithoutCallingBrevoWhenApiKeyIsMissing() {

        BrevoEmailSender sender = sender("", "noreply@teste.com");

        assertThatThrownBy(() -> sender.send(message("Cliente")))
                .isInstanceOf(EmailSendException.class)
                .hasMessageContaining("BREVO_API_KEY");

        server.verify();
    }

    @Test
    void send_failsWithoutCallingBrevoWhenSenderIsMissing() {

        BrevoEmailSender sender = sender("chave-teste", null);

        assertThatThrownBy(() -> sender.send(message("Cliente")))
                .isInstanceOf(EmailSendException.class)
                .hasMessageContaining("MAIL_FROM");

        server.verify();
    }

}
