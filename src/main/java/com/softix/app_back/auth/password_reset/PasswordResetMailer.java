package com.softix.app_back.auth.password_reset;

import com.softix.app_back.shared.email.EmailMessage;
import com.softix.app_back.shared.email.EmailSendException;
import com.softix.app_back.shared.email.EmailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Sends the password reset code by e-mail (same EmailSender as
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
@RequiredArgsConstructor
public class PasswordResetMailer {

    static final String SUBJECT = "Código para redefinir sua senha - Softix";

    private final EmailSender emailSender;

    @Async
    public void sendCode(String toEmail, String recipientName, String code, long validMinutes) {

        String html = """
                <p>Olá, %s!</p>
                <p>Recebemos um pedido para redefinir a senha da sua conta.</p>
                <p>Seu código é: <strong style="font-size:20px;letter-spacing:4px">%s</strong></p>
                <p>Ele vale por %d minutos e só pode ser usado uma vez.</p>
                <p>Se não foi você, ignore este e-mail: sua senha continua a mesma.</p>
                """.formatted(recipientName, code, validMinutes);

        try {

            emailSender.send(new EmailMessage(toEmail, recipientName, SUBJECT, html));

            log.info("E-mail de redefinicao de senha enviado para {}", toEmail);

        } catch (EmailSendException e) {
            log.error("Falha ao enviar e-mail de redefinicao de senha para {}", toEmail, e);
        }

    }

}
