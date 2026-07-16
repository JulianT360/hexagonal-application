package org.juliantovar.arquitecturahexagonal.infrastructure.client.mapper;

import jakarta.ws.rs.core.Response;
import org.juliantovar.arquitecturahexagonal.domain.exception.WeatherNotFoundException;
import org.juliantovar.arquitecturahexagonal.infrastructure.exception.provider.InvalidApiKeyException;
import org.juliantovar.arquitecturahexagonal.infrastructure.exception.provider.ProviderRateLimitException;
import org.juliantovar.arquitecturahexagonal.infrastructure.exception.provider.UnexpectedProviderException;
import org.juliantovar.arquitecturahexagonal.infrastructure.exception.provider.WeatherProviderUnavailableException;
import org.juliantovar.arquitecturahexagonal.shared.ErrorMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@ExtendWith(MockitoExtension.class)
class WeatherProviderErrorMapperUTest {

    private final WeatherProviderErrorMapper mapper = new WeatherProviderErrorMapper();

    @ParameterizedTest
    @ValueSource(ints = {400, 404})
    void toThrowableMapsNotFoundResponses(int status) {
        var throwable = mapper.toThrowable(Response.status(status).build());

        assertInstanceOf(WeatherNotFoundException.class, throwable);
        assertEquals(ErrorMessages.WEATHER_NOT_FOUND, throwable.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {401, 403})
    void toThrowableMapsInvalidApiKeyResponses(int status) {
        var throwable = mapper.toThrowable(Response.status(status).build());

        assertInstanceOf(InvalidApiKeyException.class, throwable);
        assertEquals(ErrorMessages.INVALID_API_KEY, throwable.getMessage());
    }

    @Test
    void toThrowableMapsRateLimitResponse() {
        var throwable = mapper.toThrowable(Response.status(429).build());

        assertInstanceOf(ProviderRateLimitException.class, throwable);
        assertEquals(ErrorMessages.WEATHER_RATE_LIMIT_REACHED, throwable.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {500, 502, 503, 504})
    void toThrowableMapsUnavailableResponses(int status) {
        var throwable = mapper.toThrowable(Response.status(status).build());

        assertInstanceOf(WeatherProviderUnavailableException.class, throwable);
        assertEquals(ErrorMessages.WEATHER_SERVICE_UNAVAILABLE, throwable.getMessage());
    }

    @Test
    void toThrowableMapsUnexpectedResponse() {
        var throwable = mapper.toThrowable(Response.status(418).build());

        assertInstanceOf(UnexpectedProviderException.class, throwable);
        assertEquals(ErrorMessages.UNEXPECTED_PROVIDER_ERROR, throwable.getMessage());
    }
}
