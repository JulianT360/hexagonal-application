package org.juliantovar.arquitecturahexagonal.application.command;

import lombok.Builder;

/**
 * Commando para consultar el clima actual.
 *
 * @param latitude      Latitud de la ubicación.
 * @param longitude     Longitud de la ubicación.
 * @author Julian Tovar
 * @since 09/07/2026
 */
@Builder
public record GetCurrentWeatherCommand (Double latitude,
                                        Double longitude) {
}
