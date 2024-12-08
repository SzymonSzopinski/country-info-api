package com.example.country_info_api.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    WebClient webClient(WebClient.Builder builder) {
        return builder.baseUrl("https://restcountries.com").build();
    }
}
