package com.musinsa.ohj.documentation;

import com.musinsa.ohj.common.utils.DateUtils;
import com.musinsa.ohj.documentation.docUrl.CommonDocUrl;
import com.musinsa.ohj.documentation.snippet.DocumentFieldSnippet;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class CommonDocumentTest extends DocumentApplicationTest implements DocumentFieldSnippet {
    protected FieldDescriptor[] commonResponseFieldWithPaths() {
        return new FieldDescriptor[]{fieldWithPath("code").type(JsonFieldType.STRING)
                .attributes(setFormat("S000: 성공 +" + "\n" +
                        "Exxx: 실패"))
                .description(generateLinkCodePop(CommonDocUrl.RESPONSE_CODE)),
                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 결과 메시지"),
                fieldWithPath("timestamp").type(JsonFieldType.STRING)
                        .attributes(setFormat("ex) " + DateUtils.getNowIso8601Format()))
                        .description("API 응답시간 ISO 8601 규격에 맞춰 표현합니다"),
                fieldWithPath("traceId").type(JsonFieldType.STRING).description("API 요청마다 생성되는 랜덤 ID 20자리")};
    }

    protected FieldDescriptor[] commonResponseFieldWithPaths(FieldDescriptor... fieldDescriptor) {
        return ArrayUtils.addAll(commonResponseFieldWithPaths(), fieldDescriptor);
    }
}
