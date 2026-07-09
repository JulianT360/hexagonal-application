package org.juliantovar.arquitecturahexagonal.infrastructure.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CurrentWeatherDto(
        Double temp,
        @JsonProperty("feels_like")
        Double feelsLike,
        Double pressure,
        Double humidity,
        @JsonProperty("wind_speed")
        Double windSpeed,
        List<WeatherDescriptionDto> weather
) {
}
