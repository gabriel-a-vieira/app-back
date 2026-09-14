package com.softix.app_back.auth.external;

import com.softix.app_back.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import utils.model.RootEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "user_external_identity", uniqueConstraints = {
        @UniqueConstraint(name = "uk_external_identity_provider_user", columnNames = {"provider", "provider_user_id"}),
        @UniqueConstraint(name = "uk_external_identity_user_provider", columnNames = {"user_id", "provider"})
})
public class UserExternalIdentity extends RootEntity {

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false, length = 30)
    private AuthProvider provider;

    @Column(name = "provider_user_id", nullable = false, length = 255)
    private String providerUserId;

    @Column(name = "provider_email", length = 255)
    private String providerEmail;

}