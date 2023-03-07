package com.sparta.kurlyo.controller;

import com.sparta.kurlyo.dto.BoughtException;
import com.sparta.kurlyo.dto.CustomException;
import com.sparta.kurlyo.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.bootstrap.HttpServer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import static com.sparta.kurlyo.dto.ExceptionMessage.GOODS_COUNT_INVALID_RANGE;

@Slf4j
@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<Response> handleCustomException(CustomException e) {
        log.error("handleCustomException throw CustomException : {}", e.getExceptionMessage());
        return Response.toExceptionResponseEntity(e.getExceptionMessage());
    }

    //정규식
    @ExceptionHandler({BindException.class})
    public ResponseEntity<Response> bindException(BindException ex) {
        return Response.toAllExceptionResponseEntity(HttpStatus.BAD_REQUEST,
                ex.getFieldError().getDefaultMessage(), ex.getBindingResult().getTarget());
    }

    //마이리스트 토큰 없을시
    @ExceptionHandler({MissingRequestHeaderException.class})
    public ResponseEntity<Response> missingRequestHeaderException(MissingRequestHeaderException ex) {
        return Response.toAllExceptionResponseEntity(HttpStatus.BAD_REQUEST,
                "로그인이 되어있지 않습니다.", null);
    }

    // 500
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Response> handleAll(final Exception ex) {
        return Response.toAllExceptionResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), ex.toString());
    }
    @ExceptionHandler({BoughtException.class})
    protected ResponseEntity<Response> handleCustomRollBackException(BoughtException e) {
        log.error("handleCustomException throw CustomException : {}", e.getExceptionMessage());
        return Response.toAllExceptionResponseEntity(e.getExceptionMessage(), e.getObject());
    }

}
