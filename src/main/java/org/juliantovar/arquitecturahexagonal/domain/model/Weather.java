package org.juliantovar.arquitecturahexagonal.domain.model;

/**
 * Base object weather for app domain.
 *
 * @param coordinates      Latitude and longitude of the location.
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
public record Weather (
        Coordinates coordinates,
        String timezone,
        Double temperature,
        Double feelsLike,
        Integer pressure,
        Integer humidity,
        Double windSpeed,
        String description
) {}
