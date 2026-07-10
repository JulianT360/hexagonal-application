package org.juliantovar.arquitecturahexagonal.domain.exception.client;

import org.juliantovar.arquitecturahexagonal.domain.exception.WeatherException;

/**
 * Exception class for client exceptions
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
public class ClientException extends WeatherException {
    public ClientException(String message) {
        super(message);
    }
}
