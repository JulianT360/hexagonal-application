package org.juliantovar.arquitecturahexagonal.infrastructure.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        ErrorCode error,
        String message,
        String path
) {
}
