package org.juliantovar.arquitecturahexagonal.infrastructure.adapter;

import io.smallrye.mutiny.Uni;
import org.juliantovar.arquitecturahexagonal.domain.model.Weather;
import org.juliantovar.arquitecturahexagonal.domain.port.WeatherClientPort;

public class OpenWeatherAdapter implements WeatherClientPort {

    @Override
    public Uni<Weather> getCurrentWeather(Double latitude, Double longitude) {
        return null;
    }
}
