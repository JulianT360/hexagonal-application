package org.juliantovar.arquitecturahexagonal.domain.model;

import lombok.Builder;

/**
 * Clase de dominio para las coordenadas
 *
 * @param latitude      Latitud de la ubicación
 * @param longitude     Longitud de la ubicación
 *
 * @author Julian Tovar
 * @since 10/07/2026
 */
@Builder
public record Coordinates(
        Double latitude,
        Double longitude) {

    public String asQuery() {
        return latitude + "," + longitude;
    }
}
