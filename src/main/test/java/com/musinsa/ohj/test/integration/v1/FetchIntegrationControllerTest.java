package com.musinsa.ohj.test.integration.v1;

import com.musinsa.ohj.domain.model.constants.ApiResponseCode;
import com.musinsa.ohj.test.integration.IntegrationControllerTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FetchIntegrationControllerTest extends IntegrationControllerTest {

    private final String fetchSummaryLowestPriceByCtgAndBrdApi = "/v1/summary/lowest-price/by-category-brand";
    private final String fetchSumLowestPriceByBrdApi = "/v1/summary/sum-lowest-price/by-brand";
    private final String fetchHighestPriceAndLowestPriceByCategoryNmApi = "/v1/category/highest-price-lowest-price";

    @Test
    @DisplayName("구현1 통합 테스트")
    void 구현1_통합테스트() throws Exception {
        mockMvc.perform(get(fetchSummaryLowestPriceByCtgAndBrdApi))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", Matchers.equalTo(ApiResponseCode.SUCCESS.getCode())))
                .andExpect(jsonPath("$.data.list", Matchers.hasSize(8)))
                .andExpect(jsonPath("$.data.list[0].categoryNm", Matchers.equalTo("상의")))
                .andExpect(jsonPath("$.data.list[0].brandNmList", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.data.list[0].productPrice", Matchers.equalTo("10,000")))
                .andExpect(jsonPath("$.data.totalProductPrice", Matchers.equalTo("34,100")));
    }

    @Test
    @DisplayName("구현2 통합 테스트")
    void 구현2_통합테스트() throws Exception {
        mockMvc.perform(get(fetchSumLowestPriceByBrdApi))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", Matchers.equalTo(ApiResponseCode.SUCCESS.getCode())))
                .andExpect(jsonPath("$.data", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.data[0].lowest.brandNm", Matchers.equalTo("D")))
                .andExpect(jsonPath("$.data[0].lowest.totalProductPrice", Matchers.equalTo("36,100")))
                .andExpect(jsonPath("$.data[0].lowest.categoryList", Matchers.hasSize(8)))
                .andExpect(jsonPath("$.data[0].lowest.categoryList[0].categoryNm", Matchers.equalTo("상의")))
                .andExpect(jsonPath("$.data[0].lowest.categoryList[0].productPrice", Matchers.equalTo("10,100")));
    }

    @Test
    @DisplayName("구현3 통합 테스트")
    void 구현3_통합테스트() throws Exception {
        String categoryNm = "상의";
        mockMvc.perform(get(fetchHighestPriceAndLowestPriceByCategoryNmApi)
                .param("categoryNm", categoryNm))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", Matchers.equalTo(ApiResponseCode.SUCCESS.getCode())))
                .andExpect(jsonPath("$.data.categoryNm", Matchers.equalTo("상의")))
                .andExpect(jsonPath("$.data.lowestList", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.data.lowestList[0].brandNm", Matchers.equalTo("C")))
                .andExpect(jsonPath("$.data.lowestList[0].productPrice", Matchers.equalTo("10,000")))
                .andExpect(jsonPath("$.data.highestList", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.data.highestList[0].brandNm", Matchers.equalTo("I")))
                .andExpect(jsonPath("$.data.highestList[0].productPrice", Matchers.equalTo("11,400")));
    }
}
