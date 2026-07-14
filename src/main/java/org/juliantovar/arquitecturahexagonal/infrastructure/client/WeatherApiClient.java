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
 *  Cliente rest para consumir proveedor Weather API
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
     * Endpoint para consumir y obtener la información del clima actual
     *
     * @param apiKey        API Key para consumir el API Rest
     * @param coordinates   Coordenadas de la ubicación para obtener el clima actual (latitud,longitud)
     * @param language      Lenguaje para la respuesta (es, en, fr, etc.)
     * @return Información del clima actual: {@link Uni<WeatherApiResponseDto>}
     */
    @GET
    @Path("/current.json")
    Uni<WeatherApiResponseDto> getCurrentWeather(
            @QueryParam("key") String apiKey,
            @QueryParam("q") String coordinates,
            @QueryParam("lang") String language
    );
}
