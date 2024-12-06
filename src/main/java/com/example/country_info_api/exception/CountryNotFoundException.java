package com.example.country_info_api.exception;

public class CountryNotFoundException extends RuntimeException {
    public CountryNotFoundException(String message){
        super(message);
    }
}
