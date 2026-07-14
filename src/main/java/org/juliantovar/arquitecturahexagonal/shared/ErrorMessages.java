package org.juliantovar.arquitecturahexagonal.shared;

/**
 * Class with error messages
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
public final class ErrorMessages {
    private ErrorMessages() {}

    public static final String INVALID_API_KEY = "Invalid Provider API Key";
    public static final String WEATHER_NOT_FOUND = "Weather information not found";
    public static final String WEATHER_SERVICE_UNAVAILABLE = "Weather service unavailable";
    public static final String WEATHER_RATE_LIMIT_REACHED = "Weather rate limit reached";
    public static final String UNEXPECTED_PROVIDER_ERROR = "Unexpected error calling weather provider";

    public static final String REQUIRED_LATITUDE = "Latitude is required";
    public static final String REQUIRED_LONGITUDE = "Longitude is required";
    public static final String INVALID_LATITUDE = "Latitude must be a value between -90 and 90";
    public static final String INVALID_LONGITUDE = "Longitude must be a value between -180 and 180";

}
