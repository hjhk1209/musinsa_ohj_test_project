package com.musinsa.ohj.test.integration.v1;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.musinsa.ohj.domain.model.constants.ApiResponseCode;
import com.musinsa.ohj.presentation.request.product.CreateProductRequest;
import com.musinsa.ohj.presentation.request.product.UpdateProductRequest;
import com.musinsa.ohj.test.integration.IntegrationControllerTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProductIntegrationControllerTest extends IntegrationControllerTest {

    private final JsonMapper jsonMapper = JsonMapper.builder().build();

    private final String regApi = "/v1/brand/{brandSeq}/category/{categorySeq}/product";
    private final String updateApi = "/v1/brand/{brandSeq}/category/{categorySeq}/product/{productSeq}";
    private final String deleteApi = "/v1/brand/{brandSeq}/category/{categorySeq}/product/{productSeq}";

    @Test
    @DisplayName("상품등록, 수정, 삭제 통합 테스트")
    void 상품등록_수정_삭제_통합테스트() throws Exception {
        mockMvc.perform(post(regApi, 9l, 1l)
                .content(jsonMapper.writeValueAsString(CreateProductRequest.builder()
                        .productNm("new 상품")
                        .productPrice(8000l)
                        .build()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", Matchers.equalTo(ApiResponseCode.SUCCESS.getCode())));
        mockMvc.perform(put(updateApi, 9l, 1l, 73l)
                .content(jsonMapper.writeValueAsString(UpdateProductRequest.builder()
                        .productNm("update상품")
                        .productPrice(8000l)
                        .brandSeq(9l)
                        .categorySeq(1l)
                        .build()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", Matchers.equalTo(ApiResponseCode.SUCCESS.getCode())));
        mockMvc.perform(delete(deleteApi, 9l, 1l, 73l)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", Matchers.equalTo(ApiResponseCode.SUCCESS.getCode())));
    }
}
