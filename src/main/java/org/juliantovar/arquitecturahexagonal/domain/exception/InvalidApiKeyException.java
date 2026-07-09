package org.juliantovar.arquitecturahexagonal.domain.exception;

/**
 * Exception for invalid api key scenario.
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
public class InvalidApiKeyException extends WeatherException {

    public InvalidApiKeyException(String message) {
        super(message);
    }

}
