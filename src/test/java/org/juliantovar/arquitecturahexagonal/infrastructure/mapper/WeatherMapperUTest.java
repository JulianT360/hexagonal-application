package org.juliantovar.arquitecturahexagonal.infrastructure.mapper;

import org.juliantovar.arquitecturahexagonal.domain.model.Coordinates;
import org.juliantovar.arquitecturahexagonal.domain.model.Weather;
import org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.openweather.CurrentWeatherDto;
import org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.openweather.OpenWeatherResponseDto;
import org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.openweather.WeatherDescriptionDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Pruebas unitarias para {@link WeatherMapper}
 *
 * @author Julian Tovar
 * @since 13/07/2026
 */
public class WeatherMapperUTest {

    private final WeatherMapper mapper = Mappers.getMapper(WeatherMapper.class);

    @Test
    void mapOpenWeatherResponseToDomainOk() {
        var response = new OpenWeatherResponseDto(
                25.6866,
                -100.3161,
                "America/Monterrey",
                new CurrentWeatherDto(
                        31.5,
                        34.1,
                        1012,
                        55.0,
                        15.2,
                        List.of(new WeatherDescriptionDto("Soleado"))
                )
        );

        var result = mapper.toDomain(response);

        assertNotNull(result);
        assertEquals(25.6866, result.coordinates().latitude());
        assertEquals(-100.3161, result.coordinates().longitude());
        assertEquals("America/Monterrey", result.timezone());
        assertEquals(31.5, result.temperature());
        assertEquals(34.1, result.feelsLike());
        assertEquals(1012, result.pressure());
        assertEquals(55, result.humidity());
        assertEquals(15.2, result.windSpeed());
        assertEquals("Soleado", result.description());
    }

    @Test
    void mapWeatherDescriptionReturnsUnknownWhenListIsNull() {
        assertEquals("Desconocido", mapper.mapWeatherDescription(null));
    }

    @Test
    void mapWeatherDescriptionReturnsUnknownWhenListIsEmpty() {
        assertEquals("Desconocido", mapper.mapWeatherDescription(List.of()));
    }

    @Test
    void mapDomainToResponseOk() {
        var weather = new Weather(
                new Coordinates(25.6866, -100.3161),
                "America/Monterrey",
                31.5,
                34.1,
                1012,
                55,
                15.2,
                "Soleado"
        );

        var result = mapper.toResponse(weather);

        assertEquals(25.6866, result.latitude());
        assertEquals(-100.3161, result.longitude());
        assertEquals("America/Monterrey", result.timezone());
        assertEquals(31.5, result.temperature());
        assertEquals(34.1, result.feelsLike());
        assertEquals(1012, result.pressure());
        assertEquals(55, result.humidity());
        assertEquals(15.2, result.windSpeed());
        assertEquals("Soleado", result.description());
    }
}
