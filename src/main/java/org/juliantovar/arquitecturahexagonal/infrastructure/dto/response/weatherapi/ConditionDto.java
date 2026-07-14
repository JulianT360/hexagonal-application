package org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.weatherapi;

import lombok.Builder;

/**
 * Data transfer object for condition description for current weather.
 *
 * @param text  Description of the current weather
 *
 * @author Julian Tovar
 * @since 10/07/2026
 */
@Builder
public record ConditionDto(
        String text) {
}
