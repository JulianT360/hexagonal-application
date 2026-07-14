package org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.weatherapi;

import lombok.Builder;

/**
 * DTO para la descripción de la condición del clima actual
 *
 * @param text  Descripción del clima actual
 *
 * @author Julian Tovar
 * @since 10/07/2026
 */
@Builder
public record ConditionDto(
        String text) {
}
