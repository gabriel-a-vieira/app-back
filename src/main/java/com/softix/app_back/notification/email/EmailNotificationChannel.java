package com.softix.app_back.notification.email;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import com.softix.app_back.notification.NotificationChannel;
import com.softix.app_back.notification.NotificationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class EmailNotificationChannel implements NotificationChannel {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy 'as' HH:mm");

    private final Resend resend;

    private final String fromEmail;

    public EmailNotificationChannel(@Value("${resend.api-key}") String apiKey,
                                    @Value("${resend.from-email}") String fromEmail) {
        this.resend = new Resend(apiKey);
        this.fromEmail = fromEmail;
    }

    @Override
    public void send(NotificationEvent event) {

        if (event.getRecipientEmail() == null || event.getRecipientEmail().isBlank()) {
            log.info("Notificacao {} sem e-mail de destinatario, envio ignorado", event.getType());
            return;
        }

        CreateEmailOptions options = CreateEmailOptions.builder()
                .from(fromEmail)
                .to(event.getRecipientEmail())
                .subject(subjectFor(event))
                .html(bodyFor(event))
                .build();

        try {

            CreateEmailResponse response = resend.emails().send(options);

            log.info("E-mail de notificacao {} enviado para {} (id {})", event.getType(), event.getRecipientEmail(), response.getId());

        } catch (ResendException e) {
            log.error("Falha ao enviar e-mail de notificacao {} para {}", event.getType(), event.getRecipientEmail(), e);
        }

    }

    private String subjectFor(NotificationEvent event) {
        return switch (event.getType()) {
            case APPOINTMENT_CREATED -> "Agendamento confirmado - " + event.getCompanyName();
            case APPOINTMENT_CANCELLED -> "Agendamento cancelado - " + event.getCompanyName();
            case APPOINTMENT_RESCHEDULED -> "Agendamento remarcado - " + event.getCompanyName();
        };
    }

    private String bodyFor(NotificationEvent event) {

        String when = event.getAppointmentStartAt() != null ? event.getAppointmentStartAt().format(DATE_TIME_FORMATTER) : "";

        return switch (event.getType()) {

            case APPOINTMENT_CREATED -> """
                    <p>Ola, %s!</p>
                    <p>Seu agendamento em <strong>%s</strong> foi confirmado para <strong>%s</strong>, com %s.</p>
                    <p>Servicos: %s</p>
                    """.formatted(event.getRecipientName(), event.getCompanyName(), when, event.getProfessionalName(), event.getServiceNames());

            case APPOINTMENT_CANCELLED -> """
                    <p>Ola, %s!</p>
                    <p>Seu agendamento em <strong>%s</strong> marcado para <strong>%s</strong> foi cancelado.</p>
                    """.formatted(event.getRecipientName(), event.getCompanyName(), when);

            case APPOINTMENT_RESCHEDULED -> {

                String previous = event.getPreviousStartAt() != null ? event.getPreviousStartAt().format(DATE_TIME_FORMATTER) : "";

                yield """
                        <p>Ola, %s!</p>
                        <p>Seu agendamento em <strong>%s</strong> foi remarcado de <strong>%s</strong> para <strong>%s</strong>, com %s.</p>
                        """.formatted(event.getRecipientName(), event.getCompanyName(), previous, when, event.getProfessionalName());

            }

        };
    }

}
