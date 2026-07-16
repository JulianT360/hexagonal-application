package org.juliantovar.arquitecturahexagonal.infrastructure.exception;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.LocalDateTime;
import lombok.Builder;


/**
 * Objeto de respuesta para generar las respuestas de error.
 *
 * @param timestamp Fecha y hora del error
 * @param status    Código de estatus Http
 * @param error     Código de error de: {@link ErrorCodes}
 * @param message   Mensaje de error
 * @param path      Path consumido
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
