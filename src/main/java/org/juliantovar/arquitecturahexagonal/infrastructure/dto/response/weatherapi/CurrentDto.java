package org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.weatherapi;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data transfer object for current weather data.
 *
 * @param temperature   Temperature in Celsius
 * @param windSpeed     Wind speed in Kilometers per hour
 * @param pressure      Pressure in milibar
 * @param humidity      Humidity percent
 * @param feelsLike     Temperature feels like in Celsius
 * @param condition     Description of the current weather
 *
 * @author Julian Tovar
 * @since 10/07/2026
 */
public record CurrentDto(
        @JsonProperty("temp_c")
        Double temperature,
        @JsonProperty("wind_kph")
        Double windSpeed,
        @JsonProperty("pressure_mb")
        Integer pressure,
        int humidity,
        @JsonProperty("feelslike_c")
        Double feelsLike,
        ConditionDto condition
) {
}
