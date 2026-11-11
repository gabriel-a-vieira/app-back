package com.softix.app_back.shared.email;

import java.util.List;

/**
 * A single transactional e-mail, independent of the provider that sends it.
 *
 * @param toEmail     recipient address (required)
 * @param toName      recipient display name (optional, may be null)
 * @param subject     subject line
 * @param htmlContent HTML body
 * @param attachments files attached to the e-mail (never null; empty when none)
 */
public record EmailMessage(String toEmail, String toName, String subject, String htmlContent, List<EmailAttachment> attachments) {

    public EmailMessage {
        attachments = attachments == null ? List.of() : List.copyOf(attachments);
    }

    public EmailMessage(String toEmail, String toName, String subject, String htmlContent) {
        this(toEmail, toName, subject, htmlContent, List.of());
    }

}
