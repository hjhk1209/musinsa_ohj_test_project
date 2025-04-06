package com.musinsa.ohj.test.presentation.v1;

import com.musinsa.ohj.application.BrandService;
import com.musinsa.ohj.domain.model.constants.ApiResponseCode;
import com.musinsa.ohj.presentation.request.brand.CreateBrandRequest;
import com.musinsa.ohj.presentation.request.brand.DeleteBrandRequest;
import com.musinsa.ohj.presentation.request.brand.UpdateBrandRequest;
import com.musinsa.ohj.presentation.response.brand.CreateBrandResponse;
import com.musinsa.ohj.presentation.response.brand.DeleteBrandResponse;
import com.musinsa.ohj.presentation.response.brand.UpdateBrandResponse;
import com.musinsa.ohj.presentation.v1.BrandController;
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

@WebMvcTest(BrandController.class)
public class BrandControllerTest extends ControllerTest {

    @MockBean
    BrandService brandService;

    private final String regApi = "/v1/brand";
    private final String updateApi = "/v1/brand/{brandSeq}";
    private final String deleteApi = "/v1/brand/{brandSeq}";

    @Test
    @DisplayName("브랜드 등록 API 성공")
    void 브랜드등록_성공() throws Exception {
        given(brandService.createBrand(any(CreateBrandRequest.class)))
                .willReturn(CreateBrandResponse.builder(ApiResponseCode.SUCCESS)
                        .build());
        mockMvc.perform(post(regApi)
                .content(jsonMapper.writeValueAsString(CreateBrandRequest.builder()
                        .brandNm("new상품")
                        .build()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", Matchers.equalTo(ApiResponseCode.SUCCESS.getCode())));
    }

    @Test
    @DisplayName("브랜드 등록 API 실패 파라미터유효하지않음")
    void 브랜드등록_실패_파라미터유효하지않음() throws Exception {
        mockMvc.perform(post(regApi)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", Matchers.equalTo(ApiResponseCode.BAD_REQUEST.getCode())));
        then(brandService).should(never()).createBrand(any());
        mockMvc.perform(post(regApi)
                .content(jsonMapper.writeValueAsString(CreateBrandRequest.builder()
                        .brandNm("")
                        .build()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", Matchers.equalTo(ApiResponseCode.BAD_REQUEST.getCode())));
        then(brandService).should(never()).createBrand(any());
    }

    @Test
    @DisplayName("브랜드 등록 API 실패 브랜드명이이미존재함")
    void 브랜드등록_실패_브랜드명이이미존재함() throws Exception {
        given(brandService.createBrand(any(CreateBrandRequest.class)))
                .willReturn(CreateBrandResponse.builder(ApiResponseCode.ALREADY_EXISTS_DATA)
                        .build());
        mockMvc.perform(post(regApi)
                .content(jsonMapper.writeValueAsString(CreateBrandRequest.builder()
                        .brandNm("new상품")
                        .build()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code", Matchers.equalTo(ApiResponseCode.ALREADY_EXISTS_DATA.getCode())));
    }

    @Test
    @DisplayName("브랜드 수정 API 성공")
    void 브랜드수정_성공() throws Exception {
        given(brandService.updateBrand(any(Long.class), any(UpdateBrandRequest.class)))
                .willReturn(UpdateBrandResponse.builder(ApiResponseCode.SUCCESS)
                        .build());
        mockMvc.perform(put(updateApi, 1l)
                .content(jsonMapper.writeValueAsString(UpdateBrandRequest.builder()
                        .brandNm("new상품")
                        .build()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", Matchers.equalTo(ApiResponseCode.SUCCESS.getCode())));
    }

    @Test
    @DisplayName("브랜드 수정 API 실패 파라미터유효하지않음")
    void 브랜드수정_실패_파라미터유효하지않음() throws Exception {
        mockMvc.perform(put(updateApi, 1l)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", Matchers.equalTo(ApiResponseCode.BAD_REQUEST.getCode())));
        then(brandService).should(never()).updateBrand(any(), any());
        mockMvc.perform(put(updateApi, 1l)
                .content(jsonMapper.writeValueAsString(UpdateBrandRequest.builder()
                        .brandNm("")
                        .build()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", Matchers.equalTo(ApiResponseCode.BAD_REQUEST.getCode())));
        then(brandService).should(never()).updateBrand(any(), any());
    }

    @Test
    @DisplayName("브랜드 수정 API 실패 특정데이터가이미존재하거나안함")
    void 브랜드수정_실패_특정데이터가이미존재하거나안함() throws Exception {
        given(brandService.updateBrand(any(Long.class), any(UpdateBrandRequest.class)))
                .willReturn(UpdateBrandResponse.builder(ApiResponseCode.NOT_FOUND_DATA)
                        .build());
        mockMvc.perform(put(updateApi, 1l)
                .content(jsonMapper.writeValueAsString(UpdateBrandRequest.builder()
                        .brandNm("new상품")
                        .build()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", Matchers.equalTo(ApiResponseCode.NOT_FOUND_DATA.getCode())));

        given(brandService.updateBrand(any(Long.class), any(UpdateBrandRequest.class)))
                .willReturn(UpdateBrandResponse.builder(ApiResponseCode.ALREADY_EXISTS_DATA)
                        .build());
        mockMvc.perform(put(updateApi, 1l)
                .content(jsonMapper.writeValueAsString(UpdateBrandRequest.builder()
                        .brandNm("new상품")
                        .build()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code", Matchers.equalTo(ApiResponseCode.ALREADY_EXISTS_DATA.getCode())));
    }

    @Test
    @DisplayName("브랜드 삭제 API 성공")
    void 브랜드삭제_성공() throws Exception {
        given(brandService.deleteBrand(any(Long.class), any(DeleteBrandRequest.class)))
                .willReturn(DeleteBrandResponse.builder(ApiResponseCode.SUCCESS)
                        .build());
        mockMvc.perform(delete(deleteApi, 1l)
                .content(jsonMapper.writeValueAsString(DeleteBrandRequest.builder()
                        .isProductBulkDelete(true)
                        .build()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", Matchers.equalTo(ApiResponseCode.SUCCESS.getCode())));
    }

    @Test
    @DisplayName("브랜드 삭제 API 실패 파라미터유효하지않음")
    void 브랜드삭제_실패_파라미터유효하지않음() throws Exception {
        mockMvc.perform(delete(deleteApi, 1l)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", Matchers.equalTo(ApiResponseCode.BAD_REQUEST.getCode())));
        then(brandService).should(never()).deleteBrand(any(), any());
        mockMvc.perform(delete(deleteApi, 1l)
                .content(jsonMapper.writeValueAsString(DeleteBrandRequest.builder()
                        .isProductBulkDelete(null)
                        .build()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", Matchers.equalTo(ApiResponseCode.BAD_REQUEST.getCode())));
        then(brandService).should(never()).deleteBrand(any(), any());
    }

    @Test
    @DisplayName("브랜드 삭제 API 실패 특정데이터가이미존재하거나안함")
    void 브랜드삭제_실패_특정데이터가이미존재하거나안함() throws Exception {
        given(brandService.deleteBrand(any(Long.class), any(DeleteBrandRequest.class)))
                .willReturn(DeleteBrandResponse.builder(ApiResponseCode.NOT_FOUND_DATA)
                        .build());
        mockMvc.perform(delete(deleteApi, 1l)
                .content(jsonMapper.writeValueAsString(DeleteBrandRequest.builder()
                        .isProductBulkDelete(true)
                        .build()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", Matchers.equalTo(ApiResponseCode.NOT_FOUND_DATA.getCode())));
        given(brandService.deleteBrand(any(Long.class), any(DeleteBrandRequest.class)))
                .willReturn(DeleteBrandResponse.builder(ApiResponseCode.ALREADY_EXISTS_DATA)
                        .build());
        mockMvc.perform(delete(deleteApi, 1l)
                .content(jsonMapper.writeValueAsString(DeleteBrandRequest.builder()
                        .isProductBulkDelete(true)
                        .build()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code", Matchers.equalTo(ApiResponseCode.ALREADY_EXISTS_DATA.getCode())));
    }
}
