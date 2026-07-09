package org.juliantovar.arquitecturahexagonal.infrastructure.dto.response;

/**
 * Data transfer object for weather description.
 *
 * @param description   Description of the weather.
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
public record WeatherDescriptionDto(
        String description
) {
}
