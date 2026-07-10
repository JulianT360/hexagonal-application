package org.juliantovar.arquitecturahexagonal.domain.exception.provider;

/**
 * Exception class for time out exceptions
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
public class WeatherTimeOutException extends ExternalServiceException{
    public WeatherTimeOutException(String message) {
        super(message);
    }
}
