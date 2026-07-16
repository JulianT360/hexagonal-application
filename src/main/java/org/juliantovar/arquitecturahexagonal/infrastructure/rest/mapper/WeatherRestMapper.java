package org.juliantovar.arquitecturahexagonal.infrastructure.rest.mapper;

import org.juliantovar.arquitecturahexagonal.domain.model.Coordinates;
import org.juliantovar.arquitecturahexagonal.domain.model.Weather;
import org.juliantovar.arquitecturahexagonal.infrastructure.rest.dto.WeatherResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * Mapper para convertir del modelo de dominio {@link Weather}
 * al objeto de respuesta {@link WeatherResponse}.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public interface WeatherRestMapper {

  /**
   * Mapea del modelo de dominio {@link Weather} a la respuesta {@link WeatherResponse}.
   *
   * @param model Objeto de dominio {@link Weather}
   * @return objeto de respuesta {@link WeatherResponse}
   */
  @Mapping(target = "latitude", source = "coordinates.latitude")
  @Mapping(target = "longitude", source = "coordinates.longitude")
  WeatherResponse domainToResponse(Weather model);

  /**
   * Mapea de los valores datos al objeto de dominio {@link Coordinates}.
   *
   * @param latitude latitud de la ubicacion
   * @param longitude longitud de la ubicacion
   * @return objeto de dominio {@link Coordinates}
   */
  @Mapping(target = "latitude", source = "latitude")
  @Mapping(target = "longitude", source = "longitude")
  Coordinates domainToCoordinates(Double latitude, Double longitude);

}
