package org.juliantovar.arquitecturahexagonal.domain.exception;

public class InvalidApiKeyException extends WeatherException {

    public InvalidApiKeyException(String message) {
        super(message);
    }

}
