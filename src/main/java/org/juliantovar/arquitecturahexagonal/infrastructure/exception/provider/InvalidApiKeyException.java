package org.juliantovar.arquitecturahexagonal.infrastructure.exception.provider;

import org.juliantovar.arquitecturahexagonal.infrastructure.exception.ExternalServiceException;

/**
 * Excepción para escenario de api key no válida
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
public class InvalidApiKeyException extends ExternalServiceException {

    public InvalidApiKeyException(String message) {
        super(message);
    }

}
