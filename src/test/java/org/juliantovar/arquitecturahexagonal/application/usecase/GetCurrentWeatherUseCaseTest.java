package org.juliantovar.arquitecturahexagonal.application.usecase;

import io.smallrye.mutiny.Uni;
import org.juliantovar.arquitecturahexagonal.application.command.GetCurrentWeatherCommand;
import org.juliantovar.arquitecturahexagonal.domain.exception.client.InvalidCoordinatesException;
import org.juliantovar.arquitecturahexagonal.domain.model.Coordinates;
import org.juliantovar.arquitecturahexagonal.domain.model.Weather;
import org.juliantovar.arquitecturahexagonal.domain.port.WeatherClientPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for {@link GetCurrentWeatherUseCase}
 *
 * @author Julian Tovar
 * @since 10/07/2026
 */
@ExtendWith(MockitoExtension.class)
public class GetCurrentWeatherUseCaseTest {

    private static final Double LATITUDE = 25.678254;
    private static final Double LONGITUDE = -100.284142;

    @Mock
    WeatherClientPort weatherClientPort;

    GetCurrentWeatherUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetCurrentWeatherUseCase(weatherClientPort);
    }

    @Test
    void getCurrentWeatherOk() {
        var command = new GetCurrentWeatherCommand(LATITUDE, LONGITUDE);

        var weatherResponse = new Weather(
                new Coordinates(LATITUDE, LONGITUDE),
                "America/Monterrey",
                38.5,
                42.0,
                1050,
                14,
                2.0,
                "Soleado"
        );

        when(weatherClientPort.getCurrentWeather(LATITUDE, LONGITUDE))
                .thenReturn(Uni.createFrom().item(weatherResponse));

        Weather result = useCase.execute(command)
                .await()
                .atMost(Duration.ofSeconds(1));

        assertSame(weatherResponse, result);

        verify(weatherClientPort)
                .getCurrentWeather(LATITUDE, LONGITUDE);


    }

    @Test
    void getCurrentWeatherInvalidLatitude() {
        var command = new GetCurrentWeatherCommand(
                91.0,
                LONGITUDE
        );

        InvalidCoordinatesException exception =
                assertThrows(
                        InvalidCoordinatesException.class,
                        () -> useCase.execute(command)
                );

        assertEquals(
                "La latitud debe ser un valor entre -90 y 90",
                exception.getMessage()
        );

        verify(weatherClientPort, never())
                .getCurrentWeather(
                        org.mockito.ArgumentMatchers.anyDouble(),
                        org.mockito.ArgumentMatchers.anyDouble()
                );
    }

    @Test
    void getCurrentWeatherInvalidLongitude() {
        var command = new GetCurrentWeatherCommand(
                LATITUDE,
                -181.0
        );

        InvalidCoordinatesException exception =
                assertThrows(
                        InvalidCoordinatesException.class,
                        () -> useCase.execute(command)
                );

        assertEquals(
                "La longitud debe ser un valor entre -180 y 180",
                exception.getMessage()
        );

        verify(weatherClientPort, never())
                .getCurrentWeather(
                        org.mockito.ArgumentMatchers.anyDouble(),
                        org.mockito.ArgumentMatchers.anyDouble()
                );
    }
}
