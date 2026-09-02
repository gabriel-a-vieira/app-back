package com.softix.app_back.company;

import com.softix.app_back.address.Address;
import com.softix.app_back.city.City;
import com.softix.app_back.city.CityRepository;
import com.softix.app_back.payment.PaymentMethod;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    CityRepository cityRepository;


    @Transactional(readOnly = true)
    public Page<CompanyAdminResponse> findAdminCompanies(CompanyType type, CompanyStatus status, String search, Pageable pageable) {

        String normalizedSearch = StringUtils.trimToNull(search);
        return companyRepository.findAdminCompanies(type, status, normalizedSearch, pageable).map(CompanyAdminResponse::fromEntity);

    }


    @Transactional(readOnly = true)
    public CompanyAdminDetailResponse findAdminById(String id) {

        Company company = findCompany(id);

        company.getPaymentMethods().size();
        company.getAmenities().size();
        company.getOpeningHours().size();

        return CompanyAdminDetailResponse.fromEntity(company);

    }


    @Transactional
    public CompanyAdminDetailResponse save(CompanySaveRequest request) {

        validateRequiredFields(request);

        String cnpj = normalizeCnpj(request.getCnpj());

        if (companyRepository.existsByCnpj(cnpj)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ja existe uma empresa cadastrada com este CNPJ");
        }

        Company company = new Company();

        applyRequest(company, request);

        company.setStatus(request.getStatus() != null ? request.getStatus() : CompanyStatus.ACTIVE);

        companyRepository.save(company);

        return CompanyAdminDetailResponse.fromEntity(company);

    }


    @Transactional
    public CompanyAdminDetailResponse update(String id, CompanySaveRequest request) {

        validateRequiredFields(request);

        Company company = findCompany(id);
        String cnpj = normalizeCnpj(request.getCnpj());

        if (companyRepository.existsByCnpjAndIdNot(cnpj, id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ja existe outra empresa cadastrada com este CNPJ");
        }

        applyRequest(company, request);

        if (request.getStatus() != null) {
            company.setStatus(request.getStatus());
        }

        companyRepository.save(company);

        return CompanyAdminDetailResponse.fromEntity(company);

    }


    @Transactional
    public void deactivateMany(List<String> ids) {

        if (ids == null || ids.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhuma empresa informada");
        }

        List<Company> companies = companyRepository.findAllById(ids);

        for (Company company : companies) {
            company.setStatus(CompanyStatus.INACTIVE);
        }

        companyRepository.saveAll(companies);

    }


    @Transactional(readOnly = true)
    public Page<CompanyResponse> findPublicCompanies(CompanyType type, String search, Pageable pageable) {

        String normalizedSearch = StringUtils.trimToNull(search);
        return companyRepository.findPublicCompanies(type, normalizedSearch, pageable).map(CompanyResponse::fromEntity);

    }


    public List<CompanyTypeResponse> findCompanyTypes() {
        return Arrays.stream(CompanyType.values()).map(CompanyTypeResponse::fromEnum).toList();
    }


    public List<String> findCompanyStatuses() {
        return Arrays.stream(CompanyStatus.values()).map(Enum::name).toList();
    }


    public List<String> findPaymentMethods() {
        return Arrays.stream(PaymentMethod.values()).map(Enum::name).toList();
    }


    public List<String> findAmenities() {
        return Arrays.stream(CompanyAmenity.values()).map(Enum::name).toList();
    }


    private void applyRequest(Company company, CompanySaveRequest request) {

        company.setLegalName(StringUtils.trimToNull(request.getLegalName()));
        company.setTradeName(StringUtils.trimToNull(request.getTradeName()));
        company.setCnpj(normalizeCnpj(request.getCnpj()));
        company.setType(request.getType());
        company.setAddress(buildAddress(request));
        company.setImageUrl(StringUtils.trimToNull(request.getImageUrl()));
        company.setInstagramUrl(StringUtils.trimToNull(request.getInstagramUrl()));
        company.setFacebookUrl(StringUtils.trimToNull(request.getFacebookUrl()));
        company.setWebsiteUrl(StringUtils.trimToNull(request.getWebsiteUrl()));
        company.setTiktokUrl(StringUtils.trimToNull(request.getTiktokUrl()));
        company.getPaymentMethods().clear();

        if (request.getPaymentMethods() != null) {
            company.getPaymentMethods().addAll(request.getPaymentMethods());
        }


        company.getAmenities().clear();

        if (request.getAmenities() != null) {
            company.getAmenities().addAll(request.getAmenities());
        }


        company.getOpeningHours().clear();

        if (request.getOpeningHours() != null) {

            validateOpeningHours(request.getOpeningHours());

            for (CompanyOpeningHourRequest hour : request.getOpeningHours()) {
                company.getOpeningHours().add(new CompanyOpeningHour(hour.getDayWeek(), hour.getStartTime(), hour.getEndTime()));
            }

        }

    }


    private Address buildAddress(CompanySaveRequest request) {

        boolean hasAddress = StringUtils.isNotBlank(request.getZipCode()) || StringUtils.isNotBlank(request.getStreet()) || StringUtils.isNotBlank(request.getCity());

        if (!hasAddress) {
            return null;
        }

        Address address = new Address();

        address.setNumber(StringUtils.trimToNull(request.getNumber()));
        address.setStreet(StringUtils.trimToNull(request.getStreet()));
        address.setComplement(StringUtils.trimToNull(request.getComplement()));
        address.setPostalCode(StringUtils.getDigits(request.getZipCode()));
        address.setNeighborhood(StringUtils.trimToNull(request.getDistrict()));

        City city = cityRepository.findByNameAndStateAbbreviation(request.getCity(), request.getState());

        if (city == null) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cidade nao encontrada");

        }

        address.setCity(city);

        return address;

    }


    private void validateRequiredFields(CompanySaveRequest request) {

        if (StringUtils.isBlank(request.getLegalName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Razao social obrigatoria");
        }

        if (StringUtils.isBlank(request.getTradeName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome fantasia obrigatorio");
        }

        if (StringUtils.isBlank(request.getCnpj())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CNPJ obrigatorio");
        }

        if (request.getType() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de empresa obrigatorio");
        }

    }


    private void validateOpeningHours(List<CompanyOpeningHourRequest> hours) {

        for (CompanyOpeningHourRequest hour : hours) {

            if (hour.getDayWeek() == null || hour.getStartTime() == null || hour.getEndTime() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Horario de funcionamento invalido");
            }

            if (!hour.getStartTime().isBefore(hour.getEndTime())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Horario inicial deve ser menor que o horario final");
            }

        }


        Map<DayOfWeek, List<CompanyOpeningHourRequest>> byDay = hours.stream().collect(Collectors.groupingBy(CompanyOpeningHourRequest::getDayWeek));


        for (List<CompanyOpeningHourRequest> dayHours : byDay.values()) {

            dayHours.sort(Comparator.comparing(CompanyOpeningHourRequest::getStartTime));

            for (int i = 1; i < dayHours.size(); i++) {

                CompanyOpeningHourRequest previous = dayHours.get(i - 1);
                CompanyOpeningHourRequest current = dayHours.get(i);

                if (current.getStartTime().isBefore(previous.getEndTime())) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Existem horarios de funcionamento sobrepostos");
                }

            }

        }

    }


    private Company findCompany(String id) {
        return companyRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa nao encontrada"));
    }


    private String normalizeCnpj(String value) {

        if (value == null) {
            return null;
        }

        return value.replaceAll("[^A-Za-z0-9]", "").toUpperCase();

    }

}