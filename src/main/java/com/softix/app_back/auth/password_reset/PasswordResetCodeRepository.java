package com.softix.app_back.auth.password_reset;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PasswordResetCodeRepository extends JpaRepository<PasswordResetCode, String> {

    Optional<PasswordResetCode> findFirstByUserIdAndUsedAtIsNullOrderByCreatedAtDesc(String userId);

    List<PasswordResetCode> findByUserIdAndUsedAtIsNull(String userId);

}
