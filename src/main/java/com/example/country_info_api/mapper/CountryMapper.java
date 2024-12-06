package com.example.country_info_api.mapper;

import com.example.country_info_api.dto.CountryResponseDto;
import com.example.country_info_api.dto.CurrencyDto;
import com.example.country_info_api.dto.NameDto;
import com.example.country_info_api.dto.RestCountryResponseDto;
import com.example.country_info_api.model.Country;
import com.example.country_info_api.model.Currency;
import com.example.country_info_api.model.Name;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CountryMapper {

    public Country mapToEntity(RestCountryResponseDto response) {
        Country country = new Country();

        country.setAlpha3Code(response.getCca3());
        country.setName(mapToName(response.getName()));
        country.setRegion(response.getRegion());
        country.setSubregion(response.getSubregion());
        country.setPopulation(response.getPopulation());
        country.setCapital(response.getCapital());
        country.setBorders(response.getBorders());
        country.setTimezones(response.getTimezones());
        country.setCurrencies(mapToCurrencies(response.getCurrencies()));
        country.setLanguages(response.getLanguages());

        return country;
    }

    public CountryResponseDto mapToResponseDto(Country country) {

        CountryResponseDto responseDto = new CountryResponseDto();

        responseDto.setName(mapToNameDto(country.getName()));
        responseDto.setRegion(country.getRegion());
        responseDto.setSubregion(country.getSubregion());
        responseDto.setPopulation(country.getPopulation());
        responseDto.setCapital(country.getCapital());
        responseDto.setBorders(country.getBorders());
        responseDto.setTimezones(country.getTimezones());
        responseDto.setCurrencies(mapToCurrencyDto(country.getCurrencies()));
        responseDto.setLanguages(country.getLanguages());

        return responseDto;
    }


    private Name mapToName(NameDto nameDto) {
        return new Name(nameDto.getCommon(), nameDto.getOfficial());
    }

    private NameDto mapToNameDto(Name name) {
        NameDto nameDto = new NameDto();
        nameDto.setCommon(name.getCommon());
        nameDto.setOfficial(name.getOfficial());
        return nameDto;
    }

    private Map<String, CurrencyDto> mapToCurrencyDto(Map<String, Currency> currencies) {
        if (currencies == null) {
            return new HashMap<>();
        }

        Map<String, CurrencyDto> currencyDtoMap = new HashMap<>();
        currencies.forEach((currencyCode, currency) -> {
            CurrencyDto currencyDto = new CurrencyDto();
            currencyDto.setName(currency.getName());
            currencyDto.setSymbol(currency.getSymbol());
            currencyDtoMap.put(currencyCode, currencyDto);
        });
        return currencyDtoMap;
    }

    private Map<String, Currency> mapToCurrencies(Map<String, CurrencyDto> dtoCurrencies) {
        if (dtoCurrencies == null) {
            return new HashMap<>();
        }

        Map<String, Currency> currencies = new HashMap<>();
        dtoCurrencies.forEach((currencyCode, currencyDto) -> {
            Currency currency = new Currency();
            currency.setName(currencyDto.getName());
            currency.setSymbol(currencyDto.getSymbol());
            currencies.put(currencyCode, currency);
        });
        return currencies;
    }


}
