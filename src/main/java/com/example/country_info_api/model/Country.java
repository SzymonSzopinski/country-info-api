package com.example.country_info_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;


@Entity
@Table(name = "countries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Country {

    @Id
    private String alpha3Code;

    @Embedded
    private Name name;

    @ElementCollection
    @CollectionTable(name = "country_currencies", joinColumns = @JoinColumn(name = "country_code", referencedColumnName = "alpha3Code"))
    @MapKeyColumn(name = "currency_code")
    private Map<String, Currency> currencies;

    @ElementCollection
    @CollectionTable(name = "country_capitals", joinColumns = @JoinColumn(name = "country_code", referencedColumnName = "alpha3Code"))
    private List<String> capital;

    private String region;

    private String subregion;

    @ElementCollection
    @CollectionTable(name = "country_languages", joinColumns = @JoinColumn(name = "country_code", referencedColumnName = "alpha3Code"))
    @MapKeyColumn(name = "language_code")
    private Map<String, String> languages;

    private long population;

    @ElementCollection
    @CollectionTable(name = "country_borders", joinColumns = @JoinColumn(name = "country_code", referencedColumnName = "alpha3Code"))
    private List<String> borders;

    @ElementCollection
    @CollectionTable(name = "country_timezones", joinColumns = @JoinColumn(name = "country_code", referencedColumnName = "alpha3Code"))
    private List<String> timezones;
}
