package org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.weatherapi;

/**
 * Data transfer objecto for weather api response.
 *
 * @param location  Object {@link LocationDto} with location data.
 * @param current   Object {@link CurrentDto} with current weather data.
 *
 * @author Julian Tovar
 * @since 10/07/2026
 */
public record WeatherApiResponseDto(
        LocationDto location,
        CurrentDto current) {
}
