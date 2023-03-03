package com.sparta.kurlyo.entity;

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

    public static ResponseEntity<Response> toResponseEntity(String message) {
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(Response.builder()
                        .status(!HttpStatus.OK.isError())
                        .message(message)
                        .data(message)
                        .build()
                );
    }
    public ResponseEntity<Response> toResponseEntity(String message, T data) {
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(Response.builder()
                        .status(!HttpStatus.OK.isError())
                        .message(message)
                        .data(data)
                        .build()
                );
    }
}
