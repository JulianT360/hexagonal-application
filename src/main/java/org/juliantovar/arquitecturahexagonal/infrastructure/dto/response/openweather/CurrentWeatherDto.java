package org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.openweather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

/**
 * DTO para el clima actual.
 *
 * @param temp          Temperatura
 * @param feelsLike     Sensación térmica
 * @param pressure      Presión atmosférica
 * @param humidity      Húmedad
 * @param windSpeed     Velocidad del Viento
 * @param weather       Clima
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
@Builder
public record CurrentWeatherDto(
        Double temp,
        @JsonProperty("feels_like")
        Double feelsLike,
        Integer pressure,
        Double humidity,
        @JsonProperty("wind_speed")
        Double windSpeed,
        List<WeatherDescriptionDto> weather
) {
}
