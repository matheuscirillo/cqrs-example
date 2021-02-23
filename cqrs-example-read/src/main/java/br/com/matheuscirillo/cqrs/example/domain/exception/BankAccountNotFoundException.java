package br.com.matheuscirillo.cqrs.example.domain.exception;

public class BankAccountNotFoundException extends RuntimeException {

    public BankAccountNotFoundException(String msg) {
	super(msg);
    }

}
