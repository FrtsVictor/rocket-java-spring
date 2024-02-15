package com.rocket.gestao_vagas.exceptions;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlerController {

    private MessageSource messageSource;

    public ExceptionHandlerController(MessageSource source){
     this.messageSource = source;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMessageDto>> handleMethodNotValidArgument(MethodArgumentNotValidException e){
        var errors = e.getBindingResult().getFieldErrors().stream().map(err ->{
            String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());
            return new ErrorMessageDto(message, err.getField());
        }).collect(Collectors.toList());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserFoundException.class)
    public ResponseEntity<ErrorMessage> handleUserFoundException(UserFoundException e){

        return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}

record ErrorMessage(String message){}