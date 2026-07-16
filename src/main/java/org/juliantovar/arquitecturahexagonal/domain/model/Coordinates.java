package org.juliantovar.arquitecturahexagonal.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Coordinates {

    private Double latitude;
    private Double longitude;

}
