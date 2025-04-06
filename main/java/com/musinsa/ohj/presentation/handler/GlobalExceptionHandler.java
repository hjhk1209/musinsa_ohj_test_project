package com.musinsa.ohj.presentation.handler;

import com.musinsa.ohj.domain.model.constants.ApiResponseCode;
import com.musinsa.ohj.presentation.response.ApiResponse;
import com.musinsa.ohj.presentation.response.exception.ExceptionHandlerApiApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();

        return ExceptionHandlerApiApiResponse.builder(ApiResponseCode.BAD_REQUEST)
                .message(fieldError == null ? ApiResponseCode.BAD_REQUEST.getMessage() :
                        String.format("잘못 요청된 값[%s]이 존재합니다 [%s %s]",
                                fieldError.getRejectedValue(),
                                fieldError.getField(),
                                fieldError.getDefaultMessage()))
                .build()
                .toResponse();
    }

    @ExceptionHandler({HttpMessageNotReadableException.class,
            HandlerMethodValidationException.class,
            MissingServletRequestParameterException.class})
    public ResponseEntity<ApiResponse> handleHttpMessageNotReadableException() {
        return ExceptionHandlerApiApiResponse.builder(ApiResponseCode.BAD_REQUEST)
                .build()
                .toResponse();
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ApiResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return ExceptionHandlerApiApiResponse.builder(ApiResponseCode.METHOD_NOT_ALLOWED)
                .build()
                .toResponse();
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<ApiResponse> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        return ExceptionHandlerApiApiResponse.builder(ApiResponseCode.METHOD_NOT_ALLOWED)
                .build()
                .toResponse();
    }

    @ExceptionHandler({NoResourceFoundException.class})
    public ResponseEntity<ApiResponse> handleHttpMediaTypeNotSupportedException(NoResourceFoundException e) {
        return ExceptionHandlerApiApiResponse.builder(ApiResponseCode.NOT_EXIST_RESOURCE)
                .build()
                .toResponse();
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiResponse> handleException(Exception e) {
        log.error("InternalServerErrorExceptionHandler.handleException :: {}", e.getMessage(), e);
        return ExceptionHandlerApiApiResponse.builder(ApiResponseCode.INTERNAL_SERVER_ERROR)
                .build()
                .toResponse();
    }
}
