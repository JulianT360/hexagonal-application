package org.juliantovar.arquitecturahexagonal.infrastructure.exception.provider;

import org.juliantovar.arquitecturahexagonal.infrastructure.exception.ExternalServiceException;

/**
 * Excepción para excepciones no esperadas del proveedor
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
public class UnexpectedProviderException extends ExternalServiceException {
    public UnexpectedProviderException(String message) {
        super(message);
    }
}
