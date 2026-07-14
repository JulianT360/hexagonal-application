package org.juliantovar.arquitecturahexagonal.infrastructure.exception;

/**
 * Enumerator for error codes.
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
public enum ErrorCode {
    INVALID_COORDINATES,
    WEATHER_NOT_FOUND,
    WEATHER_SERVICE_UNAVAILABLE,
    WEATHER_RATE_LIMIT_REACHED,
    INVALID_API_KEY,
    INTERNAL_SERVER_ERROR
}
