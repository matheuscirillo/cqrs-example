package br.com.matheuscirillo.cqrs.example.domain.exception;

public class BankAccountAlreadyExistsException extends RuntimeException {

    public BankAccountAlreadyExistsException(String message) {
        super(message);
    }

}
