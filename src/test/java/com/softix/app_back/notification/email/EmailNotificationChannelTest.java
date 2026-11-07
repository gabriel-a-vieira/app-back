package com.softix.app_back.notification.email;

import com.softix.app_back.notification.NotificationEvent;
import com.softix.app_back.notification.NotificationEventType;
import com.softix.app_back.shared.email.EmailMessage;
import com.softix.app_back.shared.email.EmailSendException;
import com.softix.app_back.shared.email.EmailSender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @InjectMocks
    private EmailNotificationChannel channel;

    private NotificationEvent createdEvent(String email) {
        return NotificationEvent.builder()
                .type(NotificationEventType.APPOINTMENT_CREATED)
                .recipientEmail(email)
                .recipientName("Cliente Teste")
                .companyName("Empresa X")
                .professionalName("Profissional Y")
                .serviceNames("Servico Z")
                .appointmentStartAt(LocalDateTime.of(2026, 11, 10, 14, 30))
                .build();
    }

    @Test
    void send_buildsTheMessageForTheEvent() {

        channel.send(createdEvent("cliente@teste.com"));

        ArgumentCaptor<EmailMessage> captor = ArgumentCaptor.forClass(EmailMessage.class);
        verify(emailSender).send(captor.capture());

        EmailMessage sent = captor.getValue();
        assertThat(sent.toEmail()).isEqualTo("cliente@teste.com");
        assertThat(sent.toName()).isEqualTo("Cliente Teste");
        assertThat(sent.subject()).isEqualTo("Agendamento confirmado - Empresa X");
        assertThat(sent.htmlContent()).contains("10/11/2026 as 14:30").contains("Profissional Y");
    }

    @Test
    void send_skipsWhenTheRecipientHasNoEmail() {

        channel.send(createdEvent(" "));

        verifyNoInteractions(emailSender);
    }

    @Test
    void send_doesNotPropagateProviderFailures() {

        doThrow(new EmailSendException("Brevo fora")).when(emailSender).send(any());

        assertThatCode(() -> channel.send(createdEvent("cliente@teste.com"))).doesNotThrowAnyException();
    }

}
