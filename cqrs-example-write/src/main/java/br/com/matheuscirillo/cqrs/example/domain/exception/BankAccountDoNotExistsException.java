package br.com.matheuscirillo.cqrs.example.domain.exception;

public class BankAccountDoNotExistsException extends RuntimeException {

    public BankAccountDoNotExistsException(String arg0) {
        super(arg0);
    }

}
