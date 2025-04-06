package com.musinsa.ohj.documentation.v1;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.musinsa.ohj.application.ProductService;
import com.musinsa.ohj.documentation.CommonDocumentTest;
import com.musinsa.ohj.domain.model.constants.ApiResponseCode;
import com.musinsa.ohj.presentation.request.product.CreateProductRequest;
import com.musinsa.ohj.presentation.request.product.UpdateProductRequest;
import com.musinsa.ohj.presentation.response.product.CreateProductResponse;
import com.musinsa.ohj.presentation.response.product.DeleteProductResponse;
import com.musinsa.ohj.presentation.response.product.UpdateProductResponse;
import com.musinsa.ohj.presentation.v1.ProductController;
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

@WebMvcTest(ProductController.class)
public class ProductDocumentControllerTest extends CommonDocumentTest {

    @MockBean
    ProductService productService;

    private final JsonMapper jsonMapper = JsonMapper.builder().build();

    @Test
    void createProductDocument() throws Exception {
        given(productService.createProduct(any(Long.class), any(Long.class), any(CreateProductRequest.class)))
                .willReturn(CreateProductResponse.builder(ApiResponseCode.SUCCESS)
                        .build());

        mockMvc.perform(post("/v1/brand/{brandSeq}/category/{categorySeq}/product", 9l, 1l)
                .content(jsonMapper.writeValueAsString(CreateProductRequest.builder()
                        .productNm("new 상품")
                        .productPrice(8000l)
                        .build()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("product/createProduct",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("brandSeq")
                                        .description("브랜드 Seq"),
                                parameterWithName("categorySeq")
                                        .description("카테고리 Seq")),
                        requestFields(
                                fieldWithPath("productNm").type(JsonFieldType.STRING)
                                        .description("상품명"),
                                fieldWithPath("productPrice").type(JsonFieldType.NUMBER)
                                        .description("상품가격")
                        ),
                        responseFields(commonResponseFieldWithPaths())));
    }

    @Test
    void updateProductDocument() throws Exception {
        given(productService.updateProduct(any(Long.class), any(Long.class), any(Long.class), any(UpdateProductRequest.class)))
                .willReturn(UpdateProductResponse.builder(ApiResponseCode.SUCCESS)
                        .build());

        mockMvc.perform(put("/v1/brand/{brandSeq}/category/{categorySeq}/product/{productSeq}", 9l, 1l, 73l)
                .content(jsonMapper.writeValueAsString(UpdateProductRequest.builder()
                        .productNm("update상품")
                        .productPrice(8000l)
                        .brandSeq(9l)
                        .categorySeq(1l)
                        .build()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("product/updateProduct",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("brandSeq")
                                        .description("브랜드 Seq"),
                                parameterWithName("categorySeq")
                                        .description("카테고리 Seq"),
                                parameterWithName("productSeq")
                                        .description("상품 Seq")),
                        requestFields(
                                fieldWithPath("productNm").type(JsonFieldType.STRING)
                                        .description("상품명"),
                                fieldWithPath("productPrice").type(JsonFieldType.NUMBER)
                                        .description("상품가격"),
                                fieldWithPath("brandSeq").type(JsonFieldType.NUMBER)
                                        .description("수정할 브랜드 Seq"),
                                fieldWithPath("categorySeq").type(JsonFieldType.NUMBER)
                                        .description("수정할 카테고리 Seq")
                        ),
                        responseFields(commonResponseFieldWithPaths())));
    }

    @Test
    void deleteProductDocument() throws Exception {
        given(productService.deleteProduct(any(Long.class), any(Long.class), any(Long.class)))
                .willReturn(DeleteProductResponse.builder(ApiResponseCode.SUCCESS)
                        .build());

        mockMvc.perform(delete("/v1/brand/{brandSeq}/category/{categorySeq}/product/{productSeq}", 9l, 1l, 73l))
                .andExpect(status().isOk())
                .andDo(document("product/deleteProduct",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("brandSeq")
                                        .description("브랜드 Seq"),
                                parameterWithName("categorySeq")
                                        .description("카테고리 Seq"),
                                parameterWithName("productSeq")
                                        .description("상품 Seq")),
                        responseFields(commonResponseFieldWithPaths())));
    }
}
