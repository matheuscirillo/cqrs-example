package br.com.matheuscirillo.cqrs.example.api.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import lombok.Data;

import java.util.Date;

@Data
public class ApiError {

    @JsonFormat(shape = Shape.NUMBER)
    private Date timestamp;
    private Integer statusCode;
    private String message;

    public ApiError(ErrorType errorType, String message) {
        this.statusCode = errorType.getHttpStatusCode().value();
        this.message = message;
        this.timestamp = new Date();
    }

}
