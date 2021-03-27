package br.com.matheuscirillo.cqrs.example.api.exception;

import org.springframework.http.HttpStatus;

public enum ErrorType {

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED);

    private HttpStatus httpStatusCode;

    private ErrorType(HttpStatus status) {
        this.httpStatusCode = status;
    }

    public HttpStatus getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(HttpStatus httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

}
