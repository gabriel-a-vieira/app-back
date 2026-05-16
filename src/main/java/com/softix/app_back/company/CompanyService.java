package com.softix.app_back.company;

import com.softix.app_back.address.Address;
import com.softix.app_back.address.AddressDTO;
import com.softix.app_back.city.City;
import com.softix.app_back.city.CityRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    CityRepository cityRepository;

    @Transactional
    public Company save(CompanyDTO dto) {

        Company company = new Company();

        company.setLegalName(dto.getLegalName());
        company.setTradeName(dto.getTradeName());
        company.setCnpj(dto.getCnpj());
        company.setType(dto.getType());

        company.setAddress(getAddress(dto.getAddress()));

        company.setImageUrl(dto.getImageUrl());
        company.setInstagramUrl(dto.getInstagramUrl());
        company.setFacebookUrl(dto.getFacebookUrl());
        company.setWebsiteUrl(dto.getWebsiteUrl());
        company.setTiktokUrl(dto.getTiktokUrl());

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

    public Address getAddress(AddressDTO addressDTO) {

        Address address = new Address();

        if (addressDTO != null) {

            address.setNumber(addressDTO.getNumber());
            address.setStreet(addressDTO.getStreet());
            address.setComplement(addressDTO.getComplement());
            address.setPostalCode(StringUtils.getDigits(addressDTO.getPostalCode()));
            address.setNeighborhood(addressDTO.getNeighborhood());
            address.setLatitude(addressDTO.getLatitude());
            address.setLongitude(addressDTO.getLongitude());

            City city;

            if (addressDTO.getIdCity() != null) {
                city = cityRepository.findById(addressDTO.getIdCity()).orElse(null);
            } else {
                city = cityRepository.findByNameAndStateAbbreviation(addressDTO.getCity(), addressDTO.getState());
            }

            if (city != null) {
                address.setCity(city);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cidade não encontrada!");
            }

        }

        return address;

    }

}
