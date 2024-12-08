package com.example.country_info_api;


import com.example.country_info_api.client.CountryApiClient;
import com.example.country_info_api.dto.NameDto;
import com.example.country_info_api.dto.RestCountryResponseDto;
import com.example.country_info_api.exception.NoContentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CountryApiClientTest {

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    private CountryApiClient countryApiClient;

    @BeforeEach
    void setUp() {
        countryApiClient = new CountryApiClient(webClient);

    }

    @Test
    void shouldReturnCountryWhenCountryExists() {

        RestCountryResponseDto responseDto = new RestCountryResponseDto();
        responseDto.setCca3("POL");
        responseDto.setName(new NameDto("Poland", "Republic of Poland"));

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/v3.1/alpha/{code}", "POL")).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(RestCountryResponseDto[].class)).thenReturn(Mono.just(new RestCountryResponseDto[]{responseDto}));


        RestCountryResponseDto result = countryApiClient.fetchCountryFromApi("POL");

        assertNotNull(result);
        assertEquals("POL", result.getCca3());
        assertEquals("Poland", result.getName().getCommon());
        assertEquals("Republic of Poland", result.getName().getOfficial());
    }

    @Test
    void shouldThrowNoContentExceptionWhenResponseIsEmpty() {
        String code = "POL";

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/v3.1/alpha/{code}", "POL")).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(RestCountryResponseDto[].class)).thenReturn(Mono.just(new RestCountryResponseDto[0]));


        assertThrows(NoContentException.class, () -> countryApiClient.fetchCountryFromApi(code));
    }


}
