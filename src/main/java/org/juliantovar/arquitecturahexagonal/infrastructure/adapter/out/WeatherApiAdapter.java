package org.juliantovar.arquitecturahexagonal.infrastructure.adapter.out;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.Duration;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.juliantovar.arquitecturahexagonal.application.ports.out.WeatherProviderPort;
import org.juliantovar.arquitecturahexagonal.domain.model.Coordinates;
import org.juliantovar.arquitecturahexagonal.domain.model.Weather;
import org.juliantovar.arquitecturahexagonal.infrastructure.adapter.out.mapper.WeatherApiMapper;
import org.juliantovar.arquitecturahexagonal.infrastructure.client.WeatherApiClient;
import org.juliantovar.arquitecturahexagonal.infrastructure.config.WeatherApiConfiguration;
import org.juliantovar.arquitecturahexagonal.infrastructure.exception.ExternalServiceException;

/**
 * Clase adaptador para consumir el proveedor Weather API.
 */
@ApplicationScoped
public class WeatherApiAdapter implements WeatherProviderPort {

  private final WeatherApiClient client;
  private final WeatherApiMapper mapper;
  private final WeatherApiConfiguration configuration;

  private static final int INITIAL_BACKOFF_MILLIS = 500;
  private static final int MAX_BACKOFF_SECONDS = 2;

  /**
   * Constructor.
   *
   * @param client cliente rest del proveedor
   * @param mapper mapper para convertir la respuesta del proveedor a objeto de dominio
   * @param configuration configuración del proveedor
   */
  public WeatherApiAdapter(@RestClient WeatherApiClient client,
                           WeatherApiMapper mapper,
                           WeatherApiConfiguration configuration) {
    this.client = client;
    this.mapper = mapper;
    this.configuration = configuration;
  }

  /**
   * Obtener clima actual a partir de la latitud y la longitud de la ubicación.
   *
   * @param coordinates Coordenadas de la ubicación
   * @return Información del clima obtenida del proveedor
   */
  @Override
  @CircuitBreaker
  public Uni<Weather> getCurrentWeather(Coordinates coordinates) {
    Log.debugv("Calling WeatherApi. latitude={0} longitude={1}",
            coordinates.getLatitude(),
            coordinates.getLongitude());

    return client.getCurrentWeather(
                    configuration.apiKey(),
                    coordinates.getLatitude() + "," + coordinates.getLongitude(),
                    configuration.client().language())
            .map(mapper::dtoToDomain)
            .invoke(() ->
                    Log.debugv("Weather successfully retrieved"))

            /* Provider connection errors */
            .onFailure(ExternalServiceException.class)
            .retry()
            .withBackOff(Duration.ofMillis(INITIAL_BACKOFF_MILLIS),
                    Duration.ofSeconds(MAX_BACKOFF_SECONDS))
            .atMost(3)

            .onFailure()
            .invoke(error -> Log.errorf(
                    error,
                    "Error calling WeatherAPI. type=%s message=%s",
                    error.getClass().getName(),
                    error.getMessage()
            ));
  }
}
