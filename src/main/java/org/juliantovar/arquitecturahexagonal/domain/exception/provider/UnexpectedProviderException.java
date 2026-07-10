package org.juliantovar.arquitecturahexagonal.domain.exception.provider;

/**
 * Exception class for unexpected provider exceptions
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
public class UnexpectedProviderException extends ExternalServiceException {
    public UnexpectedProviderException(String message) {
        super(message);
    }
}
