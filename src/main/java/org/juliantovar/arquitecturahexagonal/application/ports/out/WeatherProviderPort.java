package org.juliantovar.arquitecturahexagonal.application.ports.out;

import io.smallrye.mutiny.Uni;
import org.juliantovar.arquitecturahexagonal.domain.model.Coordinates;
import org.juliantovar.arquitecturahexagonal.domain.model.Weather;

/**
 * Puerto de entrada para el proveedor del clima.
 */
@FunctionalInterface
public interface WeatherProviderPort {

  /**
   * Obtiene el clima actual.
   *
   * @param coordinates Coordenadas de la ubicacion
   * @return informacion del clima actual
   */
  Uni<Weather> getCurrentWeather(Coordinates coordinates);

}
