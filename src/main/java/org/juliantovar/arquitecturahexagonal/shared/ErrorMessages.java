package org.juliantovar.arquitecturahexagonal.shared;

import lombok.NoArgsConstructor;

/**
 * Mensajes de error.
 */
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class ErrorMessages {

  public static final String INVALID_API_KEY = "Invalid Provider API Key";
  public static final String WEATHER_NOT_FOUND = "Weather information not found";
  public static final String WEATHER_SERVICE_UNAVAILABLE = "Weather service unavailable";
  public static final String WEATHER_RATE_LIMIT_REACHED = "Weather rate limit reached";
  public static final String UNEXPECTED_PROVIDER_ERROR =
          "Unexpected error calling weather provider";
  public static final String UNEXPECTED_SERVER_ERROR = "Unexpected server error";

}
