package com.musinsa.ohj.presentation.response;

import org.springframework.http.ResponseEntity;

public interface ApiResponse<T> {
    String getCode();
    String getMessage();
    T getData();
    ResponseEntity<ApiResponse> toResponse();
}
