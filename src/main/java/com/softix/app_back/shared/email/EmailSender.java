package com.softix.app_back.shared.email;

/**
 * Sends transactional e-mails. The rest of the app depends only on this
 * interface, so switching provider (Brevo today; Resend, SES etc. later) means
 * writing a new implementation without touching the callers.
 *
 * Implementations are synchronous: callers that must not block the request
 * (e.g. PasswordResetMailer, NotificationService) already run @Async.
 */
public interface EmailSender {

    /**
     * @throws EmailSendException when the e-mail could not be sent
     *                            (misconfiguration, network failure, provider rejection)
     */
    void send(EmailMessage message);

}
