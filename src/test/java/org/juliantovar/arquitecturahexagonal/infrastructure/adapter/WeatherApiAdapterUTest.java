package org.juliantovar.arquitecturahexagonal.infrastructure.adapter;

import io.smallrye.mutiny.Uni;
import org.juliantovar.arquitecturahexagonal.domain.model.Coordinates;
import org.juliantovar.arquitecturahexagonal.domain.model.Weather;
import org.juliantovar.arquitecturahexagonal.infrastructure.client.WeatherApiClient;
import org.juliantovar.arquitecturahexagonal.infrastructure.config.WeatherApiConfiguration;
import org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.weatherapi.WeatherApiResponseDto;
import org.juliantovar.arquitecturahexagonal.infrastructure.mapper.WeatherApiMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for Weather API Adapter {@link WeatherApiAdapter}
 *
 * @author Julian Tovar
 * @since 10/07/2026
 */
@ExtendWith(MockitoExtension.class)
public class WeatherApiAdapterUTest {

    @Mock
    WeatherApiClient client;

    @Mock
    WeatherApiMapper mapper;

    @Mock
    WeatherApiConfiguration configuration;

    @Mock
    WeatherApiConfiguration.Client clientConfiguration;

    WeatherApiAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new WeatherApiAdapter(
                client,
                mapper,
                configuration
        );
    }

    @Test
    void getCurrentWeatherOk() {
        var latitude = 25.6866;
        var longitude = -100.3161;
        var query = "25.6866,-100.3161";

        var apiResponse =
                org.mockito.Mockito.mock(WeatherApiResponseDto.class);

        var expectedWeather = new Weather(
                new Coordinates(latitude, longitude),
                "America/Monterrey",
                31.5,
                34.1,
                1012,
                55,
                15.2,
                "Soleado"
        );

        when(configuration.apiKey())
                .thenReturn("test-api-key");

        when(configuration.client())
                .thenReturn(clientConfiguration);

        when(clientConfiguration.language())
                .thenReturn("es");

        when(client.getCurrentWeather(
                "test-api-key",
                query,
                "es"
        )).thenReturn(Uni.createFrom().item(apiResponse));

        when(mapper.toDomain(apiResponse))
                .thenReturn(expectedWeather);

        Weather result = adapter
                .getCurrentWeather(latitude, longitude)
                .await()
                .atMost(Duration.ofSeconds(1));

        assertSame(expectedWeather, result);

        verify(client).getCurrentWeather(
                "test-api-key",
                query,
                "es"
        );

        verify(mapper).toDomain(apiResponse);
    }
}
