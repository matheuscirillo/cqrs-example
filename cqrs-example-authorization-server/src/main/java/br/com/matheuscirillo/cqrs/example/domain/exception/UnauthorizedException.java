package br.com.matheuscirillo.cqrs.example.domain.exception;

public class UnauthorizedException extends Exception {

    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(String message) {
        super(message);
    }

}
