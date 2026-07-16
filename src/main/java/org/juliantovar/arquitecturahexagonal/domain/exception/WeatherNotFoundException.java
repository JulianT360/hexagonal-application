package org.juliantovar.arquitecturahexagonal.domain.exception;

/**
 * Excepción para escenario de información del clima no encontrada.
 */
public class WeatherNotFoundException extends WeatherException {

  /**
   * Constructor.
   *
   * @param message mensaje
   */
  public WeatherNotFoundException(String message) {
    super(message);
  }
}
