package org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.openweather;

import lombok.Builder;

/**
 * DTO para la descripción del clima
 *
 * @param description   Descripción del clima
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
@Builder
public record WeatherDescriptionDto(
        String description
) {
}
