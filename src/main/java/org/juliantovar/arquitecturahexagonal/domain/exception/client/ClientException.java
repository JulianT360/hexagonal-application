package org.juliantovar.arquitecturahexagonal.domain.exception.client;

import org.juliantovar.arquitecturahexagonal.domain.exception.WeatherException;

/**
 * Excepción para las excepciones del cliente
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
public class ClientException extends WeatherException {
    public ClientException(String message) {
        super(message);
    }
}
