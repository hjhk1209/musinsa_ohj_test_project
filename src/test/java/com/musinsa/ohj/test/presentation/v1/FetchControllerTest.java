package com.musinsa.ohj.test.presentation.v1;

import com.musinsa.ohj.application.FetchService;
import com.musinsa.ohj.domain.model.constants.ApiResponseCode;
import com.musinsa.ohj.domain.model.dto.querydsl.query.ProductInfoByCondOrderByBrdSeqCtgSeqQueryDto;
import com.musinsa.ohj.domain.model.dto.querydsl.query.Rank1SumOfBrdLowestPriceQueryDto;
import com.musinsa.ohj.domain.model.dto.service.fetch.HighestPriceAndLowestPriceByCategoryNmDto;
import com.musinsa.ohj.domain.model.dto.service.fetch.HighestPriceAndLowestPriceByCategoryNmItemDto;
import com.musinsa.ohj.domain.model.dto.service.fetch.SummaryLowestPriceByCtgAndBrdDto;
import com.musinsa.ohj.domain.model.dto.service.fetch.SummaryLowestPriceByCtgAndBrdItemDto;
import com.musinsa.ohj.presentation.response.fetch.HighestPriceAndLowestPriceByCategoryNmResponse;
import com.musinsa.ohj.presentation.response.fetch.SumLowestPriceByBrdResponse;
import com.musinsa.ohj.presentation.response.fetch.SummaryLowestPriceByCtgAndBrdResponse;
import com.musinsa.ohj.presentation.v1.FetchController;
import com.musinsa.ohj.test.presentation.ControllerTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FetchController.class)
public class FetchControllerTest extends ControllerTest {

    @MockBean
    FetchService fetchService;

    private final String fetchSummaryLowestPriceByCtgAndBrdApi = "/v1/summary/lowest-price/by-category-brand";
    private final String fetchSumLowestPriceByBrdApi = "/v1/summary/sum-lowest-price/by-brand";
    private final String fetchHighestPriceAndLowestPriceByCategoryNmApi = "/v1/category/highest-price-lowest-price";

