package com.sparta.kurlyo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@NoArgsConstructor
public class Response<T> {

    private boolean status;
    private String message;
    private T data;


    public Response(boolean status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static ResponseEntity<Response> toExceptionResponseEntity(ExceptionMessage exceptionMessage) {
        return ResponseEntity
                .status(exceptionMessage.getHttpStatus())
                .body(Response.builder()
                        .status(!exceptionMessage.getHttpStatus().isError())
                        .message(exceptionMessage.getDetail())
                        .data(exceptionMessage)
                        .build()
                );
    }

    public static ResponseEntity<Response> toAllExceptionResponseEntity(HttpStatus httpStatus,String message, Object data) {
        return ResponseEntity
                .status(httpStatus)
                .body(Response.builder()
                        .status(false)
                        .message(message)
                        .data(data)
                        .build()
                );
    }
    public static ResponseEntity<Response> toAllExceptionResponseEntity(ExceptionMessage exceptionMessage , Object data) {
        return ResponseEntity
                .status(exceptionMessage.getHttpStatus())
                .body(Response.builder()
                        .status(!exceptionMessage.getHttpStatus().isError())
                        .message(exceptionMessage.getDetail())
                        .data(data)
                        .build()
                );
    }

    public static ResponseEntity<Response> toResponseEntity(SuccessMessage message) {
        return ResponseEntity
                .status(message.getHttpStatus())
                .body(Response.builder()
                        .status(!message.getHttpStatus().isError())
                        .message(message.getDetail())
                        .data(message)
                        .build()
                );
    }
    public ResponseEntity<Response> toResponseEntity(SuccessMessage message, T data) {
        return ResponseEntity
                .status(message.getHttpStatus())
                .body(Response.builder()
                        .status(!message.getHttpStatus().isError())
                        .message(message.getDetail())
                        .data(data)
                        .build()
                );
    }
}
