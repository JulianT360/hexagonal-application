package org.juliantovar.arquitecturahexagonal.infrastructure.adapter;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import org.juliantovar.arquitecturahexagonal.domain.exception.WeatherServiceUnavailableException;
import org.juliantovar.arquitecturahexagonal.domain.model.Weather;
import org.juliantovar.arquitecturahexagonal.domain.port.WeatherClientPort;
import org.juliantovar.arquitecturahexagonal.infrastructure.client.OpenWeatherClient;
import org.juliantovar.arquitecturahexagonal.infrastructure.config.OpenWeatherConfiguration;
import org.juliantovar.arquitecturahexagonal.infrastructure.mapper.WeatherMapper;
import org.juliantovar.arquitecturahexagonal.shared.ErrorMessages;

@ApplicationScoped
public class OpenWeatherAdapter implements WeatherClientPort {

    private static final Logger LOG = Logger.getLogger(OpenWeatherAdapter.class);

    private final OpenWeatherClient client;
    private final WeatherMapper mapper;
    private final OpenWeatherConfiguration configuration;

    public  OpenWeatherAdapter(@RestClient OpenWeatherClient client,
                               WeatherMapper mapper,
                               OpenWeatherConfiguration configuration) {
        this.client = client;
        this.mapper = mapper;
        this.configuration = configuration;
    }

    @Override
    @Timeout
    @Retry
    @CircuitBreaker
    @Fallback(fallbackMethod = "fallbackWeather")
    public Uni<Weather> getCurrentWeather(Double latitude, Double longitude) {
        LOG.infov("Calling OpenWeatherApi. latitude={0} longitude={1}", latitude, longitude);
        return client.getCurrentWeather(
                    latitude,
                    longitude,
                    configuration.apiKey(),
                    configuration.client().units(),
                    configuration.client().language())
                .map(weather -> {
                    LOG.infov("Weather successfully retrieved for latitude={0} longitude={1}", latitude, longitude);
                    return mapper.toDomain(weather);
                })
                .onFailure().invoke(error ->
                        LOG.errorv(error, "Error calling OpenWeather API. latitude={0} longitude={1}", latitude, longitude));
    }

    public Uni<Weather> fallbackWeather(Double latitude, Double longitude) {
        LOG.errorv("Error calling fallbackWeather");
        return Uni.createFrom().failure(
                new WeatherServiceUnavailableException(ErrorMessages.WEATHER_SERVICE_UNAVAILABLE_FALLBACK)
        );
    }
}
