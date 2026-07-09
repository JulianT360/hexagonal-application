package org.juliantovar.arquitecturahexagonal.application.command;

/**
 * Command to get the current weather.
 *
 * @param latitude      Latitude of the location.
 * @param longitude     Longitude of the location.
 * @author Julian Tovar
 * @since 09/07/2026
 */
public record GetCurrentWeatherCommand (Double latitude,
                                        Double longitude) {
}
