package com.rocket.gestao_vagas.exceptions.controller;

import com.rocket.gestao_vagas.exceptions.AppAuthenticationException;
import com.rocket.gestao_vagas.exceptions.ErrorMessageDto;
import com.rocket.gestao_vagas.exceptions.UserFoundException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ExceptionHandlerController {

    private final MessageSource messageSource;

    public ExceptionHandlerController(MessageSource source) {
        this.messageSource = source;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMessageDto>> handleMethodNotValidArgument(MethodArgumentNotValidException e) {
        var errors = e.getBindingResult().getFieldErrors().stream().map(err -> {
            String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());
            return new ErrorMessageDto(message, err.getField());
        }).toList();

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserFoundException.class)
    public ResponseEntity<String> handleUserFoundException(UserFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AppAuthenticationException.class)
    public ResponseEntity<String> handleAppAuthenticationException(AppAuthenticationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}

