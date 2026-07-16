package org.juliantovar.arquitecturahexagonal.domain.model;

import lombok.Builder;
import lombok.Data;

/**
 * Modelo de dominio para coordenadas.
 */
@Data
@Builder
public class Coordinates {

  private Double latitude;
  private Double longitude;

}
