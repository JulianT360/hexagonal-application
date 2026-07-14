package org.juliantovar.arquitecturahexagonal.infrastructure.rest;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.juliantovar.arquitecturahexagonal.application.usecase.GetCurrentWeatherUseCase;
import org.juliantovar.arquitecturahexagonal.application.command.GetCurrentWeatherCommand;
import org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.openweather.WeatherResponse;
import org.juliantovar.arquitecturahexagonal.infrastructure.mapper.WeatherMapper;

/**
 * Recurso RESTful del clima
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
@Tag(name = "Weather")
@Path("/weather")
@Produces(MediaType.APPLICATION_JSON)
public class WeatherResource {

    private final GetCurrentWeatherUseCase useCase;
    private final WeatherMapper mapper;

    public WeatherResource(GetCurrentWeatherUseCase useCase, WeatherMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    /**
     * Endpoint para consultar el clima actual
     *
     * @param latitude  Latitud de la ubicación
     * @param longitude Longitud de la ubicación
     * @return Objeto {@link Uni<WeatherResponse>} con la información del clima actual
     */
    @GET
    @Path("/current")
    @Operation(
            summary = "Get current weather",
            description = "Returns current weather information from coordinates given"
    )
    public Uni<WeatherResponse> getCurrentWeather(@QueryParam("lat") Double latitude,  @QueryParam("lon") Double longitude) {
        var command = new GetCurrentWeatherCommand(latitude, longitude);
        return useCase.execute(command).map(mapper::toResponse);
    }
}
