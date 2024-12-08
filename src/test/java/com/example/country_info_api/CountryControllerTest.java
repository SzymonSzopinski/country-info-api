package com.example.country_info_api;

import com.example.country_info_api.controller.CountryController;
import com.example.country_info_api.dto.CountryResponseDto;
import com.example.country_info_api.dto.CurrencyDto;
import com.example.country_info_api.dto.NameDto;
import com.example.country_info_api.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CountryControllerTest {

    @Mock
    private CountryService countryService;

    @InjectMocks
    private CountryController countryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnCountryResponseForValidAlpha3Code() {
        String alpha3Code = "POL";

        CountryResponseDto responseDto = getCountryResponseDto();

        when(countryService.getCountryByAlpha3Code(alpha3Code)).thenReturn(responseDto);

        ResponseEntity<CountryResponseDto> response = countryController.getCountry(alpha3Code);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Poland", response.getBody().getName().getCommon());
        assertEquals("Republic of Poland", response.getBody().getName().getOfficial());
        assertEquals("Polish Zloty", response.getBody().getCurrencies().get("PLN").getName());
        assertEquals("zł", response.getBody().getCurrencies().get("PLN").getSymbol());
        assertEquals(List.of("Warsaw"), response.getBody().getCapital());
        assertEquals("Europe", response.getBody().getRegion());
        assertEquals("Eastern Europe", response.getBody().getSubregion());
        assertEquals(Map.of("pol", "Polish"), response.getBody().getLanguages());
        assertEquals(38000000, response.getBody().getPopulation());
        assertEquals(List.of("DEU", "CZE", "SVK", "UKR", "BLR", "LTU", "RUS"), response.getBody().getBorders());
        assertEquals(List.of("UTC+01:00"), response.getBody().getTimezones());

        verify(countryService).getCountryByAlpha3Code(alpha3Code);
    }



    @Test
    void shouldThrowIllegalArgumentExceptionForRootEndpoint() {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> countryController.handleRootRequest()
        );

        assertEquals("Country code is missing. Please provide a valid alpha3Code.", exception.getMessage());

    }

    private static CountryResponseDto getCountryResponseDto() {
        CountryResponseDto responseDto = new CountryResponseDto();
        responseDto.setName(new NameDto("Poland", "Republic of Poland"));
        responseDto.setCurrencies(Map.of("PLN", new CurrencyDto("Polish Zloty", "zł")));
        responseDto.setCapital(List.of("Warsaw"));
        responseDto.setRegion("Europe");
        responseDto.setSubregion("Eastern Europe");
        responseDto.setLanguages(Map.of("pol", "Polish"));
        responseDto.setPopulation(38000000);
        responseDto.setBorders(List.of("DEU", "CZE", "SVK", "UKR", "BLR", "LTU", "RUS"));
        responseDto.setTimezones(List.of("UTC+01:00"));
        return responseDto;
    }



}
