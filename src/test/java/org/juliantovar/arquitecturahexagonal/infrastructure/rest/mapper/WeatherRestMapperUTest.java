package org.juliantovar.arquitecturahexagonal.infrastructure.rest.mapper;

import org.juliantovar.arquitecturahexagonal.domain.model.Coordinates;
import org.juliantovar.arquitecturahexagonal.domain.model.Weather;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class WeatherRestMapperUTest {

    private final WeatherRestMapper mapper = Mappers.getMapper(WeatherRestMapper.class);

    @Test
    void convertDomainToResponseModel() {
        var weather = Weather.builder()
                .coordinates(Coordinates.builder()
                        .latitude(25.6866)
                        .longitude(-100.3161)
                        .build())
                .timezone("America/Monterrey")
                .temperature(31.5)
                .feelsLike(34.1)
                .pressure(1012)
                .humidity(55)
                .windSpeed(15.2)
                .description("Soleado")
                .build();

        var response = mapper.domainToResponse(weather);

        assertAll(
                () -> assertEquals(25.6866, response.latitude()),
                () -> assertEquals(-100.3161, response.longitude()),
                () -> assertEquals("America/Monterrey", response.timezone()),
                () -> assertEquals(31.5, response.temperature()),
                () -> assertEquals(34.1, response.feelsLike()),
                () -> assertEquals(1012, response.pressure()),
                () -> assertEquals(55, response.humidity()),
                () -> assertEquals(15.2, response.windSpeed()),
                () -> assertEquals("Soleado", response.description())
        );
    }

    @Test
    void convertDomainToResponseWhenDomainIsNull() {
        assertNull(mapper.domainToResponse(null));
    }

    @Test
    void convertDomainToResponseWithoutCoordinates() {
        var response = mapper.domainToResponse(Weather.builder()
                .temperature(20.0)
                .build());

        assertAll(
                () -> assertNull(response.latitude()),
                () -> assertNull(response.longitude()),
                () -> assertEquals(20.0, response.temperature())
        );
    }

    @Test
    void convertCoordinatesToDomainModel() {
        var coordinates = mapper.domainToCoordinates(25.6866, -100.3161);

        assertAll(
                () -> assertEquals(25.6866, coordinates.getLatitude()),
                () -> assertEquals(-100.3161, coordinates.getLongitude())
        );
    }

    @Test
    void convertNullCoordinatesToDomainModel() {
        assertNull(mapper.domainToCoordinates(null, null));
    }
}
