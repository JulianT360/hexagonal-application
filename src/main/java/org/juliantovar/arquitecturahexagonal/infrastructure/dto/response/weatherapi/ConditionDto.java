package org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.weatherapi;

/**
 * Data transfer object for condition description for current weather.
 *
 * @param text  Description of the current weather
 *
 * @author Julian Tovar
 * @since 10/07/2026
 */
public record ConditionDto(
        String text) {
}
