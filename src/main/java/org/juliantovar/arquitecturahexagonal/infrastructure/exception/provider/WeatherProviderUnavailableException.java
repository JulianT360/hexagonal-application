package org.juliantovar.arquitecturahexagonal.infrastructure.exception.provider;

import org.juliantovar.arquitecturahexagonal.infrastructure.exception.ExternalServiceException;

/**
 * Excepción para escenario de proveedor no disponible.
 */
public class WeatherProviderUnavailableException extends ExternalServiceException {

  /**
   * Constructor.
   *
   * @param message mensaje de error
   */
  public WeatherProviderUnavailableException(String message) {
    super(message);
  }

}
