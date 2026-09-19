package com.softix.app_back.notification;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

/**
 * Covers NotificationService's fan-out: every registered channel is called
 * for a given event, and one channel throwing never stops the others from
 * running -- a bad Resend API key (email) should never silently swallow a
 * WhatsApp/Telegram channel added later, and vice versa.
 */
@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationChannel firstChannel;

    @Mock
    private NotificationChannel secondChannel;

    private NotificationEvent event() {
        return NotificationEvent.builder()
                .type(NotificationEventType.APPOINTMENT_CREATED)
                .recipientEmail("cliente@teste.com")
                .recipientName("Cliente Teste")
                .build();
    }

    @Test
    void notify_sendsTheEventToEveryRegisteredChannel() {

        NotificationEvent event = event();

        NotificationService service = new NotificationService(List.of(firstChannel, secondChannel));

        service.notify(event);

        verify(firstChannel).send(event);
        verify(secondChannel).send(event);
    }

    @Test
    void notify_stillCallsTheRemainingChannelsWhenOneThrows() {

        NotificationEvent event = event();

        doThrow(new RuntimeException("Resend indisponivel")).when(firstChannel).send(event);

        NotificationService service = new NotificationService(List.of(firstChannel, secondChannel));

        service.notify(event);

        verify(secondChannel).send(event);
    }

}
