package org.juliantovar.arquitecturahexagonal.domain.exception;

/**
 * Exception for weather service unavailable scenario.
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
public class WeatherServiceUnavailableException extends WeatherException {

    public WeatherServiceUnavailableException(String message) {
        super(message);
    }

}
