package br.com.matheuscirillo.cqrs.example.api.exception.error;

import lombok.Getter;

import java.util.Date;

@Getter
public class ApiError {

    private Long timestamp;
    private String type;
    private Integer httpCode;
    private String message;

    public ApiError(ErrorType errorType, String message) {
        this.timestamp = new Date().getTime();
        this.message = message;
        this.httpCode = errorType.getHttpStatus().value();
        this.type = errorType.name();
    }

}
