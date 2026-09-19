package com.softix.app_back.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Fans a NotificationEvent out to every registered NotificationChannel
 * (Spring injects all NotificationChannel beans into the list automatically
 * -- adding a new channel later is just adding a new @Component, no wiring
 * change here). Runs off the request thread (@Async) and isolates one
 * channel's failure from the others and from the business operation that
 * triggered it -- a bad Resend API key should never fail an appointment
 * booking.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final List<NotificationChannel> channels;

    @Async
    public void notify(NotificationEvent event) {

        for (NotificationChannel channel : channels) {

            try {
                channel.send(event);
            } catch (Exception e) {
                log.error("Falha ao enviar notificacao {} via {}", event.getType(), channel.getClass().getSimpleName(), e);
            }

        }

    }

}
