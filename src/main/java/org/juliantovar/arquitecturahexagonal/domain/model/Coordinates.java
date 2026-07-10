package org.juliantovar.arquitecturahexagonal.domain.model;

/**
 * Domain class for coordinates data
 *
 * @param latitude      Latitude of the location
 * @param longitude     Longitude of the location
 *
 * @author Julian Tovar
 * @since 10/07/2026
 */
public record Coordinates(
        Double latitude,
        Double longitude) {

    public String asQuery() {
        return latitude + "," + longitude;
    }
}
