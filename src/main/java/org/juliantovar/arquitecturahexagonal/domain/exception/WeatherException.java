package org.juliantovar.arquitecturahexagonal.domain.exception;

/**
 * Generic exception for weather domain.
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
public class WeatherException extends RuntimeException {

    public WeatherException(String message) {
        super(message);
    }

}
