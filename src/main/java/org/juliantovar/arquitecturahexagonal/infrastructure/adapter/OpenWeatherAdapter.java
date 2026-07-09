package org.juliantovar.arquitecturahexagonal.infrastructure.adapter;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.juliantovar.arquitecturahexagonal.domain.model.Weather;
import org.juliantovar.arquitecturahexagonal.domain.port.WeatherClientPort;
import org.juliantovar.arquitecturahexagonal.infrastructure.client.OpenWeatherClient;
import org.juliantovar.arquitecturahexagonal.infrastructure.config.OpenWeatherConfiguration;
import org.juliantovar.arquitecturahexagonal.infrastructure.mapper.WeatherMapper;

/**
 * Adapter to consume open weather client.
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
@ApplicationScoped
public class OpenWeatherAdapter implements WeatherClientPort {

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
    public Uni<Weather> getCurrentWeather(Double latitude, Double longitude) {
        return client.getCurrentWeather(
                    latitude,
                    longitude,
                    configuration.apiKey(),
                    configuration.client().units(),
                    configuration.client().language()
                ).map(mapper::toDomain);
    }
}
