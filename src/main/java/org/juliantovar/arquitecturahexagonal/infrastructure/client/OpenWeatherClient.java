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
import org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.openweather.OpenWeatherResponseDto;

/**
 * Cliente rest para consumir proveedor OpenWeather API
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
     * Endpoint para consumir y obtener la información del clima actual
     *
     * @param latitude      Latitud de la ubicación
     * @param longitude     Longitud de la ubicación
     * @param apiKey        API Key para consumir el API Rest
     * @param units         Unidades de medida (metric, imperial, standard)
     * @param language      Lenguaje para la respuesta (es, en, fr, etc.)
     * @return Información del clima actual: {@link Uni<OpenWeatherResponseDto>}
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
