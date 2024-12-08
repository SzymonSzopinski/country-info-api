
# Country Info API

A **Spring Boot** application that exposes a RESTful API to fetch and store information about a selected country using its ISO 3166-1 alpha-3 code.

---

## Table of Contents

1. [Overview](#overview)
2. [Features](#features)
3. [Technologies](#technologies)
4. [Setup and Installation](#setup-and-installation)
5. [Usage](#usage)
6. [Testing](#testing)
7. [API Endpoints](#api-endpoints)

---

## 1. Overview

This project is a **Spring Boot application** that fetches country data from the free [REST Countries API](https://restcountries.com) and stores it in an **H2 in-memory database**. It provides a RESTful API to retrieve detailed information about any country using its ISO alpha-3 code.

---

## 2. Features

- Fetch detailed information about a country:
  - Name (common and official)
  - Currencies
  - Capital
  - Region and subregion
  - Languages
  - Population
  - Borders
  - Time zones
- Store country data in an **H2 database** for faster access.
- Automatic validation of ISO alpha-3 codes.
- Integration with external APIs ([REST Countries API](https://restcountries.com)).
- Robust exception handling with meaningful error messages.

---

## 3. Technologies

- **Java 21**
- **Spring Boot**
  - Spring Web
  - Spring Data JPA
  - Spring Webflux
- **H2 Database**
- **Lombok**
- **WebClient**
- **JaCoCo** for test coverage
- **Mockito** for unit testing

---

## 4. Setup and Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/SzymonSzopinski/country-info-api.git
   cd country-info-api
   ```

2. **Build the project**:
   Ensure you have **Maven** and **Java 21** installed on your system.
   ```bash
   ./mvnw clean install
   ```

3. **Run the application**:
   Start the application using Maven:
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Access the API**:
   The application will be running at:
   ```
   http://localhost:8080
   ```

   Example request:
   ```
   GET /api/countries/{alpha3Code}
   ```

---

## 5. Usage

Once the application is running, you can interact with the API using tools like **Postman**, **cURL**, or a web browser.

Example usage with `cURL`:
```bash
curl -X GET http://localhost:8080/api/countries/{alpha3Code}
```

Replace `{alpha3Code}` with a valid ISO 3166-1 alpha-3 code (e.g., "POL" for Poland).

---

## 6. Testing

This project includes unit and integration tests to ensure functionality. To run the tests and generate a coverage report:

1. **Run Tests**:
   ```bash
   ./mvnw test
   ```

2. **Generate Test Coverage Report (using JaCoCo)**:
   ```bash
   ./mvnw verify
   ```

   The report will be available in:
   ```
   target/site/jacoco/index.html
   ```

   Open this file in your browser to view the test coverage details.

---

## 7. API Endpoints

### **GET /api/countries/{alpha3Code}**
Fetches detailed information about a country using its ISO alpha-3 code.

**Request Example**:
```bash
curl -X GET http://localhost:8080/api/countries/POL
```

**Response Example**:
```json
{
  "name": {
    "common": "Poland",
    "official": "Republic of Poland"
  },
  "currencies": {
    "PLN": {
      "name": "Polish Zloty",
      "symbol": "zł"
    }
  },
  "capital": ["Warsaw"],
  "region": "Europe",
  "subregion": "Eastern Europe",
  "languages": {
    "pol": "Polish"
  },
  "population": 38000000,
  "borders": ["DEU", "CZE", "SVK", "UKR", "BLR", "LTU", "RUS"],
  "timezones": ["UTC+01:00"]
}
```

### **Error Responses**:
- **400 Bad Request**: Invalid alpha-3 code.
- **404 Not Found**: Country not found.
- **500 Internal Server Error**: Issue with the external API or server.

---
