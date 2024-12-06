package com.example.country_info_api.controller;

import com.example.country_info_api.dto.CountryResponseDto;
import com.example.country_info_api.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }


    @GetMapping("/{alpha3Code}")
    public ResponseEntity<CountryResponseDto> getCountry(@PathVariable String alpha3Code) {
        CountryResponseDto countryResponse = countryService.getCountryByAlpha3Code(alpha3Code);
        return ResponseEntity.ok(countryResponse);
    }

}
