package com.softix.app_back.country;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CountryService {

    @Autowired
    CountryRepository countryRepository;

    @Transactional
    public Country save(CountryDTO dto) {

        Country country = new Country();
        country.setName(dto.getName());

        return countryRepository.save(country);

    }

}
