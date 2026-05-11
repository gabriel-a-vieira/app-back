package com.softix.app_back.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

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

        companyRepository.save(company);

        return company;

    }

    public Page<CompanyResponse> findPublicCompanies(CompanyType type, String search, Pageable pageable) {

        String normalizedSearch = search == null || search.isBlank() ? null : search.trim();
        return companyRepository.findPublicCompanies(type, normalizedSearch, pageable).map(CompanyResponse::fromEntity);

    }

    public List<CompanyTypeResponse> findCompanyTypes() {
        return Arrays.stream(CompanyType.values())
                .map(CompanyTypeResponse::fromEnum)
                .toList();
    }

}
