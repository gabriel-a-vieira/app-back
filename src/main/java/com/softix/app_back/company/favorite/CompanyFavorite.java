package com.softix.app_back.company.favorite;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import utils.model.RootEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "company_favorite", uniqueConstraints = {@UniqueConstraint(name = "uk_company_favorite_company_user", columnNames = {"company_id", "user_id"})})
public class CompanyFavorite extends RootEntity {

    /*
     * Nao usa TenantEntity de proposito: company_id aqui e a empresa
     * favoritada (identifica o registro), nao a empresa "dona" da
     * linha. Se estendesse TenantEntity, o filtro Hibernate de
     * multi-tenancy passaria a restringir as buscas ao companyId do
     * usuario logado (quando ele for PROFESSIONAL/COMPANY_ADMIN),
     * tornando invisivel o favorito de qualquer empresa que nao seja
     * a dele - e gerando violacao de unique constraint ao tentar
     * favoritar de novo algo que na verdade ja estava favoritado.
     */
    @Column(name = "company_id", length = 38, nullable = false)
    private String companyId;

    @Column(name = "user_id", length = 38, nullable = false)
    private String userId;

}
