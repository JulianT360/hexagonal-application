package org.juliantovar.arquitecturahexagonal.domain.exception;

public class WeatherServiceUnavailableException extends WeatherException {

    public WeatherServiceUnavailableException(String message) {
        super(message);
    }

}
