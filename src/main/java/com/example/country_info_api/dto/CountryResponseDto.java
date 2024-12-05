package com.example.country_info_api.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CountryResponseDto {

    private NameDto name;
    private Map<String, CurrencyDto> currencies;
    private List<String> capital;
    private String region;
    private String subregion;
    private Map<String, String> languages;
    private long population;
    private List<String> borders;
    private List<String> timezones;
}
