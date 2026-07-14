package org.juliantovar.arquitecturahexagonal.domain.exception.provider;

/**
 * Excepción para escenario de proveedor no disponible
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
public class WeatherProviderUnavailableException extends ExternalServiceException {
    public WeatherProviderUnavailableException(String message) {
        super(message);
    }
}
