package org.juliantovar.arquitecturahexagonal.infrastructure.rest;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import org.juliantovar.arquitecturahexagonal.domain.exception.client.InvalidApiKeyException;
import org.juliantovar.arquitecturahexagonal.domain.exception.client.WeatherNotFoundException;
import org.juliantovar.arquitecturahexagonal.domain.exception.provider.UnexpectedProviderException;
import org.juliantovar.arquitecturahexagonal.domain.exception.provider.WeatherProviderUnavailableException;
import org.juliantovar.arquitecturahexagonal.domain.exception.provider.WeatherRateLimitException;
import org.juliantovar.arquitecturahexagonal.domain.model.Coordinates;
import org.juliantovar.arquitecturahexagonal.domain.model.Weather;
import org.juliantovar.arquitecturahexagonal.domain.port.WeatherClientPort;
import org.juliantovar.arquitecturahexagonal.infrastructure.qualifiers.WeatherApiProvider;
import org.juliantovar.arquitecturahexagonal.shared.ErrorMessages;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

/**
 * Pruebas de integración para el recurso de clima {@link WeatherResource}
 *
 * @author Julian Tovar
 * @since 10/07/2026
 */
@QuarkusTest
public class WeatherResourceITest {

    @InjectMock
    @WeatherApiProvider
    WeatherClientPort weatherClientPort;

    @Test
    void getCurrentWeatherOk() {
        var latitude = 25.6866;
        var longitude = -100.3161;

        var weather = new Weather(
                new Coordinates(latitude, longitude),
                "America/Monterrey",
                31.5,
                34.1,
                1012,
                55,
                15.2,
                "Soleado"
        );

        when(weatherClientPort.getCurrentWeather(
                latitude,
                longitude
        )).thenReturn(Uni.createFrom().item(weather));

        given()
                .queryParam("lat", latitude)
                .queryParam("lon", longitude)
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
    }

    @Test
    void getCurrentWeatherInvalidCoordinates() {
        given()
                .queryParam("lat", 91.0)
                .queryParam("lon", -100.3161)
                .when()
                .get("/weather/current")
                .then()
                .statusCode(400)
                .contentType("application/json")
                .body(
                        "error",
                        equalTo("INVALID_COORDINATES")
                )
                .body(
                        "path",
                        equalTo("/weather/current")
                );
    }

    @Test
    void getCurrentWeatherMissingLatitude() {
        given()
                .queryParam("lon", -100.3161)
                .when()
                .get("/weather/current")
                .then()
                .statusCode(400)
                .contentType("application/json")
                .body("error", equalTo("INVALID_COORDINATES"))
                .body("message", equalTo(ErrorMessages.REQUIRED_LATITUDE));
    }

    @Test
    void getCurrentWeatherMissingLongitude() {
        given()
                .queryParam("lat", 25.6866)
                .when()
                .get("/weather/current")
                .then()
                .statusCode(400)
                .contentType("application/json")
                .body("error", equalTo("INVALID_COORDINATES"))
                .body("message", equalTo(ErrorMessages.REQUIRED_LONGITUDE));
    }

    @Test
    void getCurrentWeatherInvalidApiKey() {
        var latitude = 25.6866;
        var longitude = -100.3161;

        when(weatherClientPort.getCurrentWeather(
                latitude,
                longitude
        )).thenReturn(
                Uni.createFrom().failure(
                        new InvalidApiKeyException(
                                "Invalid WeatherAPI key"
                        )
                )
        );

        given()
                .queryParam("lat", latitude)
                .queryParam("lon", longitude)
                .when()
                .get("/weather/current")
                .then()
                .statusCode(401)
                .contentType("application/json")
                .body(
                        "error",
                        equalTo("INVALID_API_KEY")
                )
                .body(
                        "message",
                        equalTo("Invalid WeatherAPI key")
                );
    }

    @Test
    void getCurrentWeatherNotFound() {
        var latitude = 0.0;
        var longitude = 0.0;

        when(weatherClientPort.getCurrentWeather(latitude, longitude))
                .thenReturn(Uni.createFrom().failure(
                        new WeatherNotFoundException("Weather information not found")
                ));

        given()
                .queryParam("lat", latitude)
                .queryParam("lon", longitude)
                .when()
                .get("/weather/current")
                .then()
                .statusCode(404)
                .contentType("application/json")
                .body("error", equalTo("WEATHER_NOT_FOUND"))
                .body("message", equalTo("Weather information not found"));
    }

    @Test
    void getCurrentWeatherProviderUnavailable() {
        var latitude = 10.0;
        var longitude = 20.0;

        when(weatherClientPort.getCurrentWeather(latitude, longitude))
                .thenReturn(Uni.createFrom().failure(
                        new WeatherProviderUnavailableException("Weather service unavailable")
                ));

        given()
                .queryParam("lat", latitude)
                .queryParam("lon", longitude)
                .when()
                .get("/weather/current")
                .then()
                .statusCode(503)
                .contentType("application/json")
                .body("error", equalTo("WEATHER_SERVICE_UNAVAILABLE"))
                .body("message", equalTo("Weather service unavailable"));
    }

    @Test
    void getCurrentWeatherRateLimitReached() {
        var latitude = 11.0;
        var longitude = 21.0;

        when(weatherClientPort.getCurrentWeather(latitude, longitude))
                .thenReturn(Uni.createFrom().failure(
                        new WeatherRateLimitException("Weather rate limit reached")
                ));

        given()
                .queryParam("lat", latitude)
                .queryParam("lon", longitude)
                .when()
                .get("/weather/current")
                .then()
                .statusCode(429)
                .contentType("application/json")
                .body("error", equalTo("WEATHER_RATE_LIMIT_REACHED"))
                .body("message", equalTo("Weather rate limit reached"));
    }

    @Test
    void getCurrentWeatherUnexpectedProviderError() {
        var latitude = 12.0;
        var longitude = 22.0;

        when(weatherClientPort.getCurrentWeather(latitude, longitude))
                .thenReturn(Uni.createFrom().failure(
                        new UnexpectedProviderException("Unexpected provider error")
                ));

        given()
                .queryParam("lat", latitude)
                .queryParam("lon", longitude)
                .when()
                .get("/weather/current")
                .then()
                .statusCode(503)
                .contentType("application/json")
                .body("error", equalTo("WEATHER_SERVICE_UNAVAILABLE"))
                .body("message", equalTo("Unexpected provider error"));
    }
}
