package org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.openweather;

import lombok.Builder;

/**
 * Data transfer object for response open weather.
 *
 * @param lat       Latitude
 * @param lon       Longitude
 * @param timezone  Timezone
 * @param current   {@link CurrentWeatherDto}
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
