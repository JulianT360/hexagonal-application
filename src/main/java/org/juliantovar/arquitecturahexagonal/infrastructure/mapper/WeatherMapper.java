package org.juliantovar.arquitecturahexagonal.infrastructure.mapper;

import org.juliantovar.arquitecturahexagonal.domain.model.Weather;
import org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.OpenWeatherResponseDto;
import org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.WeatherDescriptionDto;
import org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.WeatherResponse;
import org.juliantovar.arquitecturahexagonal.shared.Constants;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

/**
 * Mapper for weather response
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public interface WeatherMapper {
    @Mapping(target = "latitude", source = "lat")
    @Mapping(target = "longitude", source = "lon")
    @Mapping(target = "temperature", source = "current.temp")
    @Mapping(target = "feelsLike", source = "current.feelsLike")
    @Mapping(target = "pressure", source = "current.pressure")
    @Mapping(target = "humidity", source = "current.humidity")
    @Mapping(target = "windSpeed", source = "current.windSpeed")
    @Mapping(target = "description", source = "current.weather")
    Weather toDomain(OpenWeatherResponseDto response);

    /**
     * Method to set default value to map weather description.
     *
     * @param weather   List of {@link WeatherDescriptionDto}
     * @return string with the first weather description
     */
    default String mapWeatherDescription(
            List<WeatherDescriptionDto> weather) {
        if(weather == null || weather.isEmpty()) {
            return Constants.UNKNOWN_DESCRIPTION;
        }

        return weather.getFirst().description();
    }

    /**
     * Method to generate {@link WeatherResponse} from {@link Weather}
     *
     * @param weather   Object that include weather data
     * @return Response object with data obtainet about current weather
     */
    @Mapping(target = "temperature", source = "temperature")
    WeatherResponse toResponse(Weather weather);

}
