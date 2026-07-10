package org.juliantovar.arquitecturahexagonal.application.usecase;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.juliantovar.arquitecturahexagonal.domain.exception.InvalidCoordinatesException;
import org.juliantovar.arquitecturahexagonal.application.command.GetCurrentWeatherCommand;
import org.juliantovar.arquitecturahexagonal.domain.model.Weather;
import org.juliantovar.arquitecturahexagonal.domain.port.WeatherClientPort;

/**
 * Get current weather use case
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
@ApplicationScoped
public class GetCurrentWeatherUseCase {

    private final WeatherClientPort weatherClientPort;

    public GetCurrentWeatherUseCase(WeatherClientPort weatherClientPort) {
        this.weatherClientPort = weatherClientPort;
    }

    /**
     * Execute the get current weather function.
     *
     * @param command   Object with coordinates of the location.
     * @return Weather data obtained {@link Uni<Weather>}.
     */
    public Uni<Weather> execute(GetCurrentWeatherCommand command) {

        if (command.latitude() < -90 || command.latitude() > 90) {
            throw new InvalidCoordinatesException("La latitud debe ser un valor entre -90 y 90");
        }

        if (command.longitude() < -180 || command.longitude() > 180) {
            throw new InvalidCoordinatesException("La longitud debe ser un valor entre -180 y 180");
        }

        return weatherClientPort.getCurrentWeather(command.latitude(), command.longitude());
    }

}
