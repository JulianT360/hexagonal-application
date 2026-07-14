package org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.weatherapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

/**
 * DTO para la información del clima actual
 *
 * @param temperature   Temperatura en Celsius
 * @param windSpeed     Velocidad del viento en kilómetros por hora
 * @param pressure      Presión en milibares
 * @param humidity      Humedad en porcentaje
 * @param feelsLike     Sensación térmica en Celsius
 * @param condition     Descripción del clima actual
 *
 * @author Julian Tovar
 * @since 10/07/2026
 */
@Builder
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
