package org.juliantovar.arquitecturahexagonal.infrastructure.client;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.juliantovar.arquitecturahexagonal.infrastructure.client.dto.WeatherApiResponseDto;
import org.juliantovar.arquitecturahexagonal.infrastructure.client.mapper.WeatherProviderErrorMapper;

/**
 * Cliente rest para consumir proveedor Weather API.
 */
@RegisterProvider(WeatherProviderErrorMapper.class)
@RegisterRestClient(configKey = "weather-api")
public interface WeatherApiClient {

  /**
   * Endpoint para consumir y obtener la información del clima actual.
   *
   * @param apiKey      API Key para consumir el API Rest
   * @param coordinates Coordenadas de la ubicación para obtener el clima actual (latitud,longitud)
   * @param language    Lenguaje para la respuesta (es, en, fr, etc.)
   * @return información del clima actual
   */
  @GET
  @Path("/v1/current.json")
  @Produces(MediaType.APPLICATION_JSON)
  Uni<WeatherApiResponseDto> getCurrentWeather(
          @QueryParam("key") String apiKey,
          @QueryParam("q") String coordinates,
          @QueryParam("lang") String language
  );
}
