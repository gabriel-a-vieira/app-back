package com.softix.app_back.shared.email;

/**
 * A single transactional e-mail, independent of the provider that sends it.
 *
 * @param toEmail     recipient address (required)
 * @param toName      recipient display name (optional, may be null)
 * @param subject     subject line
 * @param htmlContent HTML body
 */
public record EmailMessage(String toEmail, String toName, String subject, String htmlContent) {
}
