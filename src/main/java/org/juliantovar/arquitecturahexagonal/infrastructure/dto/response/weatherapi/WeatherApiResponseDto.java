package org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.weatherapi;

import lombok.Builder;

/**
 * Data transfer objecto for weather api response.
 *
 * @param location  Object {@link LocationDto} with location data.
 * @param current   Object {@link CurrentDto} with current weather data.
 *
 * @author Julian Tovar
 * @since 10/07/2026
 */
@Builder
public record WeatherApiResponseDto(
        LocationDto location,
        CurrentDto current) {
}
