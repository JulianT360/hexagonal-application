package org.juliantovar.arquitecturahexagonal.infrastructure.rest.dto;

import lombok.Builder;

/**
 * Objeto de respuesta para la información del clima.
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
