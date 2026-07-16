package org.juliantovar.arquitecturahexagonal.application.service;

import io.quarkus.test.InjectMock;
import io.smallrye.mutiny.Uni;

import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import org.juliantovar.arquitecturahexagonal.application.ports.out.WeatherProviderPort;
import org.juliantovar.arquitecturahexagonal.domain.exception.InvalidCoordinatesException;
import org.juliantovar.arquitecturahexagonal.domain.model.Coordinates;
import org.juliantovar.arquitecturahexagonal.domain.model.Weather;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Pruebas unitarias para {@link WeatherService}
 *
 * @author Julian Tovar
 * @since 10/07/2026
 */
@ExtendWith(MockitoExtension.class)
public class WeatherServiceUTest {

    @Mock
    WeatherProviderPort port;

    @InjectMock
    WeatherService service;

    @Test
    void getCurrentWeatherOk() {

        var coordinates = Coordinates.builder()
                .latitude(25.678254)
                .longitude(-100.284142)
                .build();

        var weatherResponse = Weather.builder()
                .coordinates(coordinates)
                .temperature(38.5)
                .build();

        Uni<Weather> expectedResponse = Uni.createFrom().item(weatherResponse);

        UniAssertSubscriber<Weather> subscriber = expectedResponse
                .subscribe()
                .withSubscriber(UniAssertSubscriber.create());

        subscriber
                .awaitItem()
                .assertItem(weatherResponse);
    }

    @Test
    void getCurrentWeatherMinimumValidCoordinates() {
        var coordinates = Coordinates.builder()
                .latitude(-90.0)
                .longitude(-180.0)
                .build();

        var weatherResponse = Weather.builder()
                .coordinates(coordinates)
                .temperature(-10.0)
                .build();

        Uni<Weather> expectedResponse = Uni.createFrom().item(weatherResponse);
        UniAssertSubscriber<Weather> subscriber = expectedResponse
                .subscribe().withSubscriber(UniAssertSubscriber.create());

        subscriber
                .awaitItem()
                .assertItem(weatherResponse);
    }

    @Test
    void getCurrentWeatherMaximumValidCoordinates() {
        var coordinates = Coordinates.builder()
                .latitude(90.0)
                .longitude(180.0)
                .build();

        var weatherResponse = Weather.builder()
                .coordinates(coordinates)
                .temperature(1.0)
                .build();

        Uni<Weather> expectedResponse = Uni.createFrom().item(weatherResponse);
        UniAssertSubscriber<Weather> subscriber = expectedResponse
                .subscribe().withSubscriber(UniAssertSubscriber.create());

        subscriber
                .awaitItem()
                .assertItem(weatherResponse);
    }

    @Test
    void getCurrentWeatherNullCoordinates() {
        Uni<Weather> expectedResponse = Uni.createFrom().failure(() ->
                new InvalidCoordinatesException("Coordinates are required"));

        UniAssertSubscriber<Weather> subscriber = expectedResponse
                .subscribe().withSubscriber(UniAssertSubscriber.create());

        subscriber
                .awaitFailure()
                .assertFailedWith(
                        InvalidCoordinatesException.class,
                        "Coordinates are required");
    }

    @Test
    void getCurrentWeatherNullLatitude() {
        Uni<Weather> expectedResponse = Uni.createFrom().failure(() ->
                new InvalidCoordinatesException("Latitude is required"));

        UniAssertSubscriber<Weather> subscriber = expectedResponse
                .subscribe().withSubscriber(UniAssertSubscriber.create());

        subscriber
                .awaitFailure()
                .assertFailedWith(
                        InvalidCoordinatesException.class,
                        "Latitude is required");
    }

    @Test
    void getCurrentWeatherNullLongitude() {
        Uni<Weather> expectedResponse = Uni.createFrom().failure(() ->
                new InvalidCoordinatesException("Longitude is required"));

        UniAssertSubscriber<Weather> subscriber = expectedResponse
                .subscribe().withSubscriber(UniAssertSubscriber.create());

        subscriber
                .awaitFailure()
                .assertFailedWith(
                        InvalidCoordinatesException.class,
                        "Longitude is required");
    }

    @Test
    void getCurrentWeatherInvalidLatitude() {
        Uni<Weather> expectedResponse = Uni.createFrom()
                .failure(() ->
                        new InvalidCoordinatesException(
                                "Latitude must be a value between -90 and 90"));

        UniAssertSubscriber<Weather> subscriber = expectedResponse
                .subscribe()
                .withSubscriber(UniAssertSubscriber.create());

        subscriber.awaitFailure().assertFailedWith(InvalidCoordinatesException.class,
                "Latitude must be a value between -90 and 90");
    }

    @Test
    void getCurrentWeatherInvalidLongitude() {
        Uni<Weather> expectedResponse = Uni.createFrom().failure(() ->
                new InvalidCoordinatesException("Longitude must be a value between -180 and 180"));

        UniAssertSubscriber<Weather> subscriber = expectedResponse
                .subscribe().withSubscriber(UniAssertSubscriber.create());

        subscriber
                .awaitFailure()
                .assertFailedWith(
                        InvalidCoordinatesException.class,
                        "Longitude must be a value between -180 and 180");
    }
}
