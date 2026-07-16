package org.juliantovar.arquitecturahexagonal.application.ports.in;

import io.smallrye.mutiny.Uni;
import org.juliantovar.arquitecturahexagonal.domain.model.Coordinates;
import org.juliantovar.arquitecturahexagonal.domain.model.Weather;

/**
 * Caso de uso para obtener el clima actual.
 */
@FunctionalInterface
public interface GetCurrentWeatherUseCase {
  /**
   * Obtiene el clima actual.
   *
   * @param coordinates Coordenadas de la ubicacion
   * @return clima actual
   */
  Uni<Weather> getCurrentWeather(Coordinates coordinates);
}
