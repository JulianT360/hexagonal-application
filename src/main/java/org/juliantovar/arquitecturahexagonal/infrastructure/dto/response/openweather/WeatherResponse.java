package org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.openweather;

import lombok.Builder;

/**
 * Response object for weather data.
 *
 * @param latitude      Latitude
 * @param longitude     Longitude
 * @param timezone      Timezone
 * @param temperature   Temperature
 * @param feelsLike     Feels Like
 * @param pressure      Pressure
 * @param humidity      Humidity
 * @param windSpeed     Wind Speed
 * @param description   Description
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
@Builder
public record WeatherResponse(
        Double latitude,
        Double longitude,
        String timezone,
        Double temperature,
        Double feelsLike,
        Integer pressure,
        Integer humidity,
        Double windSpeed,
        String description
) {
}
