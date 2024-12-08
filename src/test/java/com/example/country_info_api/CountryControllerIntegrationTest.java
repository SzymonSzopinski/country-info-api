package com.example.country_info_api;

import com.example.country_info_api.controller.CountryController;
import com.example.country_info_api.dto.CountryResponseDto;
import com.example.country_info_api.dto.CurrencyDto;
import com.example.country_info_api.dto.NameDto;
import com.example.country_info_api.exception.CountryNotFoundException;
import com.example.country_info_api.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@WebMvcTest(CountryController.class)
class CountryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryService countryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnCountryResponseForValidAlpha3Code() throws Exception {

        String alpha3Code = "POL";

        CountryResponseDto countryResponse = new CountryResponseDto();
        countryResponse.setName(new NameDto("Poland", "Republic of Poland"));
        countryResponse.setCurrencies(Map.of("PLN", new CurrencyDto("Polish Zloty", "zł")));
        countryResponse.setCapital(List.of("Warsaw"));
        countryResponse.setRegion("Europe");
        countryResponse.setSubregion("Eastern Europe");
        countryResponse.setLanguages(Map.of("pol", "Polish"));
        countryResponse.setPopulation(38000000);
        countryResponse.setBorders(List.of("DEU", "CZE", "SVK", "UKR", "BLR", "LTU", "RUS"));
        countryResponse.setTimezones(List.of("UTC+01:00"));

        when(countryService.getCountryByAlpha3Code(alpha3Code)).thenReturn(countryResponse);

        mockMvc.perform(get("/api/countries/{alpha3Code}", alpha3Code))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name.common").value("Poland"))
                .andExpect(jsonPath("$.name.official").value("Republic of Poland"))
                .andExpect(jsonPath("$.currencies.PLN.name").value("Polish Zloty"))
                .andExpect(jsonPath("$.currencies.PLN.symbol").value("zł"))
                .andExpect(jsonPath("$.capital[0]").value("Warsaw"))
                .andExpect(jsonPath("$.region").value("Europe"))
                .andExpect(jsonPath("$.subregion").value("Eastern Europe"))
                .andExpect(jsonPath("$.languages.pol").value("Polish"))
                .andExpect(jsonPath("$.population").value(38000000))
                .andExpect(jsonPath("$.borders[0]").value("DEU"))
                .andExpect(jsonPath("$.timezones[0]").value("UTC+01:00"));
    }

    @Test
    void shouldReturnBadRequestForInvalidAlpha3Code() throws Exception {
        String invalidAlpha3Code = "XX";

        when(countryService.getCountryByAlpha3Code(invalidAlpha3Code))
                .thenThrow(new IllegalArgumentException("Invalid country code"));

        mockMvc.perform(get("/api/countries/{alpha3Code}", invalidAlpha3Code))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid country code"));
    }

    @Test
    void shouldReturnNotFoundForNonExistingCountry() throws Exception {
        String alpha3Code = "ZZZ";

        when(countryService.getCountryByAlpha3Code(alpha3Code))
                .thenThrow(new CountryNotFoundException("Country with code " + alpha3Code + " not found"));

        mockMvc.perform(get("/api/countries/{alpha3Code}", alpha3Code))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Country with code ZZZ not found"));
    }


    @Test
    void shouldReturnBadRequestWhenRootEndpointIsAccessed() throws Exception {
        mockMvc.perform(get("/api/countries/"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Country code is missing. Please provide a valid alpha3Code."));
    }


}
