package com.gnsoftware.Ordem.Servico.controller.exceptions;

import com.gnsoftware.Ordem.Servico.services.exceptions.DataIntegrityViolationException;
import com.gnsoftware.Ordem.Servico.services.exceptions.ModelNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@ControllerAdvice
public class ControleHandllerException implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExceptionHandler(ModelNotFound.class)
    public ResponseEntity<StandardError> notFoundException(HttpServletRequest request, ModelNotFound m) {

        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        StandardError error = StandardError.builder()
                .timestamp(Instant.now())
                .status(httpStatus.value()) //404
                .Error(m.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(httpStatus).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validationError(MethodArgumentNotValidException m, HttpServletRequest request) {

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ValidationError validationError = new ValidationError(
                Instant.now(), httpStatus.value(), "ERRO NA VALIDAÇAO DOS CAMPOS",
                request.getRequestURI()
        );

        for (FieldError fieldError : m.getBindingResult().getFieldErrors()) {
            validationError.addErro(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(httpStatus).body(validationError);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> dataIntegrityViolation(DataIntegrityViolationException dataIntegrityViolationException, HttpServletRequest request) {

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        StandardError error = StandardError.builder()
                .timestamp(Instant.now())
                .status(httpStatus.value())
                .Error(dataIntegrityViolationException.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(httpStatus).body(error);

    }
}
