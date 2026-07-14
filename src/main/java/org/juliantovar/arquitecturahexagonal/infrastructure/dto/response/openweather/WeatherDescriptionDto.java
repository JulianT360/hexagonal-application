package org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.openweather;

import lombok.Builder;

/**
 * Data transfer object for weather description.
 *
 * @param description   Description of the weather.
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
@Builder
public record WeatherDescriptionDto(
        String description
) {
}
