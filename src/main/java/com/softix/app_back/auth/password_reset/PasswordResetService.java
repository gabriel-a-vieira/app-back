package com.softix.app_back.auth.password_reset;

import com.softix.app_back.shared.exception.BusinessException;
import com.softix.app_back.user.User;
import com.softix.app_back.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

/**
 * "Esqueci minha senha" in two steps:
 * <ol>
 *   <li>{@link #requestCode}: e-mails a 6-digit code to the account.</li>
 *   <li>{@link #confirm}: checks the code and sets the new password.</li>
 * </ol>
 *
 * A 6-digit code (instead of a link with a token) because the Flutter app
 * has no URL-based routing yet, so a link couldn't open the right screen;
 * a code works the same on web and mobile.
 *
 * Security rules:
 * - requestCode never reveals whether the e-mail is registered.
 * - Only the BCrypt hash of the code is stored.
 * - A code expires after {@link #CODE_VALIDITY_MINUTES}, is single use, and
 *   is burned after {@link #MAX_ATTEMPTS} wrong guesses.
 * - Asking for a new code invalidates the previous ones; requests within
 *   {@link #RESEND_COOLDOWN_SECONDS} of the last one are ignored, so the
 *   endpoint can't be used to flood someone's inbox.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordResetService {

    static final long CODE_VALIDITY_MINUTES = 15;

    static final int MAX_ATTEMPTS = 5;

    static final long RESEND_COOLDOWN_SECONDS = 60;

    static final String INVALID_CODE_MESSAGE = "Código inválido ou expirado. Solicite um novo código.";

    private static final SecureRandom RANDOM = new SecureRandom();

    private final UserRepository userRepository;

    private final PasswordResetCodeRepository codeRepository;

    private final PasswordEncoder passwordEncoder;

    private final PasswordResetMailer mailer;

    @Transactional
    public void requestCode(String email) {

        Optional<User> found = userRepository.findByEmailIgnoreCase(email.trim());

        if (found.isEmpty()) {
            log.info("Pedido de redefinicao de senha para e-mail nao cadastrado, ignorado");
            return;
        }

        User user = found.get();
        LocalDateTime now = LocalDateTime.now();

        Optional<PasswordResetCode> latest = codeRepository.findFirstByUserIdAndUsedAtIsNullOrderByCreatedAtDesc(user.getId());

        if (latest.isPresent() && isWithinCooldown(latest.get(), now)) {
            log.info("Pedido de redefinicao de senha repetido dentro do intervalo minimo, ignorado");
            return;
        }

        invalidateOpenCodes(user.getId(), now);

        String code = generateCode();

        PasswordResetCode resetCode = new PasswordResetCode();
        resetCode.setUserId(user.getId());
        resetCode.setCodeHash(passwordEncoder.encode(code));
        resetCode.setExpiresAt(now.plusMinutes(CODE_VALIDITY_MINUTES));
        resetCode.setAttempts(0);
        codeRepository.save(resetCode);

        mailer.sendCode(user.getEmail(), user.getName(), code, CODE_VALIDITY_MINUTES);

    }

    @Transactional(noRollbackFor = BusinessException.class)
    public void confirm(String email, String code, String newPassword) {

        User user = userRepository.findByEmailIgnoreCase(email.trim())
                .orElseThrow(PasswordResetService::invalidCode);

        PasswordResetCode resetCode = codeRepository.findFirstByUserIdAndUsedAtIsNullOrderByCreatedAtDesc(user.getId())
                .orElseThrow(PasswordResetService::invalidCode);

        LocalDateTime now = LocalDateTime.now();

        if (resetCode.getExpiresAt().isBefore(now) || resetCode.getAttempts() >= MAX_ATTEMPTS) {
            throw invalidCode();
        }

        if (!passwordEncoder.matches(code, resetCode.getCodeHash())) {

            resetCode.setAttempts(resetCode.getAttempts() + 1);

            if (resetCode.getAttempts() >= MAX_ATTEMPTS) {
                resetCode.setUsedAt(now);
            }

            codeRepository.save(resetCode);
            throw invalidCode();

        }

        resetCode.setUsedAt(now);
        codeRepository.save(resetCode);

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

    }

    private boolean isWithinCooldown(PasswordResetCode latest, LocalDateTime now) {

        if (latest.getCreatedAt() == null) {
            return false;
        }

        LocalDateTime createdAt = LocalDateTime.ofInstant(latest.getCreatedAt().toInstant(), ZoneId.systemDefault());

        return createdAt.plusSeconds(RESEND_COOLDOWN_SECONDS).isAfter(now);

    }

    private void invalidateOpenCodes(String userId, LocalDateTime now) {

        for (PasswordResetCode open : codeRepository.findByUserIdAndUsedAtIsNull(userId)) {
            open.setUsedAt(now);
            codeRepository.save(open);
        }

    }

    private static String generateCode() {
        return String.format("%06d", RANDOM.nextInt(1_000_000));
    }

    private static BusinessException invalidCode() {
        return new BusinessException(HttpStatus.BAD_REQUEST, INVALID_CODE_MESSAGE);
    }

}
