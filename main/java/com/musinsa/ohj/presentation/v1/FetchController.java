package com.musinsa.ohj.presentation.v1;

import com.musinsa.ohj.application.FetchService;
import com.musinsa.ohj.presentation.response.ApiResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class FetchController {

    FetchService fetchService;

    /** 카테고리 별 최저가격 브랜드 현황 및 총액을 조회하기 위한 API **/
    @GetMapping("/v1/summary/lowest-price/by-category-brand")
    public ResponseEntity<ApiResponse> fetchSummaryLowestPriceByCtgAndBrd() {
        return fetchService.fetchSummaryLowestPriceByCtgAndBrd()
                .toResponse();
    }

    /** 단일 브랜드에서 모든 카테고리 상품 합계가 최저가인 브랜드 및 각 카테고리 상품 가격, 총액을 조회하기 위한 API **/
    @GetMapping("/v1/summary/sum-lowest-price/by-brand")
    public ResponseEntity<ApiResponse> fetchSumLowestPriceByBrd() {
        return fetchService.fetchSumLowestPriceByBrd()
                .toResponse();
    }

    /** 특정 카테고리명에서 최저 및 최고가로 판매하는 브랜드들의 상품 현황을 조회하기 위한 API **/
    @GetMapping("/v1/category/highest-price-lowest-price")
    public ResponseEntity<ApiResponse> fetchHighestPriceAndLowestPriceByCategoryNm(@RequestParam @NotBlank String categoryNm) {
        return fetchService.fetchHighestPriceAndLowestPriceByCategoryNm(categoryNm)
                .toResponse();
    }
}
