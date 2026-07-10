package org.juliantovar.arquitecturahexagonal.infrastructure.config;

import io.smallrye.config.ConfigMapping;

/**
 * Configuration interaface to consume Weather API.
 *
 * @author Julian Tovar
 * @since 10/07/2026
 */
@ConfigMapping(prefix = "weatherapi")
public interface WeatherApiConfiguration {
    String apiKey();
    Client client();

    interface Client {
        String language();
    }
}
