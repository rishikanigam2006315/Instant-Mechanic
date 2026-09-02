package com.instantmechanic.mechanic_api.exception;

import com.instantmechanic.mechanic_api.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleRuntimeException(RuntimeException exception) {

        return new ErrorResponse(
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
    }
}