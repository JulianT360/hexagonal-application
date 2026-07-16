package org.juliantovar.arquitecturahexagonal.infrastructure.exception.provider;

import org.juliantovar.arquitecturahexagonal.infrastructure.exception.ExternalServiceException;

/**
 * Excepción para escenario de api key no válida.
 */
public class InvalidApiKeyException extends ExternalServiceException {

  /**
   * Constructor.
   *
   * @param message mensaje de error
   */
  public InvalidApiKeyException(String message) {
    super(message);
  }

}
