package org.juliantovar.arquitecturahexagonal.infrastructure.exception;

import java.time.LocalDateTime;

/**
 * Response Object for error responses.
 *
 * @param timestamp Timestamp
 * @param status    Http code status
 * @param error     Error code from {@link ErrorCode}
 * @param message   Message of the error
 * @param path      Path consumed
 *
 * @author Julian Tovar
 * @since 09/07/2026
 */
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        ErrorCode error,
        String message,
        String path
) {
}
