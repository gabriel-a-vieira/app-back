package com.softix.app_back.notification.email;

import com.softix.app_back.calendar.CalendarInviteBuilder;
import com.softix.app_back.notification.NotificationEvent;
import com.softix.app_back.notification.NotificationEventType;
import com.softix.app_back.shared.email.EmailMessage;
import com.softix.app_back.shared.email.EmailSendException;
import com.softix.app_back.shared.email.EmailSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class EmailNotificationChannelTest {

    @Mock
    private EmailSender emailSender;

    private EmailNotificationChannel channel;

    @BeforeEach
    void setUp() {
        // real builder: the invite content is part of what this channel promises
        channel = new EmailNotificationChannel(emailSender, new CalendarInviteBuilder("America/Sao_Paulo"), "noreply@teste.com");
    }

    private NotificationEvent event(NotificationEventType type, String email) {
        return NotificationEvent.builder()
                .type(type)
                .recipientEmail(email)
                .recipientName("Cliente Teste")
                .appointmentId("apt-1")
                .appointmentVersion(1)
                .companyName("Empresa X")
                .professionalName("Profissional Y")
                .serviceNames("Servico Z")
                .appointmentStartAt(LocalDateTime.of(2026, 11, 10, 14, 30))
                .appointmentEndAt(LocalDateTime.of(2026, 11, 10, 15, 0))
                .build();
    }

    private EmailMessage sent() {
        ArgumentCaptor<EmailMessage> captor = ArgumentCaptor.forClass(EmailMessage.class);
        verify(emailSender).send(captor.capture());
        return captor.getValue();
    }

    @Test
    void send_buildsTheMessageForTheEvent() {

        channel.send(event(NotificationEventType.APPOINTMENT_CREATED, "cliente@teste.com"));

        EmailMessage sent = sent();
        assertThat(sent.toEmail()).isEqualTo("cliente@teste.com");
        assertThat(sent.toName()).isEqualTo("Cliente Teste");
        assertThat(sent.subject()).isEqualTo("Agendamento confirmado - Empresa X");
        assertThat(sent.htmlContent()).contains("10/11/2026 as 14:30").contains("Profissional Y");
    }

    @Test
    void send_attachesACalendarInviteAndGoogleLinkOnCreate() {

        channel.send(event(NotificationEventType.APPOINTMENT_CREATED, "cliente@teste.com"));

        EmailMessage sent = sent();
        assertThat(sent.htmlContent()).contains("https://calendar.google.com/calendar/render?action=TEMPLATE&amp;text=");
        assertThat(sent.attachments()).hasSize(1);
        assertThat(sent.attachments().get(0).fileName()).isEqualTo("agendamento.ics");

        String ics = new String(sent.attachments().get(0).content(), StandardCharsets.UTF_8);
        assertThat(ics).contains("METHOD:REQUEST").contains("UID:apt-1@softix-schedule").contains("SEQUENCE:1");
    }

    @Test
    void send_attachesACancellationOnCancel() {

        channel.send(event(NotificationEventType.APPOINTMENT_CANCELLED, "cliente@teste.com"));

        EmailMessage sent = sent();
        String ics = new String(sent.attachments().get(0).content(), StandardCharsets.UTF_8);
        assertThat(ics).contains("METHOD:CANCEL").contains("UID:apt-1@softix-schedule");
        assertThat(sent.htmlContent()).doesNotContain("calendar.google.com");
    }

    @Test
    void send_withoutAppointmentDataStillSendsTheEmailWithoutInvite() {

        channel.send(event(NotificationEventType.APPOINTMENT_CREATED, "cliente@teste.com").toBuilder().appointmentId(null).build());

        EmailMessage sent = sent();
        assertThat(sent.attachments()).isEmpty();
        assertThat(sent.htmlContent()).doesNotContain("calendar.google.com");
    }

    @Test
    void send_skipsWhenTheRecipientHasNoEmail() {

        channel.send(event(NotificationEventType.APPOINTMENT_CREATED, " "));

        verifyNoInteractions(emailSender);
    }

    @Test
    void send_doesNotPropagateProviderFailures() {

        doThrow(new EmailSendException("Brevo fora")).when(emailSender).send(any());

        assertThatCode(() -> channel.send(event(NotificationEventType.APPOINTMENT_CREATED, "cliente@teste.com"))).doesNotThrowAnyException();
    }

}
