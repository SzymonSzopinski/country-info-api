package com.example.country_info_api.client;

import com.example.country_info_api.dto.RestCountryResponseDto;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CountryApiClient {

    private final WebClient webClient;

    public CountryApiClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://restcountries.com").build();
    }

    public RestCountryResponseDto fetchCountryFromApi(String code) {
        return webClient.get()
                .uri("/v3.1/alpha/{code}", code)
                .retrieve()
                .bodyToMono(RestCountryResponseDto[].class)
                .map(responseArray -> {
                    if (responseArray.length == 0) {
                        throw new RuntimeException("No country found for code: " + code);
                    }
                    return responseArray[0];
                })
                .block();
    }
}
