package com.softix.app_back.country;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

    @Transactional
    public Country save(CountryDTO dto) {

        Country country = new Country();
        country.setName(dto.getName());

        return countryRepository.save(country);

    }

}
