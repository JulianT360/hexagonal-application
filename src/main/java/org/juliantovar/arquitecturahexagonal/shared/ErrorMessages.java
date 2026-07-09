package org.juliantovar.arquitecturahexagonal.shared;

/**
 * Class with error messages
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
public final class ErrorMessages {
    private ErrorMessages() {}

    public static final String INVALID_API_KEY = "Invalid OpenWeather API Key";
    public static final String WEATHER_NOT_FOUND = "Weather information not found";
    public static final String WEATHER_SERVICE_UNAVAILABLE = "Weather service unavailable";
    public static final String WEATHER_SERVICE_UNAVAILABLE_FALLBACK =  "Weather service unavailable after retries";

}
