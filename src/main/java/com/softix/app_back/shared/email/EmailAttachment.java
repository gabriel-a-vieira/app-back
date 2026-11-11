package com.softix.app_back.shared.email;

/**
 * A file attached to an EmailMessage.
 *
 * @param fileName name shown to the recipient, with extension -- providers
 *                 (Brevo) derive the content type from it and reject unknown
 *                 extensions
 * @param content  raw file bytes
 */
public record EmailAttachment(String fileName, byte[] content) {
}
