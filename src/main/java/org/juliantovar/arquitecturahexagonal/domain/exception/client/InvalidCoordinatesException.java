package org.juliantovar.arquitecturahexagonal.domain.exception.client;

/**
 * Excepción para escenario de coordenadas inválidas
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
public class InvalidCoordinatesException extends ClientException{

    public InvalidCoordinatesException(String message) {
        super(message);
    }

}
