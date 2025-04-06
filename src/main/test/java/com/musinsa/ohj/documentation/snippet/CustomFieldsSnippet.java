package com.musinsa.ohj.documentation.snippet;

import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.Operation;
import org.springframework.restdocs.payload.AbstractFieldsSnippet;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadSubsectionExtractor;

import java.util.List;
import java.util.Map;

public class CustomFieldsSnippet extends AbstractFieldsSnippet {
    /**
     * @param type                     : snippet 명
     * @param subsectionExtractor      : payload내 하위 섹션을 추출 시
     * @param descriptors              : 문서 필드 관련 정의
     * @param attributes               : *.snippet 파일 내 양식
     * @param ignoreUndocumentedFields 문서화되지 않은 필드를 무시해야하는지 여부
     */
    public CustomFieldsSnippet(String type, PayloadSubsectionExtractor<?> subsectionExtractor,
                               List<FieldDescriptor> descriptors, Map<String, Object> attributes,
                               boolean ignoreUndocumentedFields) {
        super(type, descriptors, attributes, ignoreUndocumentedFields,
                subsectionExtractor);
    }

    @Override
    protected MediaType getContentType(Operation operation) {
        return operation.getResponse().getHeaders().getContentType();
    }

    @Override
    protected byte[] getContent(Operation operation) {
        return operation.getResponse().getContent();
    }
}
