package com.softix.app_back.company;

import jakarta.persistence.*;
import lombok.Data;
import utils.model.RootEntity;

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

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private CompanyStatus status;

}
