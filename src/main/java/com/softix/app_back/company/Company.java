package com.softix.app_back.company;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import utils.model.RootEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "company")
public class Company extends RootEntity {

    @Column(name = "legal_name")
    private String legalName;

    @Column(name = "trade_name")
    private String tradeName;

    @Column(name = "cnpj", length = 11)
    private String cnpj;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private CompanyType type;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CompanyStatus status;

}
