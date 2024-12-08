package com.example.country_info_api.client;

import com.example.country_info_api.dto.RestCountryResponseDto;
import com.example.country_info_api.exception.CountryNotFoundException;
import com.example.country_info_api.exception.NoContentException;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.naming.ServiceUnavailableException;

@Component
public class CountryApiClient {

    private final WebClient webClient;

    public CountryApiClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public RestCountryResponseDto fetchCountryFromApi(String code) {
        RestCountryResponseDto[] responseArray = webClient.get()
                .uri("/v3.1/alpha/{code}", code)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new CountryNotFoundException("Country not found for code: " + code))
                )
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new ServiceUnavailableException("Server error occurred while fetching country data: " + code))
                )
                .bodyToMono(RestCountryResponseDto[].class)
                .block();

        if (responseArray == null || responseArray.length == 0) {
            throw new NoContentException("No content found for country code: " + code);
        }

        return responseArray[0];
    }
}
