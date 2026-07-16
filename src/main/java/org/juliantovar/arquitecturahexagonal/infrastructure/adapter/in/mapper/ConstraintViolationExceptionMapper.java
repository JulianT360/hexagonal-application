package org.juliantovar.arquitecturahexagonal.infrastructure.adapter.in.mapper;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.juliantovar.arquitecturahexagonal.infrastructure.exception.ErrorCodes;
import org.juliantovar.arquitecturahexagonal.infrastructure.exception.ErrorResponse;

import java.time.LocalDateTime;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        String message = exception.getConstraintViolations()
                .stream()
                .findFirst()
                .map(ConstraintViolation::getMessage)
                .orElse("Invalid request");

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(
                        LocalDateTime.now(),
                        400,
                        ErrorCodes.INVALID_COORDINATES,
                        message,
                        uriInfo.getPath()
                ))
                .build();
    }

}
