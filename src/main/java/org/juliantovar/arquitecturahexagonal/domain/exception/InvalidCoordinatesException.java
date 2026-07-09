package org.juliantovar.arquitecturahexagonal.domain.exception;

public class InvalidCoordinatesException extends WeatherException{

    public InvalidCoordinatesException(String message) {
        super(message);
    }

}
