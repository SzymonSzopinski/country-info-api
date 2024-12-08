package com.example.country_info_api;


import com.example.country_info_api.exception.CountryNotFoundException;
import com.example.country_info_api.exception.ErrorResponse;
import com.example.country_info_api.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {


     private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

     @Test
     void shouldHandleIllegalArgumentException() {
         IllegalArgumentException exception = new IllegalArgumentException("Invalid argument");

         ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleIllegalArgumentException(exception);

         assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
         assertEquals("Invalid argument", response.getBody().getMessage());
         assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
         assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
     }


    @Test
    void shouldHandleCountryNotFoundException() {
        CountryNotFoundException exception = new CountryNotFoundException("Country not found");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleCountryNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Country not found", response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
    }


    @Test
    void shouldHandleGeneralException() {
        Exception exception = new Exception("Unexpected error");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGeneralException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred. Please try again later.", response.getBody().getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatus());
    }



}
