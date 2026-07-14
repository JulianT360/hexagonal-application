package org.juliantovar.arquitecturahexagonal.infrastructure.client.mapper;

import jakarta.ws.rs.core.Response;
import org.juliantovar.arquitecturahexagonal.domain.exception.client.InvalidApiKeyException;
import org.juliantovar.arquitecturahexagonal.domain.exception.client.WeatherNotFoundException;
import org.juliantovar.arquitecturahexagonal.domain.exception.provider.UnexpectedProviderException;
import org.juliantovar.arquitecturahexagonal.domain.exception.provider.WeatherProviderUnavailableException;
import org.juliantovar.arquitecturahexagonal.domain.exception.provider.WeatherRateLimitException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

/**
 * Pruebas unitarias para {@link WeatherApiErrorMapper}
 *
 * @author Julian Tovar
 * @since 13/07/2026
 */
public class WeatherApiErrorMapperUTest {

    private final WeatherApiErrorMapper mapper = new WeatherApiErrorMapper();

    @Test
    void mapBadRequestToWeatherNotFound() {
        assertInstanceOf(
                WeatherNotFoundException.class,
                mapper.toThrowable(Response.status(400).build())
        );
    }

    @Test
    void mapNotFoundToWeatherNotFound() {
        assertInstanceOf(
                WeatherNotFoundException.class,
                mapper.toThrowable(Response.status(404).build())
        );
    }

    @Test
    void mapUnauthorizedToInvalidApiKey() {
        assertInstanceOf(
                InvalidApiKeyException.class,
                mapper.toThrowable(Response.status(401).build())
        );
    }

    @Test
    void mapForbiddenToInvalidApiKey() {
        assertInstanceOf(
                InvalidApiKeyException.class,
                mapper.toThrowable(Response.status(403).build())
        );
    }

    @Test
    void mapTooManyRequestsToRateLimit() {
        assertInstanceOf(
                WeatherRateLimitException.class,
                mapper.toThrowable(Response.status(429).build())
        );
    }

    @Test
    void mapProviderErrorsToUnavailable() {
        assertInstanceOf(
                WeatherProviderUnavailableException.class,
                mapper.toThrowable(Response.status(503).build())
        );
    }

    @Test
    void mapUnknownStatusToUnexpectedProvider() {
        assertInstanceOf(
                UnexpectedProviderException.class,
                mapper.toThrowable(Response.status(418).build())
        );
    }
}
