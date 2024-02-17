package com.rocket.gestao_vagas.exceptions;

public class AppAuthenticationException extends RuntimeException {

    public AppAuthenticationException() {
        super("Invalid username or password");
    }

}
