package com.example.country_info_api;

import com.example.country_info_api.dto.CountryResponseDto;
import com.example.country_info_api.dto.CurrencyDto;
import com.example.country_info_api.dto.NameDto;
import com.example.country_info_api.dto.RestCountryResponseDto;
import com.example.country_info_api.mapper.CountryMapper;
import com.example.country_info_api.model.Country;
import com.example.country_info_api.model.Currency;
import com.example.country_info_api.model.Name;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CountryMapperTest {

    private final CountryMapper countryMapper = new CountryMapper();

    @Test
    public void shouldMapRestCountryResponseDtoToEntity() {
        RestCountryResponseDto restCountryResponse = createRestCountryResponse();
        Country country = countryMapper.mapToEntity(restCountryResponse);

        assertEquals("BRA", country.getAlpha3Code());
        assertEquals("Brazil", country.getName().getCommon());
        assertEquals("República Federativa do Brasil", country.getName().getOfficial());
        assertEquals("Americas", country.getRegion());
        assertEquals("South America", country.getSubregion());
        assertEquals(211000000, country.getPopulation());
        assertEquals(List.of("Brasília"), country.getCapital());
        assertEquals(List.of("ARG", "COL", "PER"), country.getBorders());
        assertEquals(Map.of("por", "Portuguese"), country.getLanguages());
        assertEquals(1, country.getCurrencies().size());
        assertEquals("Brazilian Real", country.getCurrencies().get("BRL").getName());
        assertEquals("R$", country.getCurrencies().get("BRL").getSymbol());
    }

    @Test
    public void shouldMapCountryEntityToResponseDto() {

        Country country = createCountryEntity();
        CountryResponseDto responseDto = countryMapper.mapToResponseDto(country);

        assertEquals("Brazil", responseDto.getName().getCommon());
        assertEquals("República Federativa do Brasil", responseDto.getName().getOfficial());
        assertEquals("Americas", responseDto.getRegion());
        assertEquals("South America", responseDto.getSubregion());
        assertEquals(211000000, responseDto.getPopulation());
        assertEquals(List.of("Brasília"), responseDto.getCapital());
        assertEquals(List.of("ARG", "COL", "PER"), responseDto.getBorders());
        assertEquals(Map.of("por", "Portuguese"), responseDto.getLanguages());
        assertEquals(1, responseDto.getCurrencies().size());
        assertEquals("Brazilian Real", responseDto.getCurrencies().get("BRL").getName());
        assertEquals("R$", responseDto.getCurrencies().get("BRL").getSymbol());
    }

    private RestCountryResponseDto createRestCountryResponse() {
        RestCountryResponseDto response = new RestCountryResponseDto();
        response.setCca3("BRA");
        response.setName(new NameDto("Brazil", "República Federativa do Brasil"));
        response.setRegion("Americas");
        response.setSubregion("South America");
        response.setPopulation(211000000);
        response.setCapital(List.of("Brasília"));
        response.setBorders(List.of("ARG", "COL", "PER"));
        response.setLanguages(Map.of("por", "Portuguese"));
        response.setCurrencies(Map.of("BRL", new CurrencyDto("Brazilian Real", "R$")));
        return response;
    }

    private Country createCountryEntity() {
        Country country = new Country();
        country.setAlpha3Code("BRA");
        country.setName(new Name("Brazil", "República Federativa do Brasil"));
        country.setRegion("Americas");
        country.setSubregion("South America");
        country.setPopulation(211000000);
        country.setCapital(List.of("Brasília"));
        country.setBorders(List.of("ARG", "COL", "PER"));
        country.setLanguages(Map.of("por", "Portuguese"));

        Map<String, Currency> currencies = new HashMap<>();
        Currency currency = new Currency();
        currency.setName("Brazilian Real");
        currency.setSymbol("R$");
        currencies.put("BRL", currency);
        country.setCurrencies(currencies);

        return country;
    }
}
