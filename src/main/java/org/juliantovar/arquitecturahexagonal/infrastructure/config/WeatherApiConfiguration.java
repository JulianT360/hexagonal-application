package org.juliantovar.arquitecturahexagonal.infrastructure.config;

import io.smallrye.config.ConfigMapping;

/**
 * Interfaz de configuración para el proveedor WeatherAPI
 *
 * @author Julian Tovar
 * @since 10/07/2026
 */
@ConfigMapping(prefix = "out.weatherapi.config")
public interface WeatherApiConfiguration {
    String apiKey();
    Client client();

    interface Client {
        String language();
    }
}
