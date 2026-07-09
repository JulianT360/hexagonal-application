package org.juliantovar.arquitecturahexagonal.application.command;

public record GetCurrentWeatherCommand (Double latitude,
                                        Double longitude) {
}
