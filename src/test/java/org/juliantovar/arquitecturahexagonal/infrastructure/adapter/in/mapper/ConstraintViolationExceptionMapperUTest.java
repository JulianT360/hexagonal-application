package org.juliantovar.arquitecturahexagonal.infrastructure.adapter.in.mapper;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.juliantovar.arquitecturahexagonal.infrastructure.exception.ErrorCodes;
import org.juliantovar.arquitecturahexagonal.infrastructure.exception.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ConstraintViolationExceptionMapperUTest {

    private static final String PATH = "/v1/weather/current";

    private ConstraintViolationExceptionMapper mapper;

    @BeforeEach
    void setUp() throws Exception {
        var uriInfo = mock(UriInfo.class);
        when(uriInfo.getPath()).thenReturn(PATH);

        mapper = new ConstraintViolationExceptionMapper();

        Field field = ConstraintViolationExceptionMapper.class.getDeclaredField("uriInfo");
        field.setAccessible(true);
        field.set(mapper, uriInfo);
    }

    @Test
    void toResponseMapsLatitudeRequiredMessage() {
        ConstraintViolation<?> violation = mock(ConstraintViolation.class);
        when(violation.getMessage()).thenReturn("Latitude is required");

        Response response = mapper.toResponse(
                new ConstraintViolationException(
                        Set.of(violation)));

        assertErrorResponse(response, "Latitude is required");
    }

    @Test
    void defaultResponseWhenViolationsEmpty() {
        Response response = mapper.toResponse(
                new ConstraintViolationException(
                        Set.of()));

        assertErrorResponse(response, "Invalid request");
    }

    /**
     * Metodo para validar si los codigos, mensajes y path coinciden con las respuestas mapeadas.
     *
     * @param response Respuesta con los mensajes de error
     * @param message Mensaje especifico de respuesta
     */
    private static void assertErrorResponse(Response response, String message) {
        var body = (ErrorResponse) response.getEntity();

        assertAll(
                () -> assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus()),
                () -> assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), body.status()),
                () -> assertEquals(ErrorCodes.INVALID_COORDINATES, body.error()),
                () -> assertEquals(message, body.message()),
                () -> assertEquals(PATH, body.path())
        );
    }
}
