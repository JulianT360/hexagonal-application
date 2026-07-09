package org.juliantovar.arquitecturahexagonal.infrastructure.exception;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;
import org.juliantovar.arquitecturahexagonal.domain.exception.InvalidApiKeyException;
import org.juliantovar.arquitecturahexagonal.domain.exception.InvalidCoordinatesException;
import org.juliantovar.arquitecturahexagonal.domain.exception.WeatherNotFoundException;
import org.juliantovar.arquitecturahexagonal.domain.exception.WeatherServiceUnavailableException;

import java.time.LocalDateTime;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {

    private static final Logger LOG = Logger.getLogger(GlobalExceptionMapper.class);

    @Context
    UriInfo uriInfo;


    @Override
    public Response toResponse(Exception exception) {

        if(exception instanceof InvalidApiKeyException invalidApiKey) {
            return buildResponse(
                    exception,
                    Response.Status.UNAUTHORIZED,
                    ErrorCode.INVALID_API_KEY,
                    invalidApiKey.getMessage());
        }

        if(exception instanceof WeatherServiceUnavailableException unavailable) {
            return buildResponse(
                    exception,
                    Response.Status.SERVICE_UNAVAILABLE,
                    ErrorCode.WEATHER_SERVICE_UNAVAILABLE,
                    unavailable.getMessage());
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

        return buildResponse(
                exception,
                Response.Status.INTERNAL_SERVER_ERROR,
                ErrorCode.INTERNAL_SERVER_ERROR,
                "Unexpected server error");
    }

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
