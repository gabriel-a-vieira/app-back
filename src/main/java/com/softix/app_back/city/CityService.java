package com.softix.app_back.city;

import com.softix.app_back.country.CountryRepository;
import com.softix.app_back.state.StateRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CityService {

    @Autowired
    CityRepository cityRepository;

    @Autowired
    StateRepository stateRepository;

    @Autowired
    CountryRepository countryRepository;

    @Transactional
    public City save(CityDTO dto) {

        City city = new City();

        city.setName(dto.getName());

        if (dto.getState() != null) {

            UUID idState;

            if (StringUtils.isNotBlank(dto.getState().getName()) && StringUtils.isNotBlank(dto.getState().getAbbreviation())) {
                idState = stateRepository.findIdByNameAndAbbreviation(dto.getState().getName(), dto.getState().getAbbreviation());

                if (idState != null) {
                    city.setIdState(idState);
                }

            }

        }

        if (dto.getCountry() != null) {

            UUID idCountry;

            if (StringUtils.isNotBlank(dto.getCountry().getName())) {
                idCountry = countryRepository.findIdByName(dto.getCountry().getName());

                if (idCountry != null) {
                    city.setIdCountry(idCountry);
                }

            }

        }

        return cityRepository.save(city);

    }

}
