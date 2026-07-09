package org.juliantovar.arquitecturahexagonal.domain.model;

public record GetCurrentWeatherCommand (Double latitude,
                                        Double longitude) {
}
