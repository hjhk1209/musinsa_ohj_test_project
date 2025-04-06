package com.musinsa.ohj.documentation.docUrl;

import lombok.Getter;

@Getter
public enum CommonDocUrl implements DocUrlEnumType {

    RESPONSE_CODE("./common/code/responseCode", "응답 결과");

    private final String pageId;    // 문서 위치
    private final String text;    // 문서에 표시될 명

    CommonDocUrl(String pageId, String text) {
        this.pageId = pageId;
        this.text = text;
    }
}
