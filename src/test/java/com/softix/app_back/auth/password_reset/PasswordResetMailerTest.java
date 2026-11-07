package com.softix.app_back.auth.password_reset;

import com.softix.app_back.shared.email.EmailMessage;
import com.softix.app_back.shared.email.EmailSendException;
import com.softix.app_back.shared.email.EmailSender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PasswordResetMailerTest {

    @Mock
    private EmailSender emailSender;

    @InjectMocks
    private PasswordResetMailer mailer;

    @Test
    void sendCode_sendsTheCodeToTheAccountEmail() {

        mailer.sendCode("cliente@teste.com", "Cliente", "042137", 15);

        ArgumentCaptor<EmailMessage> captor = ArgumentCaptor.forClass(EmailMessage.class);
        verify(emailSender).send(captor.capture());

        EmailMessage sent = captor.getValue();
        assertThat(sent.toEmail()).isEqualTo("cliente@teste.com");
        assertThat(sent.subject()).isEqualTo(PasswordResetMailer.SUBJECT);
        assertThat(sent.htmlContent()).contains("042137").contains("15 minutos");
    }

    @Test
    void sendCode_doesNotPropagateProviderFailures() {

        doThrow(new EmailSendException("Brevo fora")).when(emailSender).send(any());

        assertThatCode(() -> mailer.sendCode("cliente@teste.com", "Cliente", "042137", 15)).doesNotThrowAnyException();
    }

}
