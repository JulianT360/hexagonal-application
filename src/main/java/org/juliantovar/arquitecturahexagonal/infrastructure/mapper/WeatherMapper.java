package org.juliantovar.arquitecturahexagonal.infrastructure.mapper;

import org.juliantovar.arquitecturahexagonal.domain.model.Weather;
import org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.openweather.OpenWeatherResponseDto;
import org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.openweather.WeatherDescriptionDto;
import org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.openweather.WeatherResponse;
import org.juliantovar.arquitecturahexagonal.shared.Constants;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

/**
 * Mapper para la respuesta del clima de OpenWeather
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public interface WeatherMapper {
    @Mapping(target = "coordinates.latitude", source = "lat")
    @Mapping(target = "coordinates.longitude", source = "lon")
    @Mapping(target = "temperature", source = "current.temp")
    @Mapping(target = "feelsLike", source = "current.feelsLike")
    @Mapping(target = "pressure", source = "current.pressure")
    @Mapping(target = "humidity", source = "current.humidity")
    @Mapping(target = "windSpeed", source = "current.windSpeed")
    @Mapping(target = "description", source = "current.weather")
    Weather toDomain(OpenWeatherResponseDto response);

    /**
     * Metodo para establecer por defecto el valor de la descripción del clima
     *
     * @param weather   Lista de {@link WeatherDescriptionDto}
     * @return cadena de texto con la descripción del primer elemento del clima o en su defecto un string por default
     */
    default String mapWeatherDescription(
            List<WeatherDescriptionDto> weather) {
        if(weather == null || weather.isEmpty()) {
            return Constants.UNKNOWN_DESCRIPTION;
        }

        return weather.getFirst().description();
    }

    /**
     * Metodo para generar un objeto {@link WeatherResponse} de {@link Weather}
     *
     * @param weather   Objeto que incluye la informacion del clima
     * @return Response Objeto con la información obtenida sobre el clima y las coordenadas
     */
    @Mapping(target = "temperature", source = "temperature")
    @Mapping(target = "latitude", source = "coordinates.latitude")
    @Mapping(target = "longitude", source = "coordinates.longitude")
    WeatherResponse toResponse(Weather weather);

}