    @Test
    @DisplayName("구현1 API")
    void 구현1() throws Exception {
        List<SummaryLowestPriceByCtgAndBrdItemDto> summaryLowestPriceByCtgAndBrdItemDtos =
                List.of(SummaryLowestPriceByCtgAndBrdItemDto.builder()
                                .categoryNm("상의")
                                .brandNmList(Set.of("C"))
                                .productPrice(String.format("%,d", 900))
                                .build(),
                        SummaryLowestPriceByCtgAndBrdItemDto.builder()
                                .categoryNm("스니커즈")
                                .brandNmList(Set.of("A", "G"))
                                .productPrice(String.format("%,d", 9100))
                                .build());
        given(fetchService.fetchSummaryLowestPriceByCtgAndBrd())
                .willReturn(SummaryLowestPriceByCtgAndBrdResponse.builder(ApiResponseCode.SUCCESS)
                        .data(SummaryLowestPriceByCtgAndBrdDto.builder()
                                .list(summaryLowestPriceByCtgAndBrdItemDtos)
                                .totalProductPrice(String.format("%,d", 10000l))
                                .build())
                        .build());
        mockMvc.perform(get(fetchSummaryLowestPriceByCtgAndBrdApi))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", Matchers.equalTo(ApiResponseCode.SUCCESS.getCode())))
                .andExpect(jsonPath("$.data.list[0].categoryNm", Matchers.equalTo("상의")))
                .andExpect(jsonPath("$.data.list[0].brandNmList", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.data.list[0].productPrice", Matchers.equalTo("900")))
                .andExpect(jsonPath("$.data.list[1].categoryNm", Matchers.equalTo("스니커즈")))
                .andExpect(jsonPath("$.data.list[1].brandNmList", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.data.list[1].productPrice", Matchers.equalTo("9,100")))
                .andExpect(jsonPath("$.data.totalProductPrice", Matchers.equalTo("10,000")));
    }

    @Test
    @DisplayName("구현2 API")
    void 구현2() throws Exception {
        List<Rank1SumOfBrdLowestPriceQueryDto> rank1SumOfBrdLowestPriceQueryDtos =
                List.of(Rank1SumOfBrdLowestPriceQueryDto.builder()
                                .brandSeq(4l)
                                .sumProductPrice(15000l)
                                .rank(1)
                                .build(),
                        Rank1SumOfBrdLowestPriceQueryDto.builder()
                                .brandSeq(7l)
                                .sumProductPrice(15000l)
                                .rank(1)
                                .build());

        Map<Long, List<ProductInfoByCondOrderByBrdSeqCtgSeqQueryDto>> productInfoByCondMap =
                List.of(ProductInfoByCondOrderByBrdSeqCtgSeqQueryDto.builder()
                                .brandSeq(4l)
                                .brandNm("D")
                                .categoryNm("상의")
                                .productPrice(10000l)
                                .build(),
                        ProductInfoByCondOrderByBrdSeqCtgSeqQueryDto.builder()
                                .brandSeq(4l)
                                .brandNm("D")
                                .categoryNm("스니커즈")
                                .productPrice(5000l)
                                .build(),
                        ProductInfoByCondOrderByBrdSeqCtgSeqQueryDto.builder()
                                .brandSeq(7l)
                                .brandNm("G")
                                .categoryNm("상의")
                                .productPrice(11000l)
                                .build(),
                        ProductInfoByCondOrderByBrdSeqCtgSeqQueryDto.builder()
                                .brandSeq(7l)
                                .brandNm("G")
                                .categoryNm("스니커즈")
                                .productPrice(4000l)
                                .build())
                        .stream()
                        .collect(Collectors.groupingBy(ProductInfoByCondOrderByBrdSeqCtgSeqQueryDto::brandSeq));

        given(fetchService.fetchSumLowestPriceByBrd())
                .willReturn(SumLowestPriceByBrdResponse.builder(ApiResponseCode.SUCCESS)
                        .data(rank1SumOfBrdLowestPriceQueryDtos.stream()
                                .map(v -> v.toSumLowestPriceByBrdDto(productInfoByCondMap))
                                .collect(Collectors.toList()))
                        .build());
        mockMvc.perform(get(fetchSumLowestPriceByBrdApi))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", Matchers.equalTo(ApiResponseCode.SUCCESS.getCode())))
                .andExpect(jsonPath("$.data[0].lowest.brandNm", Matchers.equalTo("D")))
                .andExpect(jsonPath("$.data[0].lowest.totalProductPrice", Matchers.equalTo("15,000")))
                .andExpect(jsonPath("$.data[0].lowest.categoryList", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.data[0].lowest.categoryList[0].categoryNm", Matchers.equalTo("상의")))
                .andExpect(jsonPath("$.data[0].lowest.categoryList[0].productPrice", Matchers.equalTo("10,000")))
                .andExpect(jsonPath("$.data[0].lowest.categoryList[1].categoryNm", Matchers.equalTo("스니커즈")))
                .andExpect(jsonPath("$.data[0].lowest.categoryList[1].productPrice", Matchers.equalTo("5,000")))
                .andExpect(jsonPath("$.data[1].lowest.brandNm", Matchers.equalTo("G")))
                .andExpect(jsonPath("$.data[1].lowest.totalProductPrice", Matchers.equalTo("15,000")))
                .andExpect(jsonPath("$.data[1].lowest.categoryList", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.data[1].lowest.categoryList[0].categoryNm", Matchers.equalTo("상의")))
                .andExpect(jsonPath("$.data[1].lowest.categoryList[0].productPrice", Matchers.equalTo("11,000")))
                .andExpect(jsonPath("$.data[1].lowest.categoryList[1].categoryNm", Matchers.equalTo("스니커즈")))
                .andExpect(jsonPath("$.data[1].lowest.categoryList[1].productPrice", Matchers.equalTo("4,000")));
    }

    @Test
    @DisplayName("구현3 API")
    void 구현3() throws Exception {
        String categoryNm = "상의";
        given(fetchService.fetchHighestPriceAndLowestPriceByCategoryNm(any(String.class)))
                .willReturn(HighestPriceAndLowestPriceByCategoryNmResponse.builder(ApiResponseCode.SUCCESS)
                        .data(HighestPriceAndLowestPriceByCategoryNmDto.builder()
                                .categoryNm(categoryNm)
                                .lowestList(List.of(HighestPriceAndLowestPriceByCategoryNmItemDto.builder()
                                                .brandNm("C")
                                                .productPrice(String.format("%,d", 1500))
                                                .build(),
                                        HighestPriceAndLowestPriceByCategoryNmItemDto.builder()
                                                .brandNm("D")
                                                .productPrice(String.format("%,d", 1500))
                                                .build()))
                                .highestList(List.of(HighestPriceAndLowestPriceByCategoryNmItemDto.builder()
                                        .brandNm("A")
                                        .productPrice(String.format("%,d", 5000))
                                        .build()))
                                .build())
                        .build());

        mockMvc.perform(get(fetchHighestPriceAndLowestPriceByCategoryNmApi)
                .param("categoryNm", categoryNm))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", Matchers.equalTo(ApiResponseCode.SUCCESS.getCode())))
                .andExpect(jsonPath("$.data.categoryNm", Matchers.equalTo(categoryNm)))
                .andExpect(jsonPath("$.data.lowestList", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.data.lowestList[0].brandNm", Matchers.equalTo("C")))
                .andExpect(jsonPath("$.data.lowestList[0].productPrice", Matchers.equalTo("1,500")))
                .andExpect(jsonPath("$.data.lowestList[1].brandNm", Matchers.equalTo("D")))
                .andExpect(jsonPath("$.data.lowestList[1].productPrice", Matchers.equalTo("1,500")))
                .andExpect(jsonPath("$.data.highestList", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.data.highestList[0].brandNm", Matchers.equalTo("A")))
                .andExpect(jsonPath("$.data.highestList[0].productPrice", Matchers.equalTo("5,000")));
    }
}
