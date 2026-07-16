package org.juliantovar.arquitecturahexagonal.infrastructure.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

/**
 * DTO para la respuesta de WeatherAPI.
 *
 * @param location Objeto {@link LocationDto} con la información de la ubicación
 * @param current  Objeto {@link CurrentDto} con los datos del clima actual
 */
@Builder
public record WeatherApiResponseDto(
        LocationDto location,
        CurrentDto current) {

  /**
   * DTO para la respuesta de WeatherAPI.
   *
   * @param name      Nombre de la ciudad
   * @param region    Nombre del estado
   * @param country   Nombre del país
   * @param latitude  Latitud de la ubicación
   * @param longitude Longitud de la ubicación
   * @param timezone  Zona horaria de la ubicación
   */
  @Builder
  public record LocationDto(
          String name,
          String region,
          String country,
          @JsonProperty("lat")
          Double latitude,
          @JsonProperty("lon")
          Double longitude,
          @JsonProperty("tz_id")
          String timezone
  ) {
  }

  /**
   * DTO para la información del clima actual.
   *
   * @param temperature Temperatura en Celsius
   * @param windSpeed   Velocidad del viento en kilómetros por hora
   * @param pressure    Presión en milibares
   * @param humidity    Humedad en porcentaje
   * @param feelsLike   Sensación térmica en Celsius
   * @param condition   Descripción del clima actual
   */
  @Builder
  public record CurrentDto(
          @JsonProperty("temp_c")
          Double temperature,
          @JsonProperty("wind_kph")
          Double windSpeed,
          @JsonProperty("pressure_mb")
          Integer pressure,
          int humidity,
          @JsonProperty("feelslike_c")
          Double feelsLike,
          ConditionDto condition
  ) {
  }

  /**
   * DTO para la descripción de la condición del clima actual.
   *
   * @param text Descripción del clima actual
   */
  @Builder
  public record ConditionDto(
          String text) {
  }
}
