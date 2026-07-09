package org.juliantovar.arquitecturahexagonal.infrastructure.config;

import io.smallrye.config.ConfigMapping;

/**
 * Configuration of the open weather api.
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
@ConfigMapping(prefix = "openweather")
public interface OpenWeatherConfiguration {
    String apiKey();
    Client client();

    interface Client {
        String units();
        String language();
    }
}
