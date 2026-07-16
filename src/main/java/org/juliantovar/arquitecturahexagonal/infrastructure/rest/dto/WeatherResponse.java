package org.juliantovar.arquitecturahexagonal.infrastructure.rest.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Builder;

/**
 * Objeto de respuesta para la información del clima.
 */
@Builder
@RegisterForReflection
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
