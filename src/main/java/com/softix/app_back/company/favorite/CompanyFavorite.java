package com.softix.app_back.company.favorite;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import utils.model.tenant.TenantEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "company_favorite", uniqueConstraints = {@UniqueConstraint(name = "uk_company_favorite_company_user", columnNames = {"company_id", "user_id"})})
public class CompanyFavorite extends TenantEntity {

    @Column(name = "user_id", length = 38, nullable = false)
    private String userId;

}
