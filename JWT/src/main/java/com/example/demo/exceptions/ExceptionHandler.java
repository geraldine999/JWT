package com.example.demo.exceptions;

import java.sql.Timestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.dtos.ErrorDto;
import com.example.demo.dtos.ExceptionResponseDto;

@ControllerAdvice
@RestController
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exceptionHandler(Exception exception) {

        var error = ErrorDto.builder().code(HttpStatus.BAD_REQUEST.value()).detail(exception.getMessage())
                .timestamp(Timestamp.from(Instant.now())).build();

        return new ResponseEntity<>(new ExceptionResponseDto(List.of(error)), HttpStatus.valueOf(error.getCode()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({ AuthenticationException.class })
    public ResponseEntity<Object> securityExceptionHandler(Exception exception) {

        var error = ErrorDto.builder().code(HttpStatus.FORBIDDEN.value()).detail(exception.getMessage())
                .timestamp(Timestamp.from(Instant.now())).build();

        return new ResponseEntity<>(new ExceptionResponseDto(List.of(error)), HttpStatus.valueOf(error.getCode()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ErrorDto> errors = new ArrayList<>();
        for (ObjectError objectError : ex.getBindingResult().getAllErrors()) {
            var error = ErrorDto.builder().code(HttpStatus.BAD_REQUEST.value()).detail(objectError.getDefaultMessage())
                    .timestamp(Timestamp.from(Instant.now())).build();
            errors.add(error);
        }

        return new ResponseEntity<>(new ExceptionResponseDto(errors), HttpStatus.BAD_REQUEST);
    }

}
