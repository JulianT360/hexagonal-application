package org.juliantovar.arquitecturahexagonal.infrastructure.adapter;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import org.juliantovar.arquitecturahexagonal.domain.exception.provider.ExternalServiceException;
import org.juliantovar.arquitecturahexagonal.domain.model.Weather;
import org.juliantovar.arquitecturahexagonal.domain.port.WeatherClientPort;
import org.juliantovar.arquitecturahexagonal.infrastructure.client.OpenWeatherClient;
import org.juliantovar.arquitecturahexagonal.infrastructure.config.OpenWeatherConfiguration;
import org.juliantovar.arquitecturahexagonal.infrastructure.mapper.WeatherMapper;

import java.time.Duration;

/**
 * Adapter to consume open weather client.
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
@ApplicationScoped
public class OpenWeatherAdapter implements WeatherClientPort {

    private static final Logger LOG = Logger.getLogger(OpenWeatherAdapter.class);

    private final OpenWeatherClient client;
    private final WeatherMapper mapper;
    private final OpenWeatherConfiguration configuration;

    /**
     * Constructor
     *
     * @param client        Client to consume {@link OpenWeatherClient}
     * @param mapper        Mapper {@link WeatherMapper}
     * @param configuration Application configuration {@link OpenWeatherConfiguration}
     */
    public  OpenWeatherAdapter(@RestClient OpenWeatherClient client,
                               WeatherMapper mapper,
                               OpenWeatherConfiguration configuration) {
        this.client = client;
        this.mapper = mapper;
        this.configuration = configuration;
    }

    /**
     * Get current weather from latitude and longitude of a location
     *
     * @param latitude      Latitude of the location.
     * @param longitude     Longitude of the location.
     * @return Weather data obtained {@link Uni<Weather>}
     */
    @Override
    @CircuitBreaker
    public Uni<Weather> getCurrentWeather(Double latitude, Double longitude) {
        LOG.infov("Calling OpenWeatherApi. latitude={0} longitude={1}", latitude, longitude);
        return client.getCurrentWeather(
                    latitude,
                    longitude,
                    configuration.apiKey(),
                    configuration.client().units(),
                    configuration.client().language())
                .onItem()
                .transform(mapper::toDomain)
                .invoke(() -> LOG.info("Weather successfully retrieved"))

                /* Provider connection errors */
                .onFailure(ExternalServiceException.class)
                .retry()
                .withBackOff(Duration.ofMillis(500), Duration.ofSeconds(2))
                .atMost(3)

                .onFailure()
                .invoke(error -> LOG.error("Error calling OpenWeather API"));
    }
}
