package com.softix.app_back.company.public_api;

import com.softix.app_back.address.Address;
import com.softix.app_back.company.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class PublicCompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Transactional(readOnly = true)
    public PublicCompanyDetailDTO findDetail(String companyId) {

        Company company = companyRepository.findById(companyId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa nao encontrada"));

        return new PublicCompanyDetailDTO(

                company.getId(),
                company.getLegalName(), company.getTradeName(),
                company.getImageUrl(),
                buildAddress(company.getAddress()),
                buildOpeningHours(company),
                company.getPaymentMethods(),
                company.getAmenities(),
                company.getInstagramUrl(), company.getFacebookUrl(), company.getWebsiteUrl(), company.getTiktokUrl());

    }

    private List<PublicCompanyOpeningDayDTO> buildOpeningHours(Company company) {

        List<PublicCompanyOpeningDayDTO> result = new ArrayList<>();

        for (DayOfWeek day : DayOfWeek.values()) {

            List<PublicCompanyTimeRangeDTO> intervals = company.getOpeningHours().stream().filter(hour -> hour.getDayWeek() == day).sorted(Comparator.comparing(CompanyOpeningHour::getStartTime)).map(hour -> new PublicCompanyTimeRangeDTO(hour.getStartTime(), hour.getEndTime())).toList();
            result.add(new PublicCompanyOpeningDayDTO(day, intervals));

        }

        return result;
    }

    private PublicCompanyAddressDTO buildAddress(Address address) {

        if (address == null) {
            return null;
        }

        List<String> parts = new ArrayList<>();

        if (address.getStreet() != null && !address.getStreet().isBlank()) {

            String street = address.getStreet();

            if (address.getNumber() != null && !address.getNumber().isBlank()) {
                street += ", " + address.getNumber();
            }

            parts.add(street);
        }

        if (address.getNeighborhood() != null && !address.getNeighborhood().isBlank()) {
            parts.add(address.getNeighborhood());
        }

        if (address.getCity() != null) {

            String city = address.getCity().getName();

            if (address.getCity().getState() != null) {
                city += "/" + address.getCity().getState().getAbbreviation();
            }

            parts.add(city);
        }

        if (address.getPostalCode() != null && !address.getPostalCode().isBlank()) {
            parts.add(address.getPostalCode());
        }

        return new PublicCompanyAddressDTO(String.join(" - ", parts), address.getLatitude(), address.getLongitude());

    }

}