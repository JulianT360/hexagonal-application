package org.juliantovar.arquitecturahexagonal.domain.exception.provider;

import org.juliantovar.arquitecturahexagonal.domain.exception.WeatherException;

/**
 * Exception class for rate limit exception
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
public class WeatherRateLimitException extends WeatherException {
    public WeatherRateLimitException(String message) {
        super(message);
    }
}
