package org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.weatherapi;

import lombok.Builder;

/**
 * DTO para la respuesta de WeatherAPI
 *
 * @param location  Objeto {@link LocationDto} con la información de la ubicación.
 * @param current   Objeto {@link CurrentDto} con los datos del clima actual.
 *
 * @author Julian Tovar
 * @since 10/07/2026
 */
@Builder
public record WeatherApiResponseDto(
        LocationDto location,
        CurrentDto current) {
}
