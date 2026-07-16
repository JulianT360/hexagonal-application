package org.juliantovar.arquitecturahexagonal.infrastructure.exception.provider;

import org.juliantovar.arquitecturahexagonal.infrastructure.exception.ExternalServiceException;

/**
 * Excepción para excepciones no esperadas del proveedor.
 */
public class UnexpectedProviderException extends ExternalServiceException {

  /**
   * Constructor.
   *
   * @param message mensaje de error
   */
  public UnexpectedProviderException(String message) {
    super(message);
  }
}
