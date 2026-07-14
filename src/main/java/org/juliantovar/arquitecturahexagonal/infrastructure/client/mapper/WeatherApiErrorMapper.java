package org.juliantovar.arquitecturahexagonal.infrastructure.client.mapper;

import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;
import org.juliantovar.arquitecturahexagonal.domain.exception.WeatherException;
import org.juliantovar.arquitecturahexagonal.domain.exception.client.InvalidApiKeyException;
import org.juliantovar.arquitecturahexagonal.domain.exception.client.WeatherNotFoundException;
import org.juliantovar.arquitecturahexagonal.domain.exception.provider.UnexpectedProviderException;
import org.juliantovar.arquitecturahexagonal.domain.exception.provider.WeatherProviderUnavailableException;
import org.juliantovar.arquitecturahexagonal.domain.exception.provider.WeatherRateLimitException;
import org.juliantovar.arquitecturahexagonal.shared.ErrorMessages;

/**
 * Mapper de error para respuestas del proveedor Weather API
 *
 * @author Julian Tovar
 * @since 10/07/2026
 */
public class WeatherApiErrorMapper implements ResponseExceptionMapper<WeatherException> {

    //TODO: improve exception management by status and code returned on body by weather api
    @Override
    public WeatherException toThrowable(Response response) {
        return switch(response.getStatus()) {

            case 400, 404 ->
                    new WeatherNotFoundException(ErrorMessages.WEATHER_NOT_FOUND);

            case 401, 403 ->
                    new InvalidApiKeyException(ErrorMessages.INVALID_API_KEY);

            case 429 ->
                    new WeatherRateLimitException(ErrorMessages.WEATHER_RATE_LIMIT_REACHED);

            case 500, 502, 503, 504 ->
                    new WeatherProviderUnavailableException(ErrorMessages.WEATHER_SERVICE_UNAVAILABLE);

            default ->
                    new UnexpectedProviderException(ErrorMessages.UNEXPECTED_PROVIDER_ERROR);
        };
    }
}
