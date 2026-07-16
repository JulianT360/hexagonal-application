package org.juliantovar.arquitecturahexagonal.domain.exception;

/**
 * Excepción para escenario de coordenadas inválidas.
 */
public class InvalidCoordinatesException extends WeatherException {

  /**
   * Constructor .
   *
   * @param message mensaje
   */
  public InvalidCoordinatesException(String message) {
    super(message);
  }

}
