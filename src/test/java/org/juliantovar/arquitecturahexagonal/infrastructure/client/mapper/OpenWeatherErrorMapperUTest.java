package org.juliantovar.arquitecturahexagonal.infrastructure.client.mapper;

import jakarta.ws.rs.core.Response;
import org.juliantovar.arquitecturahexagonal.domain.exception.client.InvalidApiKeyException;
import org.juliantovar.arquitecturahexagonal.domain.exception.client.WeatherNotFoundException;
import org.juliantovar.arquitecturahexagonal.domain.exception.provider.UnexpectedProviderException;
import org.juliantovar.arquitecturahexagonal.domain.exception.provider.WeatherProviderUnavailableException;
import org.juliantovar.arquitecturahexagonal.domain.exception.provider.WeatherRateLimitException;
import org.juliantovar.arquitecturahexagonal.domain.exception.provider.WeatherTimeOutException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

/**
 * Pruebas unitarias para {@link OpenWeatherErrorMapper}
 *
 * @author Julian Tovar
 * @since 13/07/2026
 */
public class OpenWeatherErrorMapperUTest {

    private final OpenWeatherErrorMapper mapper = new OpenWeatherErrorMapper();

    @Test
    void mapUnauthorizedToInvalidApiKey() {
        assertInstanceOf(
                InvalidApiKeyException.class,
                mapper.toThrowable(Response.status(401).build())
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
                mapper.toThrowable(Response.status(500).build())
        );
    }

    @Test
    void mapGatewayTimeoutToTimeout() {
        assertInstanceOf(
                WeatherTimeOutException.class,
                mapper.toThrowable(Response.status(504).build())
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
