package org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.weatherapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

/**
 * DTO para la respuesta de WeatherAPI
 *
 * @param name      Nombre de la ciudad
 * @param region    Nombre del estado
 * @param country   Nombre del país
 * @param latitude  Latitud de la ubicación
 * @param longitude Longitud de la ubicación
 * @param timezone  Zona horaria de la ubicación
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
@Builder
public record LocationDto(
        String name,
        String region,
        String country,
        @JsonProperty("lat")
        Double latitude,
        @JsonProperty("lon")
        Double longitude,
        @JsonProperty("tz_id")
        String timezone
) {
}
