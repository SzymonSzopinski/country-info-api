package com.example.country_info_api.service;

import com.example.country_info_api.dto.CountryResponseDto;

public interface ICountryService {
    CountryResponseDto getCountryByAlpha3Code(String alpha3Code);

}
