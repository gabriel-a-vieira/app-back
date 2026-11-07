package com.softix.app_back.auth.password_reset;

import com.softix.app_back.shared.exception.BusinessException;
import com.softix.app_back.user.User;
import com.softix.app_back.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Covers the "esqueci minha senha" rules: no account enumeration, resend
 * cooldown, previous codes invalidated, and a code that expires, is single
 * use and gets burned after too many wrong guesses.
 *
 * Uses a real BCryptPasswordEncoder (not a mock) so the hash/match path is
 * the one production runs.
 */
@ExtendWith(MockitoExtension.class)
class PasswordResetServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordResetCodeRepository codeRepository;

    @Mock
    private PasswordResetMailer mailer;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(4);

    private PasswordResetService service;

    private User user;

    @BeforeEach
    void setUp() {
        service = new PasswordResetService(userRepository, codeRepository, passwordEncoder, mailer);

        user = new User();
        user.setId("user-1");
        user.setName("Ana");
        user.setEmail("ana@email.com");
        user.setPassword(passwordEncoder.encode("senha-antiga"));
    }

    // ---------- requestCode ----------

    @Test
    void requestCode_unknownEmail_doesNothingAndDoesNotFail() {
        when(userRepository.findByEmailIgnoreCase("nao@existe.com")).thenReturn(Optional.empty());

        service.requestCode("nao@existe.com");

        verify(codeRepository, never()).save(any());
        verify(mailer, never()).sendCode(anyString(), anyString(), anyString(), anyLong());
    }

    @Test
    void requestCode_knownEmail_storesHashedCodeAndEmailsThePlainCode() {
        when(userRepository.findByEmailIgnoreCase("ana@email.com")).thenReturn(Optional.of(user));
        when(codeRepository.findFirstByUserIdAndUsedAtIsNullOrderByCreatedAtDesc("user-1")).thenReturn(Optional.empty());
        when(codeRepository.findByUserIdAndUsedAtIsNull("user-1")).thenReturn(List.of());

        service.requestCode("  ana@email.com ");

        ArgumentCaptor<PasswordResetCode> saved = ArgumentCaptor.forClass(PasswordResetCode.class);
        verify(codeRepository).save(saved.capture());

        ArgumentCaptor<String> sentCode = ArgumentCaptor.forClass(String.class);
        verify(mailer).sendCode(eq("ana@email.com"), eq("Ana"), sentCode.capture(), eq(PasswordResetService.CODE_VALIDITY_MINUTES));

        assertThat(sentCode.getValue()).matches("\\d{6}");
        assertThat(saved.getValue().getCodeHash()).isNotEqualTo(sentCode.getValue());
        assertThat(passwordEncoder.matches(sentCode.getValue(), saved.getValue().getCodeHash())).isTrue();
        assertThat(saved.getValue().getUserId()).isEqualTo("user-1");
        assertThat(saved.getValue().getExpiresAt()).isAfter(LocalDateTime.now().plusMinutes(14));
    }

    @Test
    void requestCode_invalidatesPreviousOpenCodes() {
        PasswordResetCode old = codeCreatedSecondsAgo(600);

        when(userRepository.findByEmailIgnoreCase("ana@email.com")).thenReturn(Optional.of(user));
        when(codeRepository.findFirstByUserIdAndUsedAtIsNullOrderByCreatedAtDesc("user-1")).thenReturn(Optional.of(old));
        when(codeRepository.findByUserIdAndUsedAtIsNull("user-1")).thenReturn(List.of(old));

        service.requestCode("ana@email.com");

        assertThat(old.getUsedAt()).isNotNull();
        verify(mailer).sendCode(eq("ana@email.com"), eq("Ana"), anyString(), anyLong());
    }

    @Test
    void requestCode_withinCooldown_isIgnored() {
        PasswordResetCode recent = codeCreatedSecondsAgo(10);

        when(userRepository.findByEmailIgnoreCase("ana@email.com")).thenReturn(Optional.of(user));
        when(codeRepository.findFirstByUserIdAndUsedAtIsNullOrderByCreatedAtDesc("user-1")).thenReturn(Optional.of(recent));

        service.requestCode("ana@email.com");

        assertThat(recent.getUsedAt()).isNull();
        verify(codeRepository, never()).save(any());
        verify(mailer, never()).sendCode(anyString(), anyString(), anyString(), anyLong());
    }

    // ---------- confirm ----------

    @Test
    void confirm_correctCode_changesPasswordAndBurnsCode() {
        PasswordResetCode code = openCode("123456", LocalDateTime.now().plusMinutes(10));
        givenLatestCode(code);

        service.confirm("ana@email.com", "123456", "senha-nova");

        assertThat(code.getUsedAt()).isNotNull();
        assertThat(passwordEncoder.matches("senha-nova", user.getPassword())).isTrue();
        verify(userRepository).save(user);
    }

    @Test
    void confirm_wrongCode_countsAttemptAndKeepsPassword() {
        PasswordResetCode code = openCode("123456", LocalDateTime.now().plusMinutes(10));
        givenLatestCode(code);
        String oldHash = user.getPassword();

        assertInvalidCode(() -> service.confirm("ana@email.com", "000000", "senha-nova"));

        assertThat(code.getAttempts()).isEqualTo(1);
        assertThat(code.getUsedAt()).isNull();
        assertThat(user.getPassword()).isEqualTo(oldHash);
        verify(userRepository, never()).save(any());
    }

    @Test
    void confirm_lastAllowedWrongAttempt_burnsTheCode() {
        PasswordResetCode code = openCode("123456", LocalDateTime.now().plusMinutes(10));
        code.setAttempts(PasswordResetService.MAX_ATTEMPTS - 1);
        givenLatestCode(code);

        assertInvalidCode(() -> service.confirm("ana@email.com", "000000", "senha-nova"));

        assertThat(code.getUsedAt()).isNotNull();
    }

    @Test
    void confirm_tooManyAttempts_rejectsEvenTheCorrectCode() {
        PasswordResetCode code = openCode("123456", LocalDateTime.now().plusMinutes(10));
        code.setAttempts(PasswordResetService.MAX_ATTEMPTS);
        givenLatestCode(code);

        assertInvalidCode(() -> service.confirm("ana@email.com", "123456", "senha-nova"));

        verify(userRepository, never()).save(any());
    }

    @Test
    void confirm_expiredCode_isRejected() {
        PasswordResetCode code = openCode("123456", LocalDateTime.now().minusMinutes(1));
        givenLatestCode(code);

        assertInvalidCode(() -> service.confirm("ana@email.com", "123456", "senha-nova"));

        verify(userRepository, never()).save(any());
    }

    @Test
    void confirm_noOpenCode_isRejected() {
        when(userRepository.findByEmailIgnoreCase("ana@email.com")).thenReturn(Optional.of(user));
        when(codeRepository.findFirstByUserIdAndUsedAtIsNullOrderByCreatedAtDesc("user-1")).thenReturn(Optional.empty());

        assertInvalidCode(() -> service.confirm("ana@email.com", "123456", "senha-nova"));
    }

    @Test
    void confirm_unknownEmail_returnsSameGenericError() {
        when(userRepository.findByEmailIgnoreCase("nao@existe.com")).thenReturn(Optional.empty());

        assertInvalidCode(() -> service.confirm("nao@existe.com", "123456", "senha-nova"));
    }

    // ---------- helpers ----------

    private void givenLatestCode(PasswordResetCode code) {
        when(userRepository.findByEmailIgnoreCase("ana@email.com")).thenReturn(Optional.of(user));
        when(codeRepository.findFirstByUserIdAndUsedAtIsNullOrderByCreatedAtDesc("user-1")).thenReturn(Optional.of(code));
    }

    private PasswordResetCode openCode(String plainCode, LocalDateTime expiresAt) {
        PasswordResetCode code = new PasswordResetCode();
        code.setUserId("user-1");
        code.setCodeHash(passwordEncoder.encode(plainCode));
        code.setExpiresAt(expiresAt);
        return code;
    }

    private PasswordResetCode codeCreatedSecondsAgo(long seconds) {
        PasswordResetCode code = openCode("111111", LocalDateTime.now().plusMinutes(10));
        code.setCreatedAt(Timestamp.valueOf(LocalDateTime.now().minusSeconds(seconds)));
        return code;
    }

    private static void assertInvalidCode(Runnable call) {
        assertThatThrownBy(call::run)
                .isInstanceOf(BusinessException.class)
                .hasMessage(PasswordResetService.INVALID_CODE_MESSAGE)
                .extracting("status").isEqualTo(HttpStatus.BAD_REQUEST);
    }

}
