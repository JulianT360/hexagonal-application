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
 * Resource of weather
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
     * Endpoint to get the current weather.
     *
     * @param latitude  Latitude of the location
     * @param longitude Longitude of the location
     * @return Object {@link Uni<WeatherResponse>} with current weather data
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
