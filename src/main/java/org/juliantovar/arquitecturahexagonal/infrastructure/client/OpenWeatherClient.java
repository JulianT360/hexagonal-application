package org.juliantovar.arquitecturahexagonal.infrastructure.client;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.OpenWeatherResponseDto;

@Path("/data/4.0/onecall")
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "openweather-api")
public interface OpenWeatherClient {

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
