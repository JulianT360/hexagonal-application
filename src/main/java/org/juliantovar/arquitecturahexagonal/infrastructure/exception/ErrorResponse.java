package org.juliantovar.arquitecturahexagonal.infrastructure.exception;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * Objeto de respuesta para generar las respuestas de error
 *
 * @param timestamp Fecha y hora del error
 * @param status    Código de estatus Http
 * @param error     Código de error de: {@link ErrorCodes}
 * @param message   Mensaje de error
 * @param path      Path consumido
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
@Builder
@RegisterForReflection
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        ErrorCodes error,
        String message,
        String path
) {
}
