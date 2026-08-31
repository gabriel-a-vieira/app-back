package com.softix.app_back.company.review;

import com.softix.app_back.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import utils.model.tenant.TenantEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "company_review", uniqueConstraints = {@UniqueConstraint(name = "uk_company_review_company_user", columnNames = {"company_id", "user_id"})})
public class CompanyReview extends TenantEntity {

    @Column(name = "user_id", length = 38, nullable = false)
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "comment", length = 2000)
    private String comment;

    @Column(name = "image_url", length = 1000)
    private String imageUrl;

}
