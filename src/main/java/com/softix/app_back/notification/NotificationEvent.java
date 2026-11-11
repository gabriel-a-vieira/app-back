package com.softix.app_back.notification;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

/**
 * Channel-agnostic payload for a notification: whatever an
 * EmailNotificationChannel/WhatsAppNotificationChannel/etc. needs to build
 * its own message, without any of them knowing about Appointment/Client/etc.
 * entities directly.
 */
@Value
@Builder(toBuilder = true)
public class NotificationEvent {

    NotificationEventType type;

    String recipientEmail;
    String recipientName;

    String appointmentId;

    String companyName;
    String companyAddress;
    String professionalName;
    String serviceNames;

    LocalDateTime appointmentStartAt;
    LocalDateTime appointmentEndAt;

    /**
     * Only set for APPOINTMENT_RESCHEDULED, so a channel can show "de X para Y".
     */
    LocalDateTime previousStartAt;

    /**
     * Appointment's committed @Version at the time of the change -- used as the
     * calendar invite SEQUENCE, so each update outranks the previous invite.
     */
    Integer appointmentVersion;

}
