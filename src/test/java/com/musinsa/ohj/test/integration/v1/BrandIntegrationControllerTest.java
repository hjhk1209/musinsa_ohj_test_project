package com.musinsa.ohj.test.integration.v1;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.musinsa.ohj.domain.model.constants.ApiResponseCode;
import com.musinsa.ohj.presentation.request.brand.CreateBrandRequest;
import com.musinsa.ohj.presentation.request.brand.DeleteBrandRequest;
import com.musinsa.ohj.presentation.request.brand.UpdateBrandRequest;
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

public class BrandIntegrationControllerTest extends IntegrationControllerTest {

    private final JsonMapper jsonMapper = JsonMapper.builder().build();

    private final String regApi = "/v1/brand";
    private final String updateApi = "/v1/brand/{brandSeq}";
    private final String deleteApi = "/v1/brand/{brandSeq}";

    @Test
    @DisplayName("브랜드등록, 수정, 삭제 통합 테스트")
    void 브랜드등록_수정_삭제_통합테스트() throws Exception {
        mockMvc.perform(post(regApi)
                .content(jsonMapper.writeValueAsString(CreateBrandRequest.builder()
                        .brandNm("new브랜드")
                        .build()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", Matchers.equalTo(ApiResponseCode.SUCCESS.getCode())));
        mockMvc.perform(put(updateApi, 10l)
                .content(jsonMapper.writeValueAsString(UpdateBrandRequest.builder()
                        .brandNm("update브랜드")
                        .build()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", Matchers.equalTo(ApiResponseCode.SUCCESS.getCode())));
        mockMvc.perform(delete(deleteApi, 10l)
                .content(jsonMapper.writeValueAsString(DeleteBrandRequest.builder()
                        .isProductBulkDelete(true)
                        .build()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", Matchers.equalTo(ApiResponseCode.SUCCESS.getCode())));
    }

}
