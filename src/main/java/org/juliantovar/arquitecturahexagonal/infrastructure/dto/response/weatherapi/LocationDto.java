package org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.weatherapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

/**
 * Data transfer object for weather api response.
 *
 * @param name      City name
 * @param region    State Name
 * @param country   Country name
 * @param latitude  Latitude of the location
 * @param longitude Longitude of the location
 * @param timezone  Timezone of the location
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
@Builder
public record LocationDto(
        String name,
        String region,
        String country,
        @JsonProperty("lat")
        Double latitude,
        @JsonProperty("lon")
        Double longitude,
        @JsonProperty("tz_id")
        String timezone
) {
}
