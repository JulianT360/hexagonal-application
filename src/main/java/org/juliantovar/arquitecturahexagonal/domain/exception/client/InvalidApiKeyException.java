package org.juliantovar.arquitecturahexagonal.domain.exception.client;

/**
 * Excepción para escenario de api key no válida
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
public class InvalidApiKeyException extends ClientException {

    public InvalidApiKeyException(String message) {
        super(message);
    }

}
