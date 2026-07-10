package org.juliantovar.arquitecturahexagonal.infrastructure.client.mapper;

import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;
import org.juliantovar.arquitecturahexagonal.domain.exception.WeatherException;
import org.juliantovar.arquitecturahexagonal.domain.exception.client.InvalidApiKeyException;
import org.juliantovar.arquitecturahexagonal.domain.exception.client.WeatherNotFoundException;
import org.juliantovar.arquitecturahexagonal.domain.exception.provider.UnexpectedProviderException;
import org.juliantovar.arquitecturahexagonal.domain.exception.provider.WeatherProviderUnavailableException;
import org.juliantovar.arquitecturahexagonal.domain.exception.provider.WeatherRateLimitException;
import org.juliantovar.arquitecturahexagonal.domain.exception.provider.WeatherTimeOutException;
import org.juliantovar.arquitecturahexagonal.shared.ErrorMessages;

/**
 * Exception mapper for open weather response.
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
public class OpenWeatherErrorMapper implements ResponseExceptionMapper<WeatherException> {


    @Override
    public WeatherException toThrowable(Response response) {
        return switch(response.getStatus()) {

            case 401 ->
                    new InvalidApiKeyException(ErrorMessages.INVALID_API_KEY);

            case 404 ->
                    new WeatherNotFoundException(ErrorMessages.WEATHER_NOT_FOUND);

            case 429 ->
                new WeatherRateLimitException(ErrorMessages.WEATHER_RATE_LIMIT_REACHED);

            case 500, 502, 503 ->
                new WeatherProviderUnavailableException(ErrorMessages.WEATHER_SERVICE_UNAVAILABLE);

            case 504 ->
                    new WeatherTimeOutException(ErrorMessages.WEATHER_SERVICE_UNAVAILABLE);

            default ->
                    new UnexpectedProviderException(ErrorMessages.UNEXPECTED_PROVIDER_ERROR);
        };
    }
}
