package org.juliantovar.arquitecturahexagonal.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Weather {

    private Coordinates coordinates;
    private String timezone;
    private Double temperature;
    private Double feelsLike;
    private Integer pressure;
    private Integer humidity;
    private Double windSpeed;
    private String description;

}
