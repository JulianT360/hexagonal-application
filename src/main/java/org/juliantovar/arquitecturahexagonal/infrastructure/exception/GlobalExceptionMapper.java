package org.juliantovar.arquitecturahexagonal.infrastructure.exception;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;
import org.juliantovar.arquitecturahexagonal.domain.exception.client.InvalidApiKeyException;
import org.juliantovar.arquitecturahexagonal.domain.exception.client.InvalidCoordinatesException;
import org.juliantovar.arquitecturahexagonal.domain.exception.client.WeatherNotFoundException;
import org.juliantovar.arquitecturahexagonal.domain.exception.provider.ExternalServiceException;
import org.juliantovar.arquitecturahexagonal.domain.exception.provider.WeatherRateLimitException;
import org.juliantovar.arquitecturahexagonal.domain.exception.provider.WeatherProviderUnavailableException;

import java.time.LocalDateTime;

/**
 * Mapper global para las excepciones de la api
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {

    private static final Logger LOG = Logger.getLogger(GlobalExceptionMapper.class);

    @Context
    UriInfo uriInfo;

    /**
     * Metodo que genera un objeto de respuesta a partir de la excepción
     *
     * @param exception La excepción para convertirla a respuesta
     * @return objeto {@link Response} con la información del error.
     *
     */
    @Override
    public Response toResponse(Exception exception) {

        if(exception instanceof InvalidApiKeyException invalidApiKey) {
            return buildResponse(
                    exception,
                    Response.Status.UNAUTHORIZED,
                    ErrorCode.INVALID_API_KEY,
                    invalidApiKey.getMessage());
        }

        if(exception instanceof WeatherProviderUnavailableException unavailable) {
            return buildResponse(
                    exception,
                    Response.Status.SERVICE_UNAVAILABLE,
                    ErrorCode.WEATHER_SERVICE_UNAVAILABLE,
                    unavailable.getMessage());
        }

        if(exception instanceof WeatherRateLimitException rateLimit) {
            return buildResponse(
                    exception,
                    Response.Status.TOO_MANY_REQUESTS,
                    ErrorCode.WEATHER_RATE_LIMIT_REACHED,
                    rateLimit.getMessage());
        }

        if(exception instanceof WeatherNotFoundException notFound) {
            return buildResponse(
                    exception,
                    Response.Status.NOT_FOUND,
                    ErrorCode.WEATHER_NOT_FOUND,
                    notFound.getMessage());
        }

        if(exception instanceof InvalidCoordinatesException invalid) {
            return buildResponse(
                    exception,
                    Response.Status.BAD_REQUEST,
                    ErrorCode.INVALID_COORDINATES,
                    invalid.getMessage());
        }

        if(exception instanceof ExternalServiceException external) {
            return buildResponse(
                    exception,
                    Response.Status.SERVICE_UNAVAILABLE,
                    ErrorCode.WEATHER_SERVICE_UNAVAILABLE,
                    external.getMessage());
        }

        return buildResponse(
                exception,
                Response.Status.INTERNAL_SERVER_ERROR,
                ErrorCode.INTERNAL_SERVER_ERROR,
                "Unexpected server error");
    }

    /**
     * Metodo para construir la respuesta de error
     *
     * @param exception     Excepción {@link Exception}
     * @param status        Código de estado HTTP {@link Response.Status}
     * @param errorCode     Código de error desde {@link ErrorCode}
     * @param message       Mensaje de error
     * @return Objeto {@link Response} con los datos del error provenientes de la excepción.
     *
     */
    private Response buildResponse(
            Exception exception,
            Response.Status status,
            ErrorCode errorCode,
            String message) {

        LOG.warnv(exception,
                "Generating error response. status={0} code={1}",
                status.getStatusCode(),
                errorCode);

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                status.getStatusCode(),
                errorCode,
                message,
                uriInfo.getPath()
        );

        return Response
                .status(status)
                .entity(response)
                .build();
    }
}
