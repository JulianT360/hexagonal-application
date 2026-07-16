package org.juliantovar.arquitecturahexagonal.infrastructure.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.juliantovar.arquitecturahexagonal.domain.exception.InvalidCoordinatesException;
import org.juliantovar.arquitecturahexagonal.domain.exception.WeatherNotFoundException;
import org.juliantovar.arquitecturahexagonal.infrastructure.exception.provider.InvalidApiKeyException;
import org.juliantovar.arquitecturahexagonal.infrastructure.exception.provider.ProviderRateLimitException;
import org.juliantovar.arquitecturahexagonal.infrastructure.exception.provider.WeatherProviderUnavailableException;
import org.juliantovar.arquitecturahexagonal.shared.ErrorMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionMapperUTest {

    private static final String PATH = "/v1/weather/current";

    private GlobalExceptionMapper mapper;

    @BeforeEach
    void setUp() throws Exception {
        var uriInfo = mock(UriInfo.class);
        when(uriInfo.getPath()).thenReturn(PATH);

        mapper = new GlobalExceptionMapper();

        Field field = GlobalExceptionMapper.class.getDeclaredField("uriInfo");
        field.setAccessible(true);
        field.set(mapper, uriInfo);
    }

    @Test
    void verifyInvalidApiKey() {
        assertErrorResponse(
                mapper.toResponse(new InvalidApiKeyException(ErrorMessages.INVALID_API_KEY)),
                Response.Status.UNAUTHORIZED,
                ErrorCodes.INVALID_API_KEY,
                ErrorMessages.INVALID_API_KEY);
    }

    @Test
    void verifyProviderUnavailable() {
        assertErrorResponse(
                mapper.toResponse(new WeatherProviderUnavailableException(ErrorMessages.WEATHER_SERVICE_UNAVAILABLE)),
                Response.Status.SERVICE_UNAVAILABLE,
                ErrorCodes.WEATHER_SERVICE_UNAVAILABLE,
                ErrorMessages.WEATHER_SERVICE_UNAVAILABLE);
    }

    @Test
    void verifyRateLimit() {
        assertErrorResponse(
                mapper.toResponse(new ProviderRateLimitException(ErrorMessages.WEATHER_RATE_LIMIT_REACHED)),
                Response.Status.TOO_MANY_REQUESTS,
                ErrorCodes.WEATHER_RATE_LIMIT_REACHED,
                ErrorMessages.WEATHER_RATE_LIMIT_REACHED);
    }

    @Test
    void verifyWeatherNotFound() {
        assertErrorResponse(
                mapper.toResponse(new WeatherNotFoundException(ErrorMessages.WEATHER_NOT_FOUND)),
                Response.Status.NOT_FOUND,
                ErrorCodes.WEATHER_NOT_FOUND,
                ErrorMessages.WEATHER_NOT_FOUND);
    }

    @Test
    void verifyInvalidCoordinates() {
        assertErrorResponse(
                mapper.toResponse(new InvalidCoordinatesException("Latitude is required")),
                Response.Status.BAD_REQUEST,
                ErrorCodes.INVALID_COORDINATES,
                "Latitude is required");
    }

    @Test
    void verifyExternalServiceException() {
        assertErrorResponse(
                mapper.toResponse(new ExternalServiceException("Provider unavailable")),
                Response.Status.SERVICE_UNAVAILABLE,
                ErrorCodes.WEATHER_SERVICE_UNAVAILABLE,
                "Provider unavailable");
    }

    @Test
    void verifyUnexpectedException() {
        assertErrorResponse(
                mapper.toResponse(new RuntimeException("Database is down")),
                Response.Status.INTERNAL_SERVER_ERROR,
                ErrorCodes.INTERNAL_SERVER_ERROR,
                ErrorMessages.UNEXPECTED_SERVER_ERROR);
    }

    /**
     * Metodo para comparar el cuerpo del objeto mapeado a partir de la excepcion
     * con el codigo, el tipo de error y el mensaje esperados
     *
     * @param response  la respuesta a verificar
     * @param status    el estado esperado de la respuesta
     * @param errorCode el codigo de error esperado
     * @param message   el mensaje esperado
     */
    private static void assertErrorResponse(Response response,
                                            Response.Status status,
                                            ErrorCodes errorCode,
                                            String message) {
        var body = (ErrorResponse) response.getEntity();

        assertAll(
                () -> assertEquals(status.getStatusCode(), response.getStatus()),
                () -> assertEquals(status.getStatusCode(), body.status()),
                () -> assertEquals(errorCode, body.error()),
                () -> assertEquals(message, body.message()),
                () -> assertEquals(PATH, body.path())
        );
    }
}
