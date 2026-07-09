package org.juliantovar.arquitecturahexagonal.infrastructure.client.mapper;

import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;
import org.juliantovar.arquitecturahexagonal.domain.exception.InvalidApiKeyException;
import org.juliantovar.arquitecturahexagonal.domain.exception.WeatherNotFoundException;
import org.juliantovar.arquitecturahexagonal.domain.exception.WeatherServiceUnavailableException;
import org.juliantovar.arquitecturahexagonal.shared.ErrorMessages;

public class OpenWeatherResponseExceptionMapper implements ResponseExceptionMapper<RuntimeException> {


    @Override
    public RuntimeException toThrowable(Response response) {
        return switch(response.getStatus()) {

            case 401 ->
                    new InvalidApiKeyException(ErrorMessages.INVALID_API_KEY);

            case 404 ->
                    new WeatherNotFoundException(ErrorMessages.WEATHER_NOT_FOUND);

            case 429, 500, 502, 503, 504 ->
                    new WeatherServiceUnavailableException(ErrorMessages.WEATHER_SERVICE_UNAVAILABLE);

            default ->
                    new RuntimeException("Unexpected error calling OpenWeather");
        };
    }
}
