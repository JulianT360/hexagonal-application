package org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.openweather;

import lombok.Builder;

/**
 * Objeto de respuesta para la información del clima
 *
 * @param latitude      Latitud
 * @param longitude     Longitud
 * @param timezone      Zona horaria
 * @param temperature   Temperatura
 * @param feelsLike     Sensación Térmica
 * @param pressure      Presión Atmósferica
 * @param humidity      Húmedad
 * @param windSpeed     Velocidad del Viento
 * @param description   Descripcion del clima
 *
 * @author Julian Tovar
 * @since 09/07/2026
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
