package com.sparta.kurlyo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoughtException extends RuntimeException {
    private final ExceptionMessage exceptionMessage;
    private final Object object;

}
