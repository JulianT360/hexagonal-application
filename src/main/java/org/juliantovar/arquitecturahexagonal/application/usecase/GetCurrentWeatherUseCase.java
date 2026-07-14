package org.juliantovar.arquitecturahexagonal.application.usecase;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.juliantovar.arquitecturahexagonal.domain.exception.client.InvalidCoordinatesException;
import org.juliantovar.arquitecturahexagonal.application.command.GetCurrentWeatherCommand;
import org.juliantovar.arquitecturahexagonal.domain.model.Weather;
import org.juliantovar.arquitecturahexagonal.domain.port.WeatherClientPort;
import org.juliantovar.arquitecturahexagonal.infrastructure.qualifiers.WeatherApiProvider;
import org.juliantovar.arquitecturahexagonal.shared.ErrorMessages;

/**
 * Caso de uso para obtener el clima actual
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
@ApplicationScoped
public class GetCurrentWeatherUseCase {

    private final WeatherClientPort weatherClientPort;

    public GetCurrentWeatherUseCase(@WeatherApiProvider WeatherClientPort weatherClientPort) {
        this.weatherClientPort = weatherClientPort;
    }

    /**
     * Execute the get current weather function.
     *
     * @param command   Object with coordinates of the location.
     * @return Weather data obtained {@link Uni<Weather>}.
     */
    public Uni<Weather> execute(GetCurrentWeatherCommand command) {

        if (command == null || command.latitude() == null) {
            throw new InvalidCoordinatesException(ErrorMessages.REQUIRED_LATITUDE);
        }

        if (command.longitude() == null) {
            throw new InvalidCoordinatesException(ErrorMessages.REQUIRED_LONGITUDE);
        }

        if (command.latitude() < -90 || command.latitude() > 90) {
            throw new InvalidCoordinatesException(ErrorMessages.INVALID_LATITUDE);
        }

        if (command.longitude() < -180 || command.longitude() > 180) {
            throw new InvalidCoordinatesException(ErrorMessages.INVALID_LONGITUDE);
        }

        return weatherClientPort.getCurrentWeather(command.latitude(), command.longitude());
    }

}
