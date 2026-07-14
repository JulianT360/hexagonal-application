package org.juliantovar.arquitecturahexagonal.domain.exception.client;

/**
 * Excepción para escenario de información del clima no encontrada
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
public class WeatherNotFoundException extends ClientException {

    public WeatherNotFoundException(String message) {
        super(message);
    }

}
