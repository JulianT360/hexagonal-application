package org.juliantovar.arquitecturahexagonal.infrastructure.config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "openweather")
public interface OpenWeatherConfiguration {
    String apiKey();
    Client client();

    interface Client {
        String units();
        String language();
    }
}
