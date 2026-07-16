package org.juliantovar.arquitecturahexagonal.infrastructure.exception.provider;

import org.juliantovar.arquitecturahexagonal.infrastructure.exception.ExternalServiceException;

/**
 * Excepción para escenario de limite de consumo alcanzado
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
public class ProviderRateLimitException extends ExternalServiceException {
    public ProviderRateLimitException(String message) {
        super(message);
    }
}
