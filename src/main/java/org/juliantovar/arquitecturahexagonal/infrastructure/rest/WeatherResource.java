package org.juliantovar.arquitecturahexagonal.infrastructure.rest;

import io.smallrye.mutiny.Uni;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.juliantovar.arquitecturahexagonal.application.ports.in.GetCurrentWeatherUseCase;
import org.juliantovar.arquitecturahexagonal.infrastructure.rest.mapper.WeatherRestMapper;

@Path("/v1/weather")
@AllArgsConstructor
public class WeatherResource {

    private final GetCurrentWeatherUseCase useCase;
    private final WeatherRestMapper mapper;

    @GET
    @Path("/current")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Get current weather",
            description = "Returns current weather information from coordinates given")
    public Uni<Response> getCurrentWeather(@QueryParam("lat")
                                           @NotNull(message = "Latitude is required")
                                           @DecimalMin(value = "-90.0", message = "Latitude must be a value between -90 and 90")
                                           @DecimalMax(value = "90.0", message = "Latitude must be a value between -90 and 90")
                                           Double latitude,

                                           @QueryParam("lon")
                                           @NotNull(message = "Longitude is required")
                                           @DecimalMin(value = "-180.0", message = "Longitude must be a value between -180 and 180")
                                           @DecimalMax(value = "180.0", message = "Longitude must be a value between -180 and 180")
                                           Double longitude) {

        return useCase.getCurrentWeather(mapper.domainToCoordinates(latitude, longitude))
                .map(weather -> Response.ok(mapper.domainToResponse(weather)).build());
    }
}
