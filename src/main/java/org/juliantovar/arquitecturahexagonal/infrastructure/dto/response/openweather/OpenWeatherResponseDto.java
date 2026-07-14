package org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.openweather;

import lombok.Builder;

/**
 * DTO para la respuesta de OpenWeather
 *
 * @param lat       Latitud
 * @param lon       Longitud
 * @param timezone  Zona horaria
 * @param current   Clima actual: {@link CurrentWeatherDto}
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
@Builder
public record OpenWeatherResponseDto(
        Double lat,
        Double lon,
        String timezone,
        CurrentWeatherDto current
) {
}
