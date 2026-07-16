package org.juliantovar.arquitecturahexagonal.infrastructure.rest;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.juliantovar.arquitecturahexagonal.infrastructure.resource.WeatherProviderWireMockResource;
import org.juliantovar.arquitecturahexagonal.shared.ErrorMessages;
import org.junit.jupiter.api.Test;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * Pruebas de integración para el recurso de clima {@link WeatherResource}
 *
 * @author Julian Tovar
 * @since 13/07/2026
 */
@QuarkusTest
@QuarkusTestResource(WeatherProviderWireMockResource.class)
public class WeatherResourceITest {

    @Test
    void getCurrentWeatherFromWeatherApiOk() throws IOException, JSONException {
        String actualResponse = given()
                .queryParam("lat", 25.6866)
                .queryParam("lon", -100.3161)
                .when()
                .get("/v1/weather/current")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract()
                .asString();

        JSONAssert.assertEquals(
                readExpectedJson("expected/current-weather-ok-response.json"),
                actualResponse,
                JSONCompareMode.STRICT);

        WeatherProviderWireMockResource.verifyCurrentWeatherRequest("25.6866,-100.3161");
    }

    @Test
    void getCurrentWeatherFromWeatherApiWithInvalidApiKey() {
        given()
                .queryParam("lat", 1.0)
                .queryParam("lon", 1.0)
                .when()
                .get("/v1/weather/current")
                .then()
                .statusCode(401)
                .contentType("application/json")
                .body("error", equalTo("INVALID_API_KEY"))
                .body("message", equalTo(ErrorMessages.INVALID_API_KEY));
    }

    @Test
    void getCurrentWeatherFromWeatherApiNotFound() {
        given()
                .queryParam("lat", 2.0)
                .queryParam("lon", 2.0)
                .when()
                .get("/v1/weather/current")
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
                .get("/v1/weather/current")
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
                .get("/v1/weather/current")
                .then()
                .statusCode(503)
                .contentType("application/json")
                .body("error", equalTo("WEATHER_SERVICE_UNAVAILABLE"))
                .body("message", equalTo("Weather service unavailable"));
    }

    private String readExpectedJson(String resourcePath) throws IOException {
        try (var inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
