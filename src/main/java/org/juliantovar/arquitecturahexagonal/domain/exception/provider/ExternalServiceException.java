package org.juliantovar.arquitecturahexagonal.domain.exception.provider;

import org.juliantovar.arquitecturahexagonal.domain.exception.WeatherException;

/**
 * Excepción para excepciones externas del sistema (proveedor del api)
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
public class ExternalServiceException extends WeatherException {

    public ExternalServiceException(String message) {
        super(message);
    }

}
