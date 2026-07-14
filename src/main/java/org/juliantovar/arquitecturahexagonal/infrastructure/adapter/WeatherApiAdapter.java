package org.juliantovar.arquitecturahexagonal.infrastructure.adapter;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import org.juliantovar.arquitecturahexagonal.domain.exception.provider.ExternalServiceException;
import org.juliantovar.arquitecturahexagonal.domain.model.Coordinates;
import org.juliantovar.arquitecturahexagonal.domain.model.Weather;
import org.juliantovar.arquitecturahexagonal.domain.port.WeatherClientPort;
import org.juliantovar.arquitecturahexagonal.infrastructure.qualifiers.WeatherApiProvider;
import org.juliantovar.arquitecturahexagonal.infrastructure.client.WeatherApiClient;
import org.juliantovar.arquitecturahexagonal.infrastructure.config.WeatherApiConfiguration;
import org.juliantovar.arquitecturahexagonal.infrastructure.mapper.WeatherApiMapper;

import java.time.Duration;

/**
 * Adapter class to consume Weather API.
 * Clase adaptador para consumir el proveedor Weather API
 *
 * @author Julian Tovar
 * @since 10/07/2026
 */
@ApplicationScoped
@WeatherApiProvider
public class WeatherApiAdapter implements WeatherClientPort {

    private static final Logger LOG = Logger.getLogger(WeatherApiAdapter.class);

    private final WeatherApiClient client;
    private final WeatherApiMapper mapper;
    private final WeatherApiConfiguration configuration;

    /**
     * Constructor de la clase
     *
     * @param client            Cliente a consumir {@link WeatherApiClient}
     * @param mapper            Mapper para convertir objetos {@link WeatherApiMapper}
     * @param configuration     Configuración de la API remota {@link WeatherApiConfiguration}
     */
    public WeatherApiAdapter(@RestClient WeatherApiClient client,
                             WeatherApiMapper mapper,
                             WeatherApiConfiguration configuration) {
        this.client = client;
        this.mapper = mapper;
        this.configuration = configuration;
    }

    /**
     * Obtener clima actual a partir de la latitud y la longitud de la ubicación
     *
     * @param latitude      Latitud de la ubicación
     * @param longitude     Longitud de la ubicación
     * @return Información del clima obtenida {@link Uni<Weather>}
     */
    @Override
    @CircuitBreaker
    public Uni<Weather> getCurrentWeather(Double latitude, Double longitude) {
        LOG.infov("Calling WeatherApi. latitude={0} longitude={1}", latitude, longitude);

        String query = new Coordinates(latitude, longitude).asQuery();

        return client.getCurrentWeather(
                        configuration.apiKey(),
                        query,
                        configuration.client().language())
                .onItem()
                .transform(mapper::toDomain)
                .invoke(() -> LOG.info("Weather successfully retrieved"))

                /* Provider connection errors */
                .onFailure(ExternalServiceException.class)
                .retry()
                .withBackOff(Duration.ofMillis(500), Duration.ofSeconds(2))
                .atMost(3)

                .onFailure()
                .invoke(error -> LOG.errorf(
                        error,
                        "Error calling WeatherAPI. type=%s message=%s",
                        error.getClass().getName(),
                        error.getMessage()
                ));
    }
}
