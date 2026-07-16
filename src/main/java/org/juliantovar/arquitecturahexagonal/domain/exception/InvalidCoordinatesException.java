package org.juliantovar.arquitecturahexagonal.domain.exception;

/**
 * Excepción para escenario de coordenadas inválidas
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
public class InvalidCoordinatesException extends WeatherException {

    public InvalidCoordinatesException(String message) {
        super(message);
    }

}
