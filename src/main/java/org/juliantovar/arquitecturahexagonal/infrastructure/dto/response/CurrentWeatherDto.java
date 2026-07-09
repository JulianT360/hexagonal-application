package org.juliantovar.arquitecturahexagonal.infrastructure.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Data transfer object of the current weather.
 *
 * @param temp          Temperature
 * @param feelsLike     Feels like
 * @param pressure      Pressure
 * @param humidity      Humidity
 * @param windSpeed     Wind speed
 * @param weather       Weather
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
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
