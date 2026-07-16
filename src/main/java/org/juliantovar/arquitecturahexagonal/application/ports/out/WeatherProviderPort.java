package org.juliantovar.arquitecturahexagonal.application.ports.out;

import io.smallrye.mutiny.Uni;
import org.juliantovar.arquitecturahexagonal.domain.model.Coordinates;
import org.juliantovar.arquitecturahexagonal.domain.model.Weather;

@FunctionalInterface
public interface WeatherProviderPort {

    Uni<Weather> getCurrentWeather(Coordinates coordinates);

}
