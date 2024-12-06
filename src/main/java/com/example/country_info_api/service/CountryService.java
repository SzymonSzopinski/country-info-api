package com.example.country_info_api.service;

import com.example.country_info_api.client.CountryApiClient;
import com.example.country_info_api.dto.CountryResponseDto;
import com.example.country_info_api.dto.RestCountryResponseDto;
import com.example.country_info_api.mapper.CountryMapper;
import com.example.country_info_api.model.Country;
import com.example.country_info_api.repository.CountryRepository;
import org.springframework.stereotype.Service;

@Service
public class CountryService implements ICountryService{

    private final CountryRepository countryRepository;
    private final CountryApiClient countryApiClient;
    private final CountryMapper countryMapper;

    public CountryService(CountryRepository countryRepository, CountryApiClient countryApiClient, CountryMapper countryMapper) {
        this.countryRepository = countryRepository;
        this.countryApiClient = countryApiClient;
        this.countryMapper = countryMapper;
    }

    @Override
    public CountryResponseDto getCountryByAlpha3Code(String alpha3Code) {

        // do validation for alpha3code before


        Country country = countryRepository.findById(alpha3Code)
                .orElseGet(() -> fetchAndSaveCountryFromApi(alpha3Code));


        return countryMapper.mapToResponseDto(country);
    }

    private Country fetchAndSaveCountryFromApi(String alpha3Code) {
        RestCountryResponseDto apiResponse = countryApiClient.fetchCountryFromApi(alpha3Code);

        if (apiResponse == null) {
            throw new RuntimeException("Country with code " + alpha3Code + " not found in external API");
        }

        Country country = countryMapper.mapToEntity(apiResponse);
        return countryRepository.save(country);
    }
}
