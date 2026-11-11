package com.softix.app_back.notification.email;

import com.softix.app_back.calendar.AppointmentCalendarEvents;
import com.softix.app_back.calendar.CalendarEvent;
import com.softix.app_back.calendar.CalendarInviteBuilder;
import com.softix.app_back.notification.NotificationChannel;
import com.softix.app_back.notification.NotificationEvent;
import com.softix.app_back.notification.NotificationEventType;
import com.softix.app_back.shared.email.EmailAttachment;
import com.softix.app_back.shared.email.EmailMessage;
import com.softix.app_back.shared.email.EmailSendException;
import com.softix.app_back.shared.email.EmailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Component
public class EmailNotificationChannel implements NotificationChannel {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy 'as' HH:mm");

    static final String INVITE_FILE_NAME = "agendamento.ics";

    private final EmailSender emailSender;

    private final CalendarInviteBuilder calendarInviteBuilder;

    private final String organizerEmail;

    public EmailNotificationChannel(EmailSender emailSender,
                                    CalendarInviteBuilder calendarInviteBuilder,
                                    @Value("${mail.from:}") String organizerEmail) {
        this.emailSender = emailSender;
        this.calendarInviteBuilder = calendarInviteBuilder;
        this.organizerEmail = organizerEmail;
    }

    @Override
    public void send(NotificationEvent event) {

        if (event.getRecipientEmail() == null || event.getRecipientEmail().isBlank()) {
            log.info("Notificacao {} sem e-mail de destinatario, envio ignorado", event.getType());
            return;
        }

        try {

            CalendarEvent calendarEvent = toCalendarEvent(event);

            emailSender.send(new EmailMessage(
                    event.getRecipientEmail(),
                    event.getRecipientName(),
                    subjectFor(event),
                    bodyFor(event, calendarEvent),
                    attachmentsFor(event, calendarEvent)));

            log.info("E-mail de notificacao {} enviado para {}", event.getType(), event.getRecipientEmail());

        } catch (EmailSendException e) {
            log.error("Falha ao enviar e-mail de notificacao {} para {}", event.getType(), event.getRecipientEmail(), e);
        }

    }

    /**
     * Null when the event lacks what a calendar entry needs (id/start) -- the
     * e-mail still goes out, just without invite/link.
     */
    private CalendarEvent toCalendarEvent(NotificationEvent event) {

        if (event.getAppointmentId() == null || event.getAppointmentStartAt() == null) {
            return null;
        }

        return AppointmentCalendarEvents.of(
                event.getAppointmentId(),
                event.getAppointmentVersion(),
                event.getAppointmentStartAt(),
                event.getAppointmentEndAt(),
                event.getCompanyName(),
                event.getProfessionalName(),
                event.getServiceNames(),
                event.getCompanyAddress(),
                event.getType() == NotificationEventType.APPOINTMENT_CANCELLED);
    }

    private List<EmailAttachment> attachmentsFor(NotificationEvent event, CalendarEvent calendarEvent) {

        if (calendarEvent == null) {
            return List.of();
        }

        String ics = calendarInviteBuilder.buildInvite(calendarEvent, organizerEmail, event.getRecipientEmail(), event.getRecipientName());

        return List.of(new EmailAttachment(INVITE_FILE_NAME, ics.getBytes(StandardCharsets.UTF_8)));
    }

    private String subjectFor(NotificationEvent event) {
        return switch (event.getType()) {
            case APPOINTMENT_CREATED -> "Agendamento confirmado - " + event.getCompanyName();
            case APPOINTMENT_CANCELLED -> "Agendamento cancelado - " + event.getCompanyName();
            case APPOINTMENT_RESCHEDULED -> "Agendamento remarcado - " + event.getCompanyName();
        };
    }

    private String bodyFor(NotificationEvent event, CalendarEvent calendarEvent) {

        String when = event.getAppointmentStartAt() != null ? event.getAppointmentStartAt().format(DATE_TIME_FORMATTER) : "";

        return switch (event.getType()) {

            case APPOINTMENT_CREATED -> """
                    <p>Ola, %s!</p>
                    <p>Seu agendamento em <strong>%s</strong> foi confirmado para <strong>%s</strong>, com %s.</p>
                    <p>Servicos: %s</p>
                    %s""".formatted(event.getRecipientName(), event.getCompanyName(), when, event.getProfessionalName(), event.getServiceNames(), calendarSection(calendarEvent));

            case APPOINTMENT_CANCELLED -> """
                    <p>Ola, %s!</p>
                    <p>Seu agendamento em <strong>%s</strong> marcado para <strong>%s</strong> foi cancelado.</p>
                    %s""".formatted(event.getRecipientName(), event.getCompanyName(), when,
                    calendarEvent != null ? "<p>Se voce adicionou este horario ao seu calendario, abra o convite anexo para remove-lo.</p>\n" : "");

            case APPOINTMENT_RESCHEDULED -> {

                String previous = event.getPreviousStartAt() != null ? event.getPreviousStartAt().format(DATE_TIME_FORMATTER) : "";

                yield """
                        <p>Ola, %s!</p>
                        <p>Seu agendamento em <strong>%s</strong> foi remarcado de <strong>%s</strong> para <strong>%s</strong>, com %s.</p>
                        %s""".formatted(event.getRecipientName(), event.getCompanyName(), previous, when, event.getProfessionalName(), calendarSection(calendarEvent));

            }

        };
    }

    private String calendarSection(CalendarEvent calendarEvent) {

        if (calendarEvent == null) {
            return "";
        }

        String googleUrl = calendarInviteBuilder.buildGoogleCalendarUrl(calendarEvent).replace("&", "&amp;");

        return """
                <p><a href="%s">Adicionar ao Google Agenda</a></p>
                <p>Usa iPhone, Outlook ou outro calendario? Abra o convite anexo (%s).</p>
                """.formatted(googleUrl, INVITE_FILE_NAME);
    }

}
