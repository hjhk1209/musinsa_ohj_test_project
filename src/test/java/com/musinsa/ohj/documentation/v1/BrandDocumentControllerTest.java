package com.musinsa.ohj.documentation.v1;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.musinsa.ohj.application.BrandService;
import com.musinsa.ohj.documentation.CommonDocumentTest;
import com.musinsa.ohj.domain.model.constants.ApiResponseCode;
import com.musinsa.ohj.presentation.request.brand.CreateBrandRequest;
import com.musinsa.ohj.presentation.request.brand.UpdateBrandRequest;
import com.musinsa.ohj.presentation.request.brand.DeleteBrandRequest;
import com.musinsa.ohj.presentation.response.brand.CreateBrandResponse;
import com.musinsa.ohj.presentation.response.brand.DeleteBrandResponse;
import com.musinsa.ohj.presentation.response.brand.UpdateBrandResponse;
import com.musinsa.ohj.presentation.v1.BrandController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BrandController.class)
public class BrandDocumentControllerTest extends CommonDocumentTest {

    @MockBean
    BrandService brandService;

    private final JsonMapper jsonMapper = JsonMapper.builder().build();

    @Test
    void createBrandDocument() throws Exception {
        given(brandService.createBrand(any(CreateBrandRequest.class)))
                .willReturn(CreateBrandResponse.builder(ApiResponseCode.SUCCESS)
                        .build());

        mockMvc.perform(post("/v1/brand")
                .content(jsonMapper.writeValueAsString(CreateBrandRequest.builder()
                        .brandNm("new브랜드")
                        .build()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("brand/createBrand",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("brandNm").type(JsonFieldType.STRING)
                                        .description("브랜드명")
                        ),
                        responseFields(commonResponseFieldWithPaths())));
    }

    @Test
    void updateBrandDocument() throws Exception {
        given(brandService.updateBrand(any(Long.class), any(UpdateBrandRequest.class)))
                .willReturn(UpdateBrandResponse.builder(ApiResponseCode.SUCCESS)
                        .build());

        mockMvc.perform(put("/v1/brand/{brandSeq}", 10l)
                .content(jsonMapper.writeValueAsString(UpdateBrandRequest.builder()
                        .brandNm("update브랜드")
                        .build()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("brand/updateBrand",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("brandSeq")
                                .description("브랜드 Seq")),
                        requestFields(
                                fieldWithPath("brandNm").type(JsonFieldType.STRING)
                                        .description("브랜드명")
                        ),
                        responseFields(commonResponseFieldWithPaths())));
    }

    @Test
    void deleteBrandDocument() throws Exception {
        given(brandService.deleteBrand(any(Long.class), any(DeleteBrandRequest.class)))
                .willReturn(DeleteBrandResponse.builder(ApiResponseCode.SUCCESS)
                        .build());

        mockMvc.perform(delete("/v1/brand/{brandSeq}", 10l)
                .content(jsonMapper.writeValueAsString(DeleteBrandRequest.builder()
                        .isProductBulkDelete(true)
                        .build()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("brand/deleteBrand",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("brandSeq")
                                .description("브랜드 Seq")),
                        requestFields(
                                fieldWithPath("isProductBulkDelete").type(JsonFieldType.BOOLEAN)
                                        .description("삭제할 브랜드에 매핑된 상품까지 삭제할지 여부")
                        ),
                        responseFields(commonResponseFieldWithPaths())));
    }
}
