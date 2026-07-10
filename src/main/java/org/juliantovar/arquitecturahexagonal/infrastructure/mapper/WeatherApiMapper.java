package org.juliantovar.arquitecturahexagonal.infrastructure.mapper;

import org.juliantovar.arquitecturahexagonal.domain.model.Coordinates;
import org.juliantovar.arquitecturahexagonal.domain.model.Weather;
import org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.weatherapi.LocationDto;
import org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.weatherapi.WeatherApiResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * Mapper class for weather api response
 *
 * @author Julian Tovar
 * @since 10/07/2026
 */
@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public interface WeatherApiMapper {

    @Mapping(target = "coordinates", source = "location")
    @Mapping(target = "timezone", source = "location.timezone")
    @Mapping(target = "temperature", source = "current.temperature")
    @Mapping(target = "feelsLike", source = "current.feelsLike")
    @Mapping(target = "pressure", source = "current.pressure")
    @Mapping(target = "humidity", source = "current.humidity")
    @Mapping(target = "windSpeed", source = "current.windSpeed")
    @Mapping(target = "description", source = "current.condition.text")
    Weather toDomain(WeatherApiResponseDto response);

    /**
     * Parse from {@link WeatherApiResponseDto} to {@link Coordinates}
     *
     * @param location   Response location from weather api {@link LocationDto}
     * @return coordinates object
     */
    default Coordinates toCoordinates(LocationDto location) {
        return new Coordinates(
                location.latitude(),
                location.longitude());
    }
}
