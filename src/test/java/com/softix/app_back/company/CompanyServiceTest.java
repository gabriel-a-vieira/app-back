package com.softix.app_back.company;

import com.softix.app_back.city.City;
import com.softix.app_back.city.CityRepository;
import com.softix.app_back.company.favorite.CompanyFavoriteService;
import com.softix.app_back.shared.exception.BusinessException;
import com.softix.app_back.state.State;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Covers how CompanyService resolves the company's city: the admin form now
 * picks UF and then a city from the lookup (sending its id), while older
 * clients may still send only the city name + UF. Both must keep resolving to
 * the same City row, and the detail response must echo back the city id so
 * the edit form can preselect it.
 */
@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private CompanyFavoriteService companyFavoriteService;

    @InjectMocks
    private CompanyService companyService;

    private static final String CITY_ID = "city-1";

    private City buildCity() {

        State state = new State();
        state.setAbbreviation("SP");

        City city = new City();
        city.setId(CITY_ID);
        city.setName("Campinas");
        city.setState(state);

        return city;

    }

    private CompanySaveRequest buildRequest() {

        CompanySaveRequest request = new CompanySaveRequest();
        request.setLegalName("Empresa Teste LTDA");
        request.setTradeName("Empresa Teste");
        request.setCnpj("12345678000190");
        request.setType(CompanyType.values()[0]);
        request.setStreet("Rua A");
        request.setState("SP");

        return request;

    }

    @Test
    void save_resolvesCityByIdWhenCityIdIsSent() {

        CompanySaveRequest request = buildRequest();
        request.setCityId(CITY_ID);
        request.setCity("Campinas");

        when(companyRepository.existsByCnpj(anyString())).thenReturn(false);
        when(cityRepository.findById(CITY_ID)).thenReturn(Optional.of(buildCity()));

        CompanyAdminDetailResponse response = companyService.save(request);

        ArgumentCaptor<Company> captor = ArgumentCaptor.forClass(Company.class);
        verify(companyRepository).save(captor.capture());

        assertThat(captor.getValue().getAddress().getCity().getId()).isEqualTo(CITY_ID);
        assertThat(response.cityId()).isEqualTo(CITY_ID);
        assertThat(response.city()).isEqualTo("Campinas");
        assertThat(response.state()).isEqualTo("SP");

        verify(cityRepository, never()).findByNameAndStateAbbreviation(any(), any());
    }

    @Test
    void save_fallsBackToCityNameAndStateWhenCityIdIsMissing() {

        CompanySaveRequest request = buildRequest();
        request.setCity("Campinas");

        when(companyRepository.existsByCnpj(anyString())).thenReturn(false);
        when(cityRepository.findByNameAndStateAbbreviation("Campinas", "SP")).thenReturn(buildCity());

        CompanyAdminDetailResponse response = companyService.save(request);

        assertThat(response.cityId()).isEqualTo(CITY_ID);
        verify(cityRepository, never()).findById(any());
    }

    @Test
    void save_throwsWhenCityIdDoesNotExist() {

        CompanySaveRequest request = buildRequest();
        request.setCityId("missing");

        when(companyRepository.existsByCnpj(anyString())).thenReturn(false);
        when(cityRepository.findById("missing")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> companyService.save(request))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Cidade nao encontrada");

        verify(companyRepository, never()).save(any());
    }

}
