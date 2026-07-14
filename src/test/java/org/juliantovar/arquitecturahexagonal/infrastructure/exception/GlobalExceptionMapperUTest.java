package org.juliantovar.arquitecturahexagonal.infrastructure.exception;

import jakarta.ws.rs.core.UriInfo;
import org.juliantovar.arquitecturahexagonal.domain.exception.client.InvalidApiKeyException;
import org.juliantovar.arquitecturahexagonal.domain.exception.client.InvalidCoordinatesException;
import org.juliantovar.arquitecturahexagonal.domain.exception.client.WeatherNotFoundException;
import org.juliantovar.arquitecturahexagonal.domain.exception.provider.UnexpectedProviderException;
import org.juliantovar.arquitecturahexagonal.domain.exception.provider.WeatherProviderUnavailableException;
import org.juliantovar.arquitecturahexagonal.domain.exception.provider.WeatherRateLimitException;
import org.juliantovar.arquitecturahexagonal.domain.exception.provider.WeatherTimeOutException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Pruebas unitarias para {@link GlobalExceptionMapper}
 *
 * @author Julian Tovar
 * @since 13/07/2026
 */
public class GlobalExceptionMapperUTest {

    private GlobalExceptionMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new GlobalExceptionMapper();

        UriInfo uriInfo = mock(UriInfo.class);
        when(uriInfo.getPath()).thenReturn("/weather/current");
        mapper.uriInfo = uriInfo;
    }

    @Test
    void mapInvalidApiKey() {
        var response = mapper.toResponse(new InvalidApiKeyException("Invalid key"));
        var entity = (ErrorResponse) response.getEntity();

        assertEquals(401, response.getStatus());
        assertEquals(ErrorCode.INVALID_API_KEY, entity.error());
        assertEquals("Invalid key", entity.message());
        assertEquals("/weather/current", entity.path());
    }

    @Test
    void mapInvalidCoordinates() {
        var response = mapper.toResponse(new InvalidCoordinatesException("Invalid coordinates"));
        var entity = (ErrorResponse) response.getEntity();

        assertEquals(400, response.getStatus());
        assertEquals(ErrorCode.INVALID_COORDINATES, entity.error());
    }

    @Test
    void mapWeatherNotFound() {
        var response = mapper.toResponse(new WeatherNotFoundException("Not found"));
        var entity = (ErrorResponse) response.getEntity();

        assertEquals(404, response.getStatus());
        assertEquals(ErrorCode.WEATHER_NOT_FOUND, entity.error());
    }

    @Test
    void mapProviderUnavailable() {
        var response = mapper.toResponse(new WeatherProviderUnavailableException("Unavailable"));
        var entity = (ErrorResponse) response.getEntity();

        assertEquals(503, response.getStatus());
        assertEquals(ErrorCode.WEATHER_SERVICE_UNAVAILABLE, entity.error());
    }

    @Test
    void mapRateLimit() {
        var response = mapper.toResponse(new WeatherRateLimitException("Rate limit"));
        var entity = (ErrorResponse) response.getEntity();

        assertEquals(429, response.getStatus());
        assertEquals(ErrorCode.WEATHER_RATE_LIMIT_REACHED, entity.error());
    }

    @Test
    void mapTimeoutAsUnavailable() {
        var response = mapper.toResponse(new WeatherTimeOutException("Timeout"));
        var entity = (ErrorResponse) response.getEntity();

        assertEquals(503, response.getStatus());
        assertEquals(ErrorCode.WEATHER_SERVICE_UNAVAILABLE, entity.error());
    }

    @Test
    void mapUnexpectedProviderAsUnavailable() {
        var response = mapper.toResponse(new UnexpectedProviderException("Unexpected"));
        var entity = (ErrorResponse) response.getEntity();

        assertEquals(503, response.getStatus());
        assertEquals(ErrorCode.WEATHER_SERVICE_UNAVAILABLE, entity.error());
    }

    @Test
    void mapUnknownExceptionAsInternalServerError() {
        var response = mapper.toResponse(new RuntimeException("Boom"));
        var entity = (ErrorResponse) response.getEntity();

        assertEquals(500, response.getStatus());
        assertEquals(ErrorCode.INTERNAL_SERVER_ERROR, entity.error());
        assertEquals("Unexpected server error", entity.message());
    }
}
