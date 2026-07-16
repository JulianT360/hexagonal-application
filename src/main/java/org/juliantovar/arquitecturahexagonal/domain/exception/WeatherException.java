package org.juliantovar.arquitecturahexagonal.domain.exception;

/**
 * Excepción genérica de negocio.
 */
public class WeatherException extends RuntimeException {

  /**
   * Constructor.
   *
   * @param message mensaje
   */
  public WeatherException(String message) {
    super(message);
  }
}