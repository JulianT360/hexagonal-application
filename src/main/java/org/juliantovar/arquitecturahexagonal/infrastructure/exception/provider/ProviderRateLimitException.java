package org.juliantovar.arquitecturahexagonal.infrastructure.exception.provider;

import org.juliantovar.arquitecturahexagonal.infrastructure.exception.ExternalServiceException;

/**
 * Excepción para escenario de limite de consumo alcanzado.
 */
public class ProviderRateLimitException extends ExternalServiceException {

  /**
   * Constructor.
   *
   * @param message mensaje de error
   */
  public ProviderRateLimitException(String message) {
    super(message);
  }
}
