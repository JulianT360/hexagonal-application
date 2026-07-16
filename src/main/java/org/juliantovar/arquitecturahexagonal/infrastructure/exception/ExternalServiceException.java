package org.juliantovar.arquitecturahexagonal.infrastructure.exception;

import org.juliantovar.arquitecturahexagonal.domain.exception.WeatherException;

/**
 * Excepción para excepciones externas del sistema (proveedor del api).
 */
public class ExternalServiceException extends WeatherException {

  /**
   * Constructor.
   *
   * @param message mensaje
   */
  public ExternalServiceException(String message) {
    super(message);
  }

}
