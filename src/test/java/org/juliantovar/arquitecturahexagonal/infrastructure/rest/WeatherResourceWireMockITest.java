package org.juliantovar.arquitecturahexagonal.infrastructure.rest;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.juliantovar.arquitecturahexagonal.infrastructure.resource.WeatherApiWireMockResource;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * Prueba de integración utilizando el adaptador de WeatherAPI y WireMock.
 *
 * @author Julian Tovar
 * @since 13/07/2026
 */
@QuarkusTest
@QuarkusTestResource(WeatherApiWireMockResource.class)
public class WeatherResourceWireMockITest {

    @Test
    void getCurrentWeatherFromWeatherApiOk() {
        given()
                .queryParam("lat", 25.6866)
                .queryParam("lon", -100.3161)
                .when()
                .get("/weather/current")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("latitude", equalTo(25.6866f))
                .body("longitude", equalTo(-100.3161f))
                .body("timezone", equalTo("America/Monterrey"))
                .body("temperature", equalTo(31.5f))
                .body("feelsLike", equalTo(34.1f))
                .body("pressure", equalTo(1012))
                .body("humidity", equalTo(55))
                .body("windSpeed", equalTo(15.2f))
                .body("description", equalTo("Soleado"));

        WeatherApiWireMockResource.verifyCurrentWeatherRequest("25.6866,-100.3161");
    }

    @Test
    void getCurrentWeatherFromWeatherApiWithInvalidApiKey() {
        given()
                .queryParam("lat", 1.0)
                .queryParam("lon", 1.0)
                .when()
                .get("/weather/current")
                .then()
                .statusCode(401)
                .contentType("application/json")
                .body("error", equalTo("INVALID_API_KEY"))
                .body("message", equalTo("Invalid OpenWeather API Key"));
    }

    @Test
    void getCurrentWeatherFromWeatherApiNotFound() {
        given()
                .queryParam("lat", 2.0)
                .queryParam("lon", 2.0)
                .when()
                .get("/weather/current")
                .then()
                .statusCode(404)
                .contentType("application/json")
                .body("error", equalTo("WEATHER_NOT_FOUND"))
                .body("message", equalTo("Weather information not found"));
    }

    @Test
    void getCurrentWeatherFromWeatherApiRateLimit() {
        given()
                .queryParam("lat", 3.0)
                .queryParam("lon", 3.0)
                .when()
                .get("/weather/current")
                .then()
                .statusCode(429)
                .contentType("application/json")
                .body("error", equalTo("WEATHER_RATE_LIMIT_REACHED"))
                .body("message", equalTo("Weather rate limit reached"));
    }

    @Test
    void getCurrentWeatherFromWeatherApiUnavailable() {
        given()
                .queryParam("lat", 4.0)
                .queryParam("lon", 4.0)
                .when()
                .get("/weather/current")
                .then()
                .statusCode(503)
                .contentType("application/json")
                .body("error", equalTo("WEATHER_SERVICE_UNAVAILABLE"))
                .body("message", equalTo("Weather service unavailable"));
    }
}
