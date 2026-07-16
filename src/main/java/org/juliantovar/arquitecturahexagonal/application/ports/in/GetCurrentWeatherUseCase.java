package org.juliantovar.arquitecturahexagonal.application.ports.in;

import io.smallrye.mutiny.Uni;
import org.juliantovar.arquitecturahexagonal.domain.model.Coordinates;
import org.juliantovar.arquitecturahexagonal.domain.model.Weather;

@FunctionalInterface
public interface GetCurrentWeatherUseCase {

    Uni<Weather> getCurrentWeather(Coordinates coordinates);

}
