package org.juliantovar.arquitecturahexagonal.domain.port;

import io.smallrye.mutiny.Uni;
import org.juliantovar.arquitecturahexagonal.domain.model.Weather;

/**
 * Port for the weather client.
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
public interface WeatherClientPort {

    Uni<Weather> getCurrentWeather(Double latitude,Double longitude);

}
