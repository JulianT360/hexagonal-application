package org.juliantovar.arquitecturahexagonal.application.usecase;

import io.smallrye.mutiny.Uni;
import org.juliantovar.arquitecturahexagonal.application.command.GetCurrentWeatherCommand;
import org.juliantovar.arquitecturahexagonal.domain.exception.client.InvalidCoordinatesException;
import org.juliantovar.arquitecturahexagonal.domain.model.Coordinates;
import org.juliantovar.arquitecturahexagonal.domain.model.Weather;
import org.juliantovar.arquitecturahexagonal.domain.port.WeatherClientPort;
import org.juliantovar.arquitecturahexagonal.shared.ErrorMessages;
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
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

/**
 * Pruebas unitarias para {@link GetCurrentWeatherUseCase}
 *
 * @author Julian Tovar
 * @since 10/07/2026
 */
@ExtendWith(MockitoExtension.class)
public class GetCurrentWeatherUseCaseUTest {

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
                ErrorMessages.INVALID_LATITUDE,
                exception.getMessage()
        );

        verify(weatherClientPort, never())
                .getCurrentWeather(
                        org.mockito.ArgumentMatchers.anyDouble(),
                        org.mockito.ArgumentMatchers.anyDouble()
                );
    }

    @Test
    void getCurrentWeatherMinimumValidCoordinates() {
        var command = new GetCurrentWeatherCommand(-90.0, -180.0);
        var weatherResponse = new Weather(
                new Coordinates(-90.0, -180.0),
                "Antarctica",
                -10.0,
                -15.0,
                1000,
                80,
                12.0,
                "Frio"
        );

        when(weatherClientPort.getCurrentWeather(-90.0, -180.0))
                .thenReturn(Uni.createFrom().item(weatherResponse));

        Weather result = useCase.execute(command)
                .await()
                .atMost(Duration.ofSeconds(1));

        assertSame(weatherResponse, result);
        verify(weatherClientPort).getCurrentWeather(-90.0, -180.0);
    }

    @Test
    void getCurrentWeatherMaximumValidCoordinates() {
        var command = new GetCurrentWeatherCommand(90.0, 180.0);
        var weatherResponse = new Weather(
                new Coordinates(90.0, 180.0),
                "UTC",
                1.0,
                0.0,
                1000,
                50,
                2.0,
                "Nublado"
        );

        when(weatherClientPort.getCurrentWeather(90.0, 180.0))
                .thenReturn(Uni.createFrom().item(weatherResponse));

        Weather result = useCase.execute(command)
                .await()
                .atMost(Duration.ofSeconds(1));

        assertSame(weatherResponse, result);
        verify(weatherClientPort).getCurrentWeather(90.0, 180.0);
    }

    @Test
    void getCurrentWeatherNullCommand() {
        InvalidCoordinatesException exception =
                assertThrows(
                        InvalidCoordinatesException.class,
                        () -> useCase.execute(null)
                );

        assertEquals(ErrorMessages.REQUIRED_LATITUDE, exception.getMessage());
        verifyNoInteractions(weatherClientPort);
    }

    @Test
    void getCurrentWeatherNullLatitude() {
        var command = new GetCurrentWeatherCommand(null, LONGITUDE);

        InvalidCoordinatesException exception =
                assertThrows(
                        InvalidCoordinatesException.class,
                        () -> useCase.execute(command)
                );

        assertEquals(ErrorMessages.REQUIRED_LATITUDE, exception.getMessage());
        verifyNoInteractions(weatherClientPort);
    }

    @Test
    void getCurrentWeatherNullLongitude() {
        var command = new GetCurrentWeatherCommand(LATITUDE, null);

        InvalidCoordinatesException exception =
                assertThrows(
                        InvalidCoordinatesException.class,
                        () -> useCase.execute(command)
                );

        assertEquals(ErrorMessages.REQUIRED_LONGITUDE, exception.getMessage());
        verifyNoInteractions(weatherClientPort);
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
                ErrorMessages.INVALID_LONGITUDE,
                exception.getMessage()
        );

        verify(weatherClientPort, never())
                .getCurrentWeather(
                        org.mockito.ArgumentMatchers.anyDouble(),
                        org.mockito.ArgumentMatchers.anyDouble()
                );
    }
}
