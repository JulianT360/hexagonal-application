package org.juliantovar.arquitecturahexagonal.domain.model;

import lombok.Builder;

/**
 * Clase de dominio para el objeto base del clima
 *
 * @param coordinates   Latitud y longitud de la ubicación
 * @param timezone      Zona horaria de la ubicación
 * @param temperature   Temperatura de la ubicación
 * @param feelsLike     Sensación térmica de la ubicación
 * @param pressure      Presión atmosférica de la ubicación
 * @param humidity      Porcentaje de humedad de la ubicación
 * @param windSpeed     Velocidad del viento de la ubicación
 * @param description   Descripción sobre el clima de la ubicación
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
@Builder
public record Weather (
        Coordinates coordinates,
        String timezone,
        Double temperature,
        Double feelsLike,
        Integer pressure,
        Integer humidity,
        Double windSpeed,
        String description
) {}
