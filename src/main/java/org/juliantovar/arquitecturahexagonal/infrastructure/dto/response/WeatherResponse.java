package org.juliantovar.arquitecturahexagonal.infrastructure.dto.response;

import lombok.Builder;

@Builder
public record WeatherResponse(
        Double latitude,
        Double longitude,
        Double timezone,
        Double temperature,
        Double feelsLike,
        Integer pressure,
        Integer humidity,
        Double windSpeed,
        String description
) {
}
