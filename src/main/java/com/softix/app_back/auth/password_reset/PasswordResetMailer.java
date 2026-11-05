package com.softix.app_back.auth.password_reset;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Sends the password reset code by e-mail (same Gmail SMTP setup as
 * EmailNotificationChannel).
 *
 * Kept separate from NotificationService on purpose: that one fans an
 * appointment event out to every channel (e-mail today, WhatsApp/Telegram
 * later), while a reset code must only ever go to the account's own e-mail.
 *
 * Runs @Async so the request endpoint answers in the same time whether the
 * e-mail exists or not (no account enumeration via response time).
 */
@Slf4j
@Component
public class PasswordResetMailer {

    private final JavaMailSender mailSender;

    private final String fromEmail;

    public PasswordResetMailer(JavaMailSender mailSender,
                               @Value("${spring.mail.username}") String fromEmail) {
        this.mailSender = mailSender;
        this.fromEmail = fromEmail;
    }

    @Async
    public void sendCode(String toEmail, String recipientName, String code, long validMinutes) {

        try {

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Código para redefinir sua senha - Softix");
            helper.setText("""
                    <p>Olá, %s!</p>
                    <p>Recebemos um pedido para redefinir a senha da sua conta.</p>
                    <p>Seu código é: <strong style="font-size:20px;letter-spacing:4px">%s</strong></p>
                    <p>Ele vale por %d minutos e só pode ser usado uma vez.</p>
                    <p>Se não foi você, ignore este e-mail: sua senha continua a mesma.</p>
                    """.formatted(recipientName, code, validMinutes), true);

            mailSender.send(message);

            log.info("E-mail de redefinicao de senha enviado para {}", toEmail);

        } catch (MessagingException | MailException e) {
            log.error("Falha ao enviar e-mail de redefinicao de senha para {}", toEmail, e);
        }

    }

}
