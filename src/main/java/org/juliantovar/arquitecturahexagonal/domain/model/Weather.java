package org.juliantovar.arquitecturahexagonal.domain.model;

import lombok.Builder;

@Builder
public record Weather (
        Double latitude,
        Double longitude,
        String timezone,
        Double temperature,
        Double feelsLike,
        Integer pressure,
        Integer humidity,
        Double windSpeed,
        String description
) {}
