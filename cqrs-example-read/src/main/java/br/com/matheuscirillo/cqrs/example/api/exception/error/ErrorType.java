package br.com.matheuscirillo.cqrs.example.api.exception.error;

import org.springframework.http.HttpStatus;

public enum ErrorType {

    AccountNotFoundError(HttpStatus.NOT_FOUND);

    private HttpStatus httpStatus;

    private ErrorType(HttpStatus httpStatus) {
	this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
	return httpStatus;
    }

}
