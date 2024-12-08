package com.example.country_info_api;

import com.example.country_info_api.client.CountryApiClient;
import com.example.country_info_api.dto.CountryResponseDto;
import com.example.country_info_api.dto.RestCountryResponseDto;
import com.example.country_info_api.exception.CountryNotFoundException;
import com.example.country_info_api.mapper.CountryMapper;
import com.example.country_info_api.model.Country;
import com.example.country_info_api.repository.CountryRepository;
import com.example.country_info_api.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CountryServiceTest {

    @Mock
     private CountryRepository countryRepository;

    @Mock
     private CountryApiClient countryApiClient;

    @Mock
     private CountryMapper countryMapper;

    @InjectMocks
    private CountryService countryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnCountryResponseDtoWhenCountryExistsInRepository() {

        String alpha3Code = "POL";

        Country country = new Country();
        country.setAlpha3Code(alpha3Code);

        CountryResponseDto responseDto = mock(CountryResponseDto.class);

        when(countryRepository.findById(alpha3Code)).thenReturn(Optional.of(country));
        when(countryMapper.mapToResponseDto(country)).thenReturn(responseDto);

        CountryResponseDto result = countryService.getCountryByAlpha3Code(alpha3Code);

        assertEquals(responseDto, result);
        verify(countryRepository).findById(alpha3Code);
        verify(countryMapper).mapToResponseDto(country);
        verifyNoInteractions(countryApiClient);
    }

    @Test
    void shouldFetchAndSaveCountryFromApiWhenNotInRepository() {
        String alpha3Code = "POL";

        RestCountryResponseDto apiResponse = mock(RestCountryResponseDto.class);
        Country country = mock(Country.class);
        CountryResponseDto responseDto = mock(CountryResponseDto.class);

        when(countryRepository.findById(alpha3Code)).thenReturn(Optional.empty());
        when(countryApiClient.fetchCountryFromApi(alpha3Code)).thenReturn(apiResponse);
        when(countryMapper.mapToEntity(apiResponse)).thenReturn(country);
        when(countryRepository.save(country)).thenReturn(country);
        when(countryMapper.mapToResponseDto(country)).thenReturn(responseDto);

        CountryResponseDto result = countryService.getCountryByAlpha3Code(alpha3Code);

        assertEquals(responseDto, result);
        verify(countryRepository).findById(alpha3Code);
        verify(countryApiClient).fetchCountryFromApi(alpha3Code);
        verify(countryRepository).save(country);
        verify(countryMapper).mapToResponseDto(country);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionForInvalidAlpha3Code() {
        String invalidAlpha3CodeShort = "XY";
        assertThrows(IllegalArgumentException.class, () -> countryService.getCountryByAlpha3Code(invalidAlpha3CodeShort));

        assertThrows(IllegalArgumentException.class, () -> countryService.getCountryByAlpha3Code(null));

        String invalidAlpha3CodeWithNumbers = "12A";
        assertThrows(IllegalArgumentException.class, () -> countryService.getCountryByAlpha3Code(invalidAlpha3CodeWithNumbers));
    }

    @Test
    void shouldThrowCountryNotFoundExceptionWhenCountryNotFoundInApi() {
        String alpha3Code = "XYZ";

        when(countryRepository.findById(alpha3Code)).thenReturn(Optional.empty());
        when(countryApiClient.fetchCountryFromApi(alpha3Code)).thenReturn(null);

        assertThrows(CountryNotFoundException.class, () -> countryService.getCountryByAlpha3Code(alpha3Code));

        verify(countryRepository).findById(alpha3Code);
        verify(countryApiClient).fetchCountryFromApi(alpha3Code);
        verifyNoMoreInteractions(countryRepository, countryMapper);
    }

    @Test
    void shouldCallMethodsInCorrectOrder() {
        String alpha3Code = "POL";

        RestCountryResponseDto apiResponse = mock(RestCountryResponseDto.class);
        Country mappedCountry = new Country();
        CountryResponseDto responseDto = mock(CountryResponseDto.class);

        when(countryRepository.findById(alpha3Code)).thenReturn(Optional.empty());
        when(countryApiClient.fetchCountryFromApi(alpha3Code)).thenReturn(apiResponse);
        when(countryMapper.mapToEntity(apiResponse)).thenReturn(mappedCountry);
        when(countryRepository.save(mappedCountry)).thenReturn(mappedCountry);
        when(countryMapper.mapToResponseDto(mappedCountry)).thenReturn(responseDto);

        countryService.getCountryByAlpha3Code(alpha3Code);

        InOrder inOrder = inOrder(countryRepository, countryApiClient, countryMapper);
        inOrder.verify(countryRepository).findById(alpha3Code);
        inOrder.verify(countryApiClient).fetchCountryFromApi(alpha3Code);
        inOrder.verify(countryMapper).mapToEntity(apiResponse);
        inOrder.verify(countryRepository).save(mappedCountry);
        inOrder.verify(countryMapper).mapToResponseDto(mappedCountry);
    }

    }

