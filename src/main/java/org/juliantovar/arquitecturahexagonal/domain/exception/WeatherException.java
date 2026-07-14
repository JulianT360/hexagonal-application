package org.juliantovar.arquitecturahexagonal.domain.exception;

/**
 * Excepción genérica de la api
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
public class WeatherException extends RuntimeException {

    public WeatherException(String message) {
        super(message);
    }

}
