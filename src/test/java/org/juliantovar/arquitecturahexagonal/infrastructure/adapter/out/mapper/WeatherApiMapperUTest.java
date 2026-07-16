package org.juliantovar.arquitecturahexagonal.infrastructure.adapter.out.mapper;

import org.juliantovar.arquitecturahexagonal.infrastructure.client.dto.WeatherApiResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class WeatherApiMapperUTest {

    private final WeatherApiMapper mapper = Mappers.getMapper(WeatherApiMapper.class);

    @Test
    void convertDtoToDomainMapsFullData() {

        var dto = WeatherApiResponseDto.builder()
                .location(
                        WeatherApiResponseDto.LocationDto.builder()
                        .latitude(25.6866)
                        .longitude(-100.3161)
                        .timezone("America/Monterrey")
                        .build()
                )
                .current(
                        WeatherApiResponseDto.CurrentDto.builder()
                        .temperature(31.5)
                        .feelsLike(34.1)
                        .pressure(1012)
                        .humidity(55)
                        .windSpeed(15.2)
                        .condition(
                                WeatherApiResponseDto.ConditionDto.builder()
                                .text("Soleado")
                                .build()
                        )
                        .build()
                ).build();

        var weather = mapper.dtoToDomain(dto);

        assertAll(
                () -> assertEquals(25.6866, weather.getCoordinates().getLatitude()),
                () -> assertEquals(-100.3161, weather.getCoordinates().getLongitude()),
                () -> assertEquals("America/Monterrey", weather.getTimezone()),
                () -> assertEquals(31.5, weather.getTemperature()),
                () -> assertEquals(34.1, weather.getFeelsLike()),
                () -> assertEquals(1012, weather.getPressure()),
                () -> assertEquals(55, weather.getHumidity()),
                () -> assertEquals(15.2, weather.getWindSpeed()),
                () -> assertEquals("Soleado", weather.getDescription())
        );
    }

    @Test
    void convertDtoToDomainReturnsNullWhenDtoIsNull() {
        assertNull(mapper.dtoToDomain(null));
    }

    @Test
    void convertDtoToDomainWhenMissingNestedProviderData() {
        var dto = WeatherApiResponseDto.builder().build();

        var weather = mapper.dtoToDomain(dto);

        assertAll(
                () -> assertNull(weather.getCoordinates()),
                () -> assertNull(weather.getTimezone()),
                () -> assertNull(weather.getTemperature()),
                () -> assertNull(weather.getDescription())
        );
    }
}
