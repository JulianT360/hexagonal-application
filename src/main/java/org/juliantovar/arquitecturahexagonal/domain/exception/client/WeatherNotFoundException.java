package org.juliantovar.arquitecturahexagonal.domain.exception.client;

/**
 * Exception for weather data not found scenario.
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
public class WeatherNotFoundException extends ClientException {

    public WeatherNotFoundException(String message) {
        super(message);
    }

}
