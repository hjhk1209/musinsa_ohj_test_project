package com.musinsa.ohj.test.presentation.v1;

import com.musinsa.ohj.application.ProductService;
import com.musinsa.ohj.domain.model.constants.ApiResponseCode;
import com.musinsa.ohj.presentation.request.product.CreateProductRequest;
import com.musinsa.ohj.presentation.request.product.UpdateProductRequest;
import com.musinsa.ohj.presentation.response.product.CreateProductResponse;
import com.musinsa.ohj.presentation.response.product.DeleteProductResponse;
import com.musinsa.ohj.presentation.response.product.UpdateProductResponse;
import com.musinsa.ohj.presentation.v1.ProductController;
import com.musinsa.ohj.test.presentation.ControllerTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest extends ControllerTest {
    @MockBean
    ProductService productService;

    private final String regApi = "/v1/brand/{brandSeq}/category/{categorySeq}/product";
    private final String updateApi = "/v1/brand/{brandSeq}/category/{categorySeq}/product/{productSeq}";
    private final String deleteApi = "/v1/brand/{brandSeq}/category/{categorySeq}/product/{productSeq}";

    @Test
    @DisplayName("상품등록 API 성공")
    void 상품등록_성공() throws Exception {
        given(productService.createProduct(any(Long.class), any(Long.class), any(CreateProductRequest.class)))
                .willReturn(CreateProductResponse.builder(ApiResponseCode.SUCCESS)
                        .build());
        mockMvc.perform(post(regApi, 1l, 1l)
                .content(jsonMapper.writeValueAsString(CreateProductRequest.builder()
                        .productNm("new 상품")
                        .productPrice(1000l)
                        .build()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", Matchers.equalTo(ApiResponseCode.SUCCESS.getCode())));
    }

    @Test
    @DisplayName("상품등록 API 실패 파라미터유효하지않음")
    void 상품등록_실패_파라미터유효하지않음() throws Exception {
        mockMvc.perform(post(regApi, 1l, 1l)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", Matchers.equalTo(ApiResponseCode.BAD_REQUEST.getCode())));
        then(productService).should(never()).createProduct(any(), any(), any());
        mockMvc.perform(post(regApi, 1l, 1l)
                .content(jsonMapper.writeValueAsString(CreateProductRequest.builder()
                        .productNm("")
                        .productPrice(null)
                        .build()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", Matchers.equalTo(ApiResponseCode.BAD_REQUEST.getCode())));
        then(productService).should(never()).createProduct(any(), any(), any());
    }

    @Test
    @DisplayName("상품등록 API 실패 특정데이터가이미존재하거나안함")
    void 상품등록_실패_특정데이터가이미존재하거나안함() throws Exception {
        given(productService.createProduct(any(Long.class), any(Long.class), any(CreateProductRequest.class)))
                .willReturn(CreateProductResponse.builder(ApiResponseCode.NOT_FOUND_DATA)
                        .build());
        mockMvc.perform(post(regApi, 1l, 1l)
                .content(jsonMapper.writeValueAsString(CreateProductRequest.builder()
                        .productNm("new 상품")
                        .productPrice(1000l)
                        .build()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", Matchers.equalTo(ApiResponseCode.NOT_FOUND_DATA.getCode())));
    }

    @Test
    @DisplayName("상품수정 API 성공")
    void 상품수정_성공() throws Exception {
        given(productService.updateProduct(any(Long.class), any(Long.class), any(Long.class), any(UpdateProductRequest.class)))
                .willReturn(UpdateProductResponse.builder(ApiResponseCode.SUCCESS)
                        .build());
        mockMvc.perform(put(updateApi, 1l, 1l, 1l)
                .content(jsonMapper.writeValueAsString(UpdateProductRequest.builder()
                        .productNm("new 상품")
                        .productPrice(1000l)
                        .brandSeq(1l)
                        .categorySeq(1l)
                        .build()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", Matchers.equalTo(ApiResponseCode.SUCCESS.getCode())));
    }

    @Test
    @DisplayName("상품수정 API 실패 파라미터유효하지않음")
    void 상품수정_실패_파라미터유효하지않음() throws Exception {
        mockMvc.perform(put(updateApi, 1l, 1l, 1l)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", Matchers.equalTo(ApiResponseCode.BAD_REQUEST.getCode())));
        then(productService).should(never()).createProduct(any(), any(), any());
        mockMvc.perform(put(updateApi, 1l, 1l, 1l)
                .content(jsonMapper.writeValueAsString(UpdateProductRequest.builder()
                        .productNm("")
                        .productPrice(null)
                        .brandSeq(null)
                        .categorySeq(null)
                        .build()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", Matchers.equalTo(ApiResponseCode.BAD_REQUEST.getCode())));
        then(productService).should(never()).createProduct(any(), any(), any());
        then(productService).should(never()).updateProduct(any(), any(), any(), any());
    }

    @Test
    @DisplayName("상품수정 API 실패 특정데이터가이미존재하거나안함")
    void 상품수정_실패_특정데이터가이미존재하거나안함() throws Exception {
        given(productService.updateProduct(any(Long.class), any(Long.class), any(Long.class), any(UpdateProductRequest.class)))
                .willReturn(UpdateProductResponse.builder(ApiResponseCode.NOT_FOUND_DATA)
                        .build());
        mockMvc.perform(put(updateApi, 1l, 1l, 1l)
                .content(jsonMapper.writeValueAsString(UpdateProductRequest.builder()
                        .productNm("new 상품")
                        .productPrice(1000l)
                        .brandSeq(1l)
                        .categorySeq(1l)
                        .build()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", Matchers.equalTo(ApiResponseCode.NOT_FOUND_DATA.getCode())));
    }

    @Test
    @DisplayName("상품삭제 API 성공")
    void 상품삭제_성공() throws Exception {
        given(productService.deleteProduct(any(Long.class), any(Long.class), any(Long.class)))
                .willReturn(DeleteProductResponse.builder(ApiResponseCode.SUCCESS)
                        .build());
        mockMvc.perform(delete(deleteApi, 1l, 1l, 1l)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", Matchers.equalTo(ApiResponseCode.SUCCESS.getCode())));
    }

    @Test
    @DisplayName("상품삭제 API 실패 특정데이터가이미존재하거나안함")
    void 상품삭제_실패_특정데이터가이미존재하거나안함() throws Exception {
        given(productService.deleteProduct(any(Long.class), any(Long.class), any(Long.class)))
                .willReturn(DeleteProductResponse.builder(ApiResponseCode.NOT_FOUND_DATA)
                        .build());
        mockMvc.perform(delete(deleteApi, 1l, 1l, 1l)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", Matchers.equalTo(ApiResponseCode.NOT_FOUND_DATA.getCode())));
    }
}
