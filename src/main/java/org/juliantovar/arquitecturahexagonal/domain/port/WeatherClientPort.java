package org.juliantovar.arquitecturahexagonal.domain.port;

import io.smallrye.mutiny.Uni;
import org.juliantovar.arquitecturahexagonal.domain.model.Weather;

public interface WeatherClientPort {

    Uni<Weather> getCurrentWeather(Double latitude,Double longitude);

}
