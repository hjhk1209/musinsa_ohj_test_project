package com.musinsa.ohj.documentation.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.musinsa.ohj.documentation.CommonDocumentTest;
import com.musinsa.ohj.documentation.api.response.CommonDocumentApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommonDocumentController.class)
public class CommonDocumentControllerTest extends CommonDocumentTest {

    @Test
    void getCommonTest() throws Exception {
        ResultActions result = mockMvc.perform(
                get("/test/common/"));

        MvcResult mvcResult = result.andReturn();
        CommonDocumentApiResponse commonDocumentApiResponse = getData(mvcResult);

        result.andExpect(status().isOk())
                .andDo(document("common/default",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        createCustomFieldsSnippet(
                                "common-response",
                                null,
                                attributes(key("title").value("공통 응답값")),
                                commonResponseFieldWithPaths()
                        )
                ))
                .andDo(document("common/code",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        createCustomFieldsSnippet(
                                "custom-pop-response",
                                beneathPath("data.responseCodeMap").withSubsectionId("responseCodeMap"),
                                attributes(key("title").value("응답결과코드")),
                                convertMapToFieldDescriptor(commonDocumentApiResponse.getData().responseCodeMap())
                        )
                ));
    }

    private CommonDocumentApiResponse getData(MvcResult result) throws IOException {
        return JsonMapper.builder().build()
                .readValue(result.getResponse().getContentAsString(Charset.defaultCharset()),
                        new TypeReference<>() {
                        });
    }
}
