package org.juliantovar.arquitecturahexagonal.infrastructure.adapter.out;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.juliantovar.arquitecturahexagonal.domain.model.Coordinates;
import org.juliantovar.arquitecturahexagonal.domain.model.Weather;
import org.juliantovar.arquitecturahexagonal.infrastructure.adapter.out.mapper.WeatherApiMapper;
import org.juliantovar.arquitecturahexagonal.infrastructure.client.WeatherApiClient;
import org.juliantovar.arquitecturahexagonal.infrastructure.client.dto.WeatherApiResponseDto;
import org.juliantovar.arquitecturahexagonal.infrastructure.config.WeatherApiConfiguration;
import org.juliantovar.arquitecturahexagonal.infrastructure.exception.ExternalServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherApiAdapterUTest {

    @Mock
    @RestClient
    WeatherApiClient client;

    @Mock
    WeatherApiMapper mapper;

    @Mock
    WeatherApiConfiguration configuration;

    @Mock
    WeatherApiConfiguration.Client clientConfiguration;

    @Test
    void getCurrentWeatherOk() {
        var coordinates = Coordinates.builder()
                .latitude(25.6866)
                .longitude(-100.3161)
                .build();

        var dto = WeatherApiResponseDto.builder().build();

        var weather = Weather.builder()
                .coordinates(coordinates)
                .temperature(31.5)
                .build();

        when(configuration.apiKey()).thenReturn("api-key");

        when(configuration.client()).thenReturn(clientConfiguration);

        when(clientConfiguration.language()).thenReturn("es");

        when(client.getCurrentWeather("api-key", "25.6866,-100.3161", "es"))
                .thenReturn(Uni.createFrom().item(dto));

        when(mapper.dtoToDomain(dto)).thenReturn(weather);

        var adapter = new WeatherApiAdapter(client, mapper, configuration);

        adapter.getCurrentWeather(coordinates)
                .subscribe()
                .withSubscriber(UniAssertSubscriber.create())
                .awaitItem()
                .assertItem(weather);

        verify(client).getCurrentWeather("api-key", "25.6866,-100.3161", "es");
        verify(mapper).dtoToDomain(dto);
    }

    @Test
    void getCurrentWeatherUnexpectedFailure() {
        var coordinates = Coordinates.builder()
                .latitude(1.0)
                .longitude(2.0)
                .build();

        var failure = new IllegalStateException("Unexpected client failure");

        when(configuration.apiKey()).thenReturn("api-key");

        when(configuration.client()).thenReturn(clientConfiguration);

        when(clientConfiguration.language()).thenReturn("en");

        when(client.getCurrentWeather("api-key", "1.0,2.0", "en"))
                .thenReturn(Uni.createFrom().failure(failure));

        var adapter = new WeatherApiAdapter(client, mapper, configuration);

        adapter.getCurrentWeather(coordinates)
                .subscribe()
                .withSubscriber(UniAssertSubscriber.create())
                .awaitFailure()
                .assertFailedWith(IllegalStateException.class, "Unexpected client failure");

        verify(client).getCurrentWeather("api-key", "1.0,2.0", "en");
        verifyNoMoreInteractions(mapper);
    }

    @Test
    void getCurrentWeatherExternalServiceFailures() {
        var coordinates = Coordinates.builder()
                .latitude(3.0)
                .longitude(4.0)
                .build();

        var failure = new ExternalServiceException("Provider unavailable");

        when(configuration.apiKey()).thenReturn("api-key");

        when(configuration.client()).thenReturn(clientConfiguration);

        when(clientConfiguration.language()).thenReturn("es");

        when(client.getCurrentWeather("api-key", "3.0,4.0", "es"))
                .thenReturn(Uni.createFrom().failure(failure));

        var adapter = new WeatherApiAdapter(client, mapper, configuration);

        adapter.getCurrentWeather(coordinates)
                .subscribe()
                .withSubscriber(UniAssertSubscriber.create())
                .awaitFailure()
                .assertFailedWith(ExternalServiceException.class, "Provider unavailable");

        verify(client).getCurrentWeather("api-key", "3.0,4.0", "es");
        verifyNoMoreInteractions(mapper);
    }
}
