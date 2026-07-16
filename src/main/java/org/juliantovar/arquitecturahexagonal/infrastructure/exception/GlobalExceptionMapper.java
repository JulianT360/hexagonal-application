package org.juliantovar.arquitecturahexagonal.infrastructure.exception;

import io.quarkus.logging.Log;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.time.LocalDateTime;
import org.juliantovar.arquitecturahexagonal.domain.exception.InvalidCoordinatesException;
import org.juliantovar.arquitecturahexagonal.domain.exception.WeatherNotFoundException;
import org.juliantovar.arquitecturahexagonal.infrastructure.exception.provider.InvalidApiKeyException;
import org.juliantovar.arquitecturahexagonal.infrastructure.exception.provider.ProviderRateLimitException;
import org.juliantovar.arquitecturahexagonal.infrastructure.exception.provider.WeatherProviderUnavailableException;
import org.juliantovar.arquitecturahexagonal.shared.ErrorMessages;

/**
 * Mapper global para las excepciones de la api.
 */
@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {

  @Context
  UriInfo uriInfo;

  /**
   * Metodo que genera un objeto de respuesta a partir de la excepción.
   *
   * @param exception La excepción para convertirla a respuesta
   * @return objeto {@link Response} con la información del error.
   */
  @Override
  public Response toResponse(Exception exception) {

    return switch (exception) {
      case InvalidApiKeyException invalidApiKey -> buildResponse(
              exception,
              Response.Status.UNAUTHORIZED,
              ErrorCodes.INVALID_API_KEY,
              invalidApiKey.getMessage());

      case WeatherProviderUnavailableException unavailable -> buildResponse(
              exception,
              Response.Status.SERVICE_UNAVAILABLE,
              ErrorCodes.WEATHER_SERVICE_UNAVAILABLE,
              unavailable.getMessage());

      case ProviderRateLimitException rateLimit -> buildResponse(
              exception,
              Response.Status.TOO_MANY_REQUESTS,
              ErrorCodes.WEATHER_RATE_LIMIT_REACHED,
              rateLimit.getMessage());

      case WeatherNotFoundException notFound -> buildResponse(
              exception,
              Response.Status.NOT_FOUND,
              ErrorCodes.WEATHER_NOT_FOUND,
              notFound.getMessage());

      case InvalidCoordinatesException invalid -> buildResponse(
              exception,
              Response.Status.BAD_REQUEST,
              ErrorCodes.INVALID_COORDINATES,
              invalid.getMessage());

      case ExternalServiceException external -> buildResponse(
              exception,
              Response.Status.SERVICE_UNAVAILABLE,
              ErrorCodes.WEATHER_SERVICE_UNAVAILABLE,
              external.getMessage());

      default -> buildResponse(
              exception,
              Response.Status.INTERNAL_SERVER_ERROR,
              ErrorCodes.INTERNAL_SERVER_ERROR,
              ErrorMessages.UNEXPECTED_SERVER_ERROR);
    };
  }

  /**
   * Metodo para construir la respuesta de error.
   *
   * @param exception  Excepción {@link Exception}
   * @param status     Código de estado HTTP {@link Response.Status}
   * @param errorCodes Código de error desde {@link ErrorCodes}
   * @param message    Mensaje de error
   * @return Objeto {@link Response} con los datos del error provenientes de la excepción.
   *
   */
  private Response buildResponse(
          Exception exception,
          Response.Status status,
          ErrorCodes errorCodes,
          String message) {

    Log.debugf(exception,
            "Generating error response. status={0} code={1}",
            status.getStatusCode(),
            errorCodes);

    ErrorResponse response = new ErrorResponse(
            LocalDateTime.now(),
            status.getStatusCode(),
            errorCodes,
            message,
            uriInfo.getPath()
    );

    return Response
            .status(status)
            .entity(response)
            .build();
  }
}
