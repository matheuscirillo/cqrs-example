package br.com.matheuscirillo.cqrs.example.api.exception.error;

import org.springframework.http.HttpStatus;

public enum ErrorType {

    TransactionError(HttpStatus.UNPROCESSABLE_ENTITY),
    AccountNotFoundError(HttpStatus.NOT_FOUND),
    AccountAlreadyExistsError(HttpStatus.UNPROCESSABLE_ENTITY);

    private HttpStatus httpStatus;

    private ErrorType(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
