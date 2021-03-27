package br.com.matheuscirillo.cqrs.example.domain.exception;

public class TransactionException extends RuntimeException {

    public TransactionException(String message) {
        super(message);
    }

}
