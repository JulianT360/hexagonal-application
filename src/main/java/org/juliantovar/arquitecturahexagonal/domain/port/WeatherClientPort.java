package org.juliantovar.arquitecturahexagonal.domain.port;

import io.smallrye.mutiny.Uni;
import org.juliantovar.arquitecturahexagonal.domain.model.Weather;

/**
 * Puerto para el cliente del proveedor del clima
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
public interface WeatherClientPort {

    Uni<Weather> getCurrentWeather(Double latitude,Double longitude);

}
