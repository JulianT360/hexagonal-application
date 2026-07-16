package org.juliantovar.arquitecturahexagonal.application.service;

import io.smallrye.common.constraint.NotNull;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import org.juliantovar.arquitecturahexagonal.application.ports.in.GetCurrentWeatherUseCase;
import org.juliantovar.arquitecturahexagonal.application.ports.out.WeatherProviderPort;
import org.juliantovar.arquitecturahexagonal.domain.model.Coordinates;
import org.juliantovar.arquitecturahexagonal.domain.model.Weather;

@ApplicationScoped
@AllArgsConstructor
public class WeatherService implements GetCurrentWeatherUseCase {

    private final WeatherProviderPort weatherProvider;

    @Override
    public Uni<Weather> getCurrentWeather(@NotNull Coordinates coordinates) {
        return weatherProvider.getCurrentWeather(coordinates);
    }
}
