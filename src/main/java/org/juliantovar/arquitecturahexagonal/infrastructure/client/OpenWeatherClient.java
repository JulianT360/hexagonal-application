package org.juliantovar.arquitecturahexagonal.infrastructure.client;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.juliantovar.arquitecturahexagonal.infrastructure.client.mapper.OpenWeatherErrorMapper;
import org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.OpenWeatherResponseDto;

/**
 * Rest client for open weather API.
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
@Path("/data/4.0/onecall")
@Produces(MediaType.APPLICATION_JSON)
@RegisterProvider(OpenWeatherErrorMapper.class)
@RegisterRestClient(configKey = "openweather-api")
public interface OpenWeatherClient {

    /**
     * Endpoint to consume and get data of the current weather.
     *
     * @param latitude      Latitude of the location
     * @param longitude     Longitude of the location
     * @param apiKey        Api key to consume REST API
     * @param units         Metric units
     * @param language      Language
     * @return Data of the current weather {@link Uni<OpenWeatherResponseDto>}
     */
    @GET
    @Path("/current")
    Uni<OpenWeatherResponseDto> getCurrentWeather(
            @QueryParam("lat") Double latitude,
            @QueryParam("lon") Double longitude,
            @QueryParam("appid") String apiKey,
            @QueryParam("units") String units,
            @QueryParam("lang") String language
    );

}
