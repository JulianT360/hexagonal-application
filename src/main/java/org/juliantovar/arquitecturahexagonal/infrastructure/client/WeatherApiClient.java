package org.juliantovar.arquitecturahexagonal.infrastructure.client;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.juliantovar.arquitecturahexagonal.infrastructure.client.mapper.WeatherApiErrorMapper;
import org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.weatherapi.WeatherApiResponseDto;

/**
 *  Client interface for weather api.
 *
 * @author Julian Tovar
 * @since 10/07/2026
 */
@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
@RegisterProvider(WeatherApiErrorMapper.class)
@RegisterRestClient(configKey = "weather-api")
public interface WeatherApiClient {

    /**
     * Endpoint to consume and get data of the current weather
     *
     * @param apiKey        Api Key to consume weather api
     * @param coordinates   Coordinates of location to get current weather
     * @param language      Language to generate data
     * @return Data of the current weather {@link Uni<WeatherApiResponseDto>}
     */
    @GET
    @Path("/current.json")
    Uni<WeatherApiResponseDto> getCurrentWeather(
            @QueryParam("key") String apiKey,
            @QueryParam("q") String coordinates,
            @QueryParam("lang") String language
    );
}
