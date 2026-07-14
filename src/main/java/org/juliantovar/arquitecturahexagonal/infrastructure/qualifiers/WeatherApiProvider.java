package org.juliantovar.arquitecturahexagonal.infrastructure.qualifiers;

import jakarta.inject.Qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Qualifier para el proveedor WeatherAPI
 *
 * @author Julian Tovar
 * @since 10/07/2026
 */
@Qualifier
@Retention(RUNTIME)
@Target({TYPE, FIELD, PARAMETER})
public @interface WeatherApiProvider {
}
