package com.softix.app_back.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Transactional
    public Company save(CompanyDTO dto) {

        Company company = new Company();

        company.setLegalName(dto.getLegalName());
        company.setTradeName(dto.getTradeName());
        company.setCnpj(dto.getCnpj());
        company.setType(dto.getType());
        company.setStatus(dto.getStatus());

        companyRepository.save(company);

        return company;

    }

}
