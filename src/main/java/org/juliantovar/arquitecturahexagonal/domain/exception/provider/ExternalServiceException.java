package org.juliantovar.arquitecturahexagonal.domain.exception.provider;

import org.juliantovar.arquitecturahexagonal.domain.exception.WeatherException;

/**
 * Exception class for external service exceptions
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
public class ExternalServiceException extends WeatherException {

    public ExternalServiceException(String message) {
        super(message);
    }

}
