package org.juliantovar.arquitecturahexagonal.infrastructure.config;

import io.smallrye.config.ConfigMapping;

/**
 * Interfaz de configuración para el proveedor WeatherAPI.
 */
@ConfigMapping(prefix = "out.weatherapi.config")
public interface WeatherApiConfiguration {

  /**
   * Api key del proveedor.
   *
   * @return api key
   */
  String apiKey();

  /**
   * Informacion para consumir el proveedor.
   *
   * @return parametros de consulta
   */
  Client client();

  /**
   * Interaz que alberga los parametros adicionales para consumir al proveedor.
   */
  interface Client {
    String language();
  }
}
