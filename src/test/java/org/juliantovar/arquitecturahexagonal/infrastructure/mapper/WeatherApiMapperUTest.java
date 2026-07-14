package org.juliantovar.arquitecturahexagonal.infrastructure.mapper;

import org.jspecify.annotations.NonNull;
import org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.weatherapi.ConditionDto;
import org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.weatherapi.CurrentDto;
import org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.weatherapi.LocationDto;
import org.juliantovar.arquitecturahexagonal.infrastructure.dto.response.weatherapi.WeatherApiResponseDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Pruebas unitarias para {@link WeatherApiMapper}
 *
 * @author Julian Tovar
 * @since 10/07/2026
 */
public class WeatherApiMapperUTest {

    private final WeatherApiMapper mapper =
            Mappers.getMapper(WeatherApiMapper.class);

    @Test
    void mapWeatherResponseToDomainOk() {
        var dto = getWeatherApiResponseDto();

        var result = mapper.toDomain(dto);

        assertNotNull(result);
        assertNotNull(result.coordinates());

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

    /**
     * Method to generate a weather api response
     *
     * @return weather api response dummy {@link WeatherApiResponseDto}
     */
    private static @NonNull WeatherApiResponseDto getWeatherApiResponseDto() {
        var location = new LocationDto(
                "Monterrey",
                "Nuevo Leon",
                "Mexico",
                25.6866,
                -100.3161,
                "America/Monterrey"
        );

        var condition = new ConditionDto(
                "Soleado"
        );

        var current = new CurrentDto(
                31.5,
                15.2,
                1012,
                55,
                34.1,
                condition
        );

        return new WeatherApiResponseDto(location, current);
    }
}
