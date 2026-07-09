package org.juliantovar.arquitecturahexagonal.infrastructure.dto.response;

public record OpenWeatherResponseDto(
        Double lat,
        Double lon,
        String timezone,
        CurrentWeatherDto current
) {
}
