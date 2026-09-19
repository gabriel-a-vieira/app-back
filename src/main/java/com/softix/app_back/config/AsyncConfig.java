package com.softix.app_back.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Enables @Async, used by NotificationService so sending a notification
 * (network call to an external provider) never blocks the request thread
 * that created/cancelled/rescheduled the appointment.
 */
@Configuration
@EnableAsync
public class AsyncConfig {
}
