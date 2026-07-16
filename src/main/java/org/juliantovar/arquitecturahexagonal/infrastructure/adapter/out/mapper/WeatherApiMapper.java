package org.juliantovar.arquitecturahexagonal.infrastructure.adapter.out.mapper;

import org.juliantovar.arquitecturahexagonal.domain.model.Weather;
import org.juliantovar.arquitecturahexagonal.infrastructure.client.dto.WeatherApiResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * Clase mapper para mapear la respuesta del api del clima
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
    Weather dtoToDomain(WeatherApiResponseDto response);

}
