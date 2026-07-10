package org.juliantovar.arquitecturahexagonal.domain.model;

import lombok.Builder;

/**
 * Base object weather for app domain.
 *
 * @param latitude      Latitude of the location.
 * @param longitude     Longitude of the location.
 * @param timezone      Timezone of the location.
 * @param temperature   Temperature of the location.
 * @param feelsLike     Feels Like of the location.
 * @param pressure      Atmospheric pressure of the location.
 * @param humidity      Humidity percent of the location.
 * @param windSpeed     Wind Speed of the location.
 * @param description   Description about the weather of the location.
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
@Builder
public record Weather (
        Double latitude,
        Double longitude,
        String timezone,
        Double temperature,
        Double feelsLike,
        Integer pressure,
        Integer humidity,
        Double windSpeed,
        String description
) {}
