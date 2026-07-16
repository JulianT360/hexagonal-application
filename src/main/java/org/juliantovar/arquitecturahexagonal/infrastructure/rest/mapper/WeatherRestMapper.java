package org.juliantovar.arquitecturahexagonal.infrastructure.rest.mapper;

import org.juliantovar.arquitecturahexagonal.domain.model.Coordinates;
import org.juliantovar.arquitecturahexagonal.domain.model.Weather;
import org.juliantovar.arquitecturahexagonal.infrastructure.rest.dto.WeatherResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * Mapper para convertir del modelo de dominio {@link Weather}
 * al objeto de respuesta {@link WeatherResponse}
 *
 * @author Julian Tovar
 * @since 15/07/2026
 */
@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public interface WeatherRestMapper {

    @Mapping(target = "latitude", source = "coordinates.latitude")
    @Mapping(target = "longitude", source = "coordinates.longitude")
    WeatherResponse domainToResponse(Weather model);

    @Mapping(target = "latitude", source = "latitude")
    @Mapping(target = "longitude", source = "longitude")
    Coordinates domainToCoordinates(Double latitude, Double longitude);

}
