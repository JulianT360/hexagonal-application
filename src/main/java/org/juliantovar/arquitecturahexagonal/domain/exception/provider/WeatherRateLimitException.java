package org.juliantovar.arquitecturahexagonal.domain.exception.provider;

import org.juliantovar.arquitecturahexagonal.domain.exception.WeatherException;

/**
 * Excepción para escenario de limite de consumo alcanzado
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
public class WeatherRateLimitException extends WeatherException {
    public WeatherRateLimitException(String message) {
        super(message);
    }
}
