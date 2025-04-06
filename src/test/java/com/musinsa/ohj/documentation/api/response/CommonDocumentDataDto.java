package com.musinsa.ohj.documentation.api.response;

import lombok.Builder;

import java.util.Map;

public record CommonDocumentDataDto(Map<String, String> responseCodeMap) {
    @Builder
    public CommonDocumentDataDto {

    }
}
