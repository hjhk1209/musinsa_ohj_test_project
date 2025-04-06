package com.musinsa.ohj.documentation.snippet;

import com.musinsa.ohj.documentation.docUrl.DocUrlEnumType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadSubsectionExtractor;
import org.springframework.restdocs.snippet.Attributes;

import java.util.List;
import java.util.Map;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.key;

public interface DocumentFieldSnippet {

    default CustomFieldsSnippet createCustomFieldsSnippet(String type,
                                                          PayloadSubsectionExtractor<?> subsectionExtractor,
                                                          Map<String, Object> attributes,
                                                          FieldDescriptor... descriptors) {
        return new CustomFieldsSnippet(type, subsectionExtractor, List.of(descriptors), attributes
                , true);
    }

    default FieldDescriptor[] convertMapToFieldDescriptor(Map<String, String> map) {
        return map.entrySet().stream()
                .map(x -> fieldWithPath(x.getKey()).description(x.getValue()))
                .toArray(FieldDescriptor[]::new);
    }

    default Attributes.Attribute setFormat(String value) {
        return setAttribute("format", value);
    }

    /**
     * 템플릿의 key값과 일치한 태그에 입력한 값으로 넣어주는 메서드
     */
    default Attributes.Attribute setAttribute(String key, Object value) {
        return key(key).value(value);
    }

    default String generateLinkCodePop(DocUrlEnumType docUrl) {
        return String.format("link:%s.html[%s %s,role=\"popup\"]", docUrl.getPageId(), docUrl.getText(), "코드");
    }
}
