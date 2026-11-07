package com.softix.app_back.shared.email;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import java.util.List;

/**
 * Sends e-mail through Brevo's transactional HTTP API
 * (POST /v3/smtp/email, docs: https://developers.brevo.com/reference/sendtransacemail).
 *
 * HTTP API instead of SMTP because Render's free tier blocks outbound SMTP
 * ports (25/465/587); HTTPS (443) goes through.
 *
 * Config (application.properties):
 * - BREVO_API_KEY: API key generated in Brevo -> SMTP & API -> API Keys.
 * - MAIL_FROM: sender address; must be a verified sender (or on a verified
 *   domain) in Brevo, otherwise Brevo rejects the request.
 * Connection/read timeouts come from spring.http.client.* so a hung provider
 * can't hold a thread forever.
 */
@Slf4j
@Component
public class BrevoEmailSender implements EmailSender {

    private final RestClient restClient;

    private final String apiKey;

    private final String fromEmail;

    private final String fromName;

    public BrevoEmailSender(RestClient.Builder restClientBuilder,
                            @Value("${brevo.base-url}") String baseUrl,
                            @Value("${brevo.api-key:}") String apiKey,
                            @Value("${mail.from:}") String fromEmail,
                            @Value("${mail.from-name:Softix}") String fromName) {
        this.restClient = restClientBuilder.baseUrl(baseUrl).build();
        this.apiKey = apiKey;
        this.fromEmail = fromEmail;
        this.fromName = fromName;
    }

    @Override
    public void send(EmailMessage message) {

        if (isBlank(apiKey) || isBlank(fromEmail)) {
            throw new EmailSendException("Envio de e-mail nao configurado: defina BREVO_API_KEY e MAIL_FROM");
        }

        BrevoRequest body = new BrevoRequest(
                new BrevoContact(fromEmail, fromName),
                List.of(new BrevoContact(message.toEmail(), blankToNull(message.toName()))),
                message.subject(),
                message.htmlContent());

        try {

            BrevoResponse response = restClient.post()
                    .uri("/smtp/email")
                    .header("api-key", apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(body)
                    .retrieve()
                    .body(BrevoResponse.class);

            log.debug("Brevo aceitou o e-mail, messageId={}", response != null ? response.messageId() : null);

        } catch (RestClientResponseException e) {
            throw new EmailSendException("Brevo recusou o e-mail: HTTP " + e.getStatusCode().value() + " " + e.getResponseBodyAsString(), e);
        } catch (RestClientException e) {
            throw new EmailSendException("Falha de comunicacao com a Brevo: " + e.getMessage(), e);
        }

    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private static String blankToNull(String value) {
        return isBlank(value) ? null : value;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    record BrevoContact(String email, String name) {
    }

    record BrevoRequest(BrevoContact sender, List<BrevoContact> to, String subject, String htmlContent) {
    }

    record BrevoResponse(String messageId) {
    }

}
