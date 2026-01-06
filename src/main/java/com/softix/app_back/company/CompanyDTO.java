package com.softix.app_back.company;

import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class CompanyDTO {

    private String id;
    private String legalName;
    private String tradeName;
    private String cnpj;
    private CompanyType type;
    private CompanyStatus status;

    public CompanyDTO() {}

    public CompanyDTO(Company company) {
        BeanUtils.copyProperties(company, this);
    }

}
