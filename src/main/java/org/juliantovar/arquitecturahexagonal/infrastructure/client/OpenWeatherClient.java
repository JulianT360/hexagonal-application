package org.juliantovar.arquitecturahexagonal.infrastructure.client;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.OpenWeatherResponseDto;

@Path("/data/4.0/onecall")
@RegisterRestClient(configKey = "openweather-api")
public interface OpenWeatherClient {

    @GET
    @Path("/current")
    Uni<OpenWeatherResponseDto> getCurrentWeather(
            @QueryParam("lat") Double latitude,
            @QueryParam("lon") Double longitude,
            @QueryParam("appid") String apiKey
    );

}
