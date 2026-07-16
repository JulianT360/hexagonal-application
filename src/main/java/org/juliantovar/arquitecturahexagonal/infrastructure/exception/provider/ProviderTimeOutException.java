package org.juliantovar.arquitecturahexagonal.infrastructure.exception.provider;

import org.juliantovar.arquitecturahexagonal.infrastructure.exception.ExternalServiceException;

/**
 * Excepcion para tiempo de espera del proveedor.
 */
public class ProviderTimeOutException extends ExternalServiceException {

  /**
   * Constructor.
   *
   * @param message mensaje de error
   */
  public ProviderTimeOutException(String message) {
    super(message);
  }
}
