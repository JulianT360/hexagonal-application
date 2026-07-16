package org.juliantovar.arquitecturahexagonal.infrastructure.resource;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

/**
 * Recurso de prueba para exponer WeatherAPI a través de WireMock.
 *
 * @author Julian Tovar
 * @since 13/07/2026
 */
public class WeatherProviderWireMockResource implements QuarkusTestResourceLifecycleManager {

    private static WireMockServer wireMockServer;

    @Override
    public Map<String, String> start() {
        wireMockServer = new WireMockServer(
                options()
                        .dynamicPort()
                        .usingFilesUnderClasspath("wiremock")
        );
        wireMockServer.start();

        return Map.of(
                "quarkus.rest-client.weather-api.url",
                wireMockServer.baseUrl()
        );
    }

    @Override
    public void stop() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }

    public static void verifyCurrentWeatherRequest(String coordinates) {
        wireMockServer.verify(getRequestedFor(urlPathEqualTo("/v1/current.json"))
                .withQueryParam("key", equalTo("test-api-key"))
                .withQueryParam("q", equalTo(coordinates))
                .withQueryParam("lang", equalTo("es")));
    }
}
