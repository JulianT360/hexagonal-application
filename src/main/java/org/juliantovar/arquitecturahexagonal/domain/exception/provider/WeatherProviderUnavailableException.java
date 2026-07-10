package org.juliantovar.arquitecturahexagonal.domain.exception.provider;

/**
 * Exception for provider unavailable exceptions
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
public class WeatherProviderUnavailableException extends ExternalServiceException {
    public WeatherProviderUnavailableException(String message) {
        super(message);
    }
}
