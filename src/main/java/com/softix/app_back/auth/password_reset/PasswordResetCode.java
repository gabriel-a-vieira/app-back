package com.softix.app_back.auth.password_reset;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import utils.model.RootEntity;

import java.time.LocalDateTime;

/**
 * One "esqueci minha senha" code sent by e-mail. Global (not tenant-scoped):
 * the flow is anonymous and a user is looked up by e-mail across companies,
 * same as /auth/login.
 *
 * Only a BCrypt hash of the 6-digit code is stored, never the code itself.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "password_reset_code")
public class PasswordResetCode extends RootEntity {

    @Column(name = "user_id", length = 38, nullable = false)
    private String userId;

    @Column(name = "code_hash", length = 100, nullable = false)
    private String codeHash;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "attempts", nullable = false)
    private int attempts;

    @Column(name = "used_at")
    private LocalDateTime usedAt;

}
