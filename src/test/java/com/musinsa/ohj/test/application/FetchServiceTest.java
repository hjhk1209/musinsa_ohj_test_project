package com.musinsa.ohj.test.application;

import com.musinsa.ohj.application.FetchService;
import com.musinsa.ohj.domain.model.constants.ApiResponseCode;
import com.musinsa.ohj.domain.model.dto.querydsl.param.ProductInfoByCondOrderByBrdSeqCtgSeqParamDto;
import com.musinsa.ohj.domain.model.dto.querydsl.query.ProductInfoByCondOrderByBrdSeqCtgSeqQueryDto;
import com.musinsa.ohj.domain.model.dto.querydsl.query.Rank1SumOfBrdLowestPriceQueryDto;
import com.musinsa.ohj.domain.model.dto.querydsl.query.RankProductPriceByCtgSeqQueryDto;
import com.musinsa.ohj.domain.model.dto.querydsl.query.SummaryLowestPriceByCtgAndBrdQueryDto;
import com.musinsa.ohj.domain.model.dto.service.fetch.SumLowestPriceByBrdDto;
import com.musinsa.ohj.domain.model.dto.service.fetch.SummaryLowestPriceByCtgAndBrdItemDto;
import com.musinsa.ohj.domain.model.entity.Category;
import com.musinsa.ohj.infrastructure.database.querydsl.ProductFetchQueryDslImpl;
import com.musinsa.ohj.infrastructure.database.repository.CategoryJpaRepository;
import com.musinsa.ohj.presentation.response.fetch.HighestPriceAndLowestPriceByCategoryNmResponse;
import com.musinsa.ohj.presentation.response.fetch.SumLowestPriceByBrdResponse;
import com.musinsa.ohj.presentation.response.fetch.SummaryLowestPriceByCtgAndBrdResponse;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class FetchServiceTest {
    @Mock
    ProductFetchQueryDslImpl productFetchQueryDslImpl;
    @Mock
    CategoryJpaRepository categoryJpaRepository;
    @InjectMocks
    FetchService fetchService;

    @Test
    @DisplayName("구현1 테스트")
    void 구현1_조회성공() throws Exception {
        List<SummaryLowestPriceByCtgAndBrdQueryDto> summaryLowestPriceByCtgAndBrdQueryDtos =
                List.of(SummaryLowestPriceByCtgAndBrdQueryDto.builder()
                                .categorySeq(1l)
                                .categoryNm("상의")
                                .brandNm("C")
                                .productPrice(10000l)
                                .build(),
                        SummaryLowestPriceByCtgAndBrdQueryDto.builder()
                                .categorySeq(2l)
                                .categoryNm("아우터")
                                .brandNm("E")
                                .productPrice(5000l)
                                .build());
        given(productFetchQueryDslImpl.findSummaryLowestPriceByCtgAndBrd())
                .willReturn(summaryLowestPriceByCtgAndBrdQueryDtos);

        SummaryLowestPriceByCtgAndBrdResponse summaryLowestPriceByCtgAndBrdResponse
                = fetchService.fetchSummaryLowestPriceByCtgAndBrd();
        SummaryLowestPriceByCtgAndBrdItemDto summaryLowestPriceByCtgAndBrdItemDto
                = summaryLowestPriceByCtgAndBrdResponse.getData().list().get(0);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(summaryLowestPriceByCtgAndBrdResponse.getCode())
                    .isEqualTo(ApiResponseCode.SUCCESS.getCode());
            softAssertions.assertThat(summaryLowestPriceByCtgAndBrdResponse.getData()
                    .list().size())
                    .isEqualTo(2);
            softAssertions.assertThat(summaryLowestPriceByCtgAndBrdItemDto.categoryNm())
                    .isEqualTo("상의");
            softAssertions.assertThat(summaryLowestPriceByCtgAndBrdItemDto.productPrice())
                    .isEqualTo("10,000");
            softAssertions.assertThat(summaryLowestPriceByCtgAndBrdItemDto.brandNmList().size())
                    .isEqualTo(1);
            softAssertions.assertThat(summaryLowestPriceByCtgAndBrdResponse.getData()
                    .totalProductPrice())
                    .isEqualTo("15,000");
        });
    }

    @Test
    @DisplayName("구현1 결과없음 테스트")
    void 구현1_조회성공_결과없음() throws Exception {
        given(productFetchQueryDslImpl.findSummaryLowestPriceByCtgAndBrd())
                .willReturn(List.of());

        SummaryLowestPriceByCtgAndBrdResponse summaryLowestPriceByCtgAndBrdResponse
                = fetchService.fetchSummaryLowestPriceByCtgAndBrd();

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(summaryLowestPriceByCtgAndBrdResponse.getCode())
                    .isEqualTo(ApiResponseCode.SUCCESS.getCode());
            softAssertions.assertThat(summaryLowestPriceByCtgAndBrdResponse.getData())
                    .isNull();
        });
    }

    @Test
    @DisplayName("구현2 테스트")
    void 구현2_조회성공() throws Exception {
        List<Rank1SumOfBrdLowestPriceQueryDto> rank1SumOfBrdLowestPriceQueryDtos =
                List.of(Rank1SumOfBrdLowestPriceQueryDto.builder()
                        .brandSeq(4l)
                        .sumProductPrice(15200l)
                        .rank(1)
                        .build());
        given(productFetchQueryDslImpl.findRank1SumOfBrdLowestPrice())
                .willReturn(rank1SumOfBrdLowestPriceQueryDtos);

        List<ProductInfoByCondOrderByBrdSeqCtgSeqQueryDto> productInfoByCondOrderByBrdSeqCtgSeqQueryDtos =
                List.of(ProductInfoByCondOrderByBrdSeqCtgSeqQueryDto.builder()
                                .productSeq(25l)
                                .brandSeq(4l)
                                .categorySeq(1l)
                                .brandNm("D")
                                .categoryNm("상의")
                                .productNm("D-상의")
                                .productPrice(10100l)
                                .build(),
                        ProductInfoByCondOrderByBrdSeqCtgSeqQueryDto.builder()
                                .productSeq(26l)
                                .brandSeq(4l)
                                .categorySeq(2l)
                                .brandNm("D")
                                .categoryNm("아우터")
                                .productNm("D-아우터")
                                .productPrice(5100l)
                                .build());
        given(productFetchQueryDslImpl.findProductInfoByCondOrderByBrdSeqCtgSeq(any(ProductInfoByCondOrderByBrdSeqCtgSeqParamDto.class)))
                .willReturn(productInfoByCondOrderByBrdSeqCtgSeqQueryDtos);

        SumLowestPriceByBrdResponse<SumLowestPriceByBrdDto> sumLowestPriceByBrdResponse = fetchService.fetchSumLowestPriceByBrd();
        SumLowestPriceByBrdDto sumLowestPriceByBrdDto = sumLowestPriceByBrdResponse.getData().get(0);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(sumLowestPriceByBrdResponse.getCode())
                    .isEqualTo(ApiResponseCode.SUCCESS.getCode());
            softAssertions.assertThat(sumLowestPriceByBrdResponse.getData().size())
                    .isEqualTo(1);
            softAssertions.assertThat(sumLowestPriceByBrdDto.lowest().brandNm())
                    .isEqualTo("D");
            softAssertions.assertThat(sumLowestPriceByBrdDto.lowest().totalProductPrice())
                    .isEqualTo("15,200");
            softAssertions.assertThat(sumLowestPriceByBrdDto.lowest().categoryList().size())
                    .isEqualTo(2);
            softAssertions.assertThat(sumLowestPriceByBrdDto.lowest().categoryList().get(0).categoryNm())
                    .isEqualTo("상의");
            softAssertions.assertThat(sumLowestPriceByBrdDto.lowest().categoryList().get(0).productPrice())
                    .isEqualTo("10,100");
        });
    }

    @Test
    @DisplayName("구현2 결과없음 테스트")
    void 구현2_조회성공_결과없음() throws Exception {
        given(productFetchQueryDslImpl.findRank1SumOfBrdLowestPrice())
                .willReturn(List.of());

        SumLowestPriceByBrdResponse<SumLowestPriceByBrdDto> sumLowestPriceByBrdResponse = fetchService.fetchSumLowestPriceByBrd();
        then(productFetchQueryDslImpl).should(never())
                .findProductInfoByCondOrderByBrdSeqCtgSeq(any(ProductInfoByCondOrderByBrdSeqCtgSeqParamDto.class));
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(sumLowestPriceByBrdResponse.getCode())
                    .isEqualTo(ApiResponseCode.SUCCESS.getCode());
            softAssertions.assertThat(sumLowestPriceByBrdResponse.getData())
                    .isNull();
        });
    }

    @Test
    @DisplayName("구현3 테스트")
    void 구현3_조회성공() throws Exception {
        given(categoryJpaRepository.findByCategoryNm(any(String.class)))
                .willReturn(Optional.ofNullable(Category.builder()
                        .categorySeq(1l)
                        .categoryNm("상의")
                        .build()));
        given(productFetchQueryDslImpl.findRankProductPriceByCtgSeq(any(Long.class)))
                .willReturn(List.of(
                        RankProductPriceByCtgSeqQueryDto.builder()
                                .productSeq(17l)
                                .productPrice(10000l)
                                .brandNm("C")
                                .rank(1)
                                .build(),
                        RankProductPriceByCtgSeqQueryDto.builder()
                                .productSeq(25l)
                                .productPrice(10100l)
                                .brandNm("D")
                                .rank(2)
                                .build(),
                        RankProductPriceByCtgSeqQueryDto.builder()
                                .productSeq(9l)
                                .productPrice(10500l)
                                .brandNm("B")
                                .rank(3)
                                .build()));

        HighestPriceAndLowestPriceByCategoryNmResponse highestPriceAndLowestPriceByCategoryNmResponse =
                fetchService.fetchHighestPriceAndLowestPriceByCategoryNm("상의");

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(highestPriceAndLowestPriceByCategoryNmResponse.getCode())
                    .isEqualTo(ApiResponseCode.SUCCESS.getCode());
            softAssertions.assertThat(highestPriceAndLowestPriceByCategoryNmResponse.getData().categoryNm())
                    .isEqualTo("상의");
            softAssertions.assertThat(highestPriceAndLowestPriceByCategoryNmResponse.getData().highestList().size())
                    .isEqualTo(1);
            softAssertions.assertThat(highestPriceAndLowestPriceByCategoryNmResponse.getData().highestList()
                    .get(0)
                    .brandNm())
                    .isEqualTo("B");
            softAssertions.assertThat(highestPriceAndLowestPriceByCategoryNmResponse.getData().highestList()
                    .get(0)
                    .productPrice())
                    .isEqualTo("10,500");
            softAssertions.assertThat(highestPriceAndLowestPriceByCategoryNmResponse.getData().lowestList().size())
                    .isEqualTo(1);
            softAssertions.assertThat(highestPriceAndLowestPriceByCategoryNmResponse.getData().lowestList()
                    .get(0)
                    .brandNm())
                    .isEqualTo("C");
            softAssertions.assertThat(highestPriceAndLowestPriceByCategoryNmResponse.getData().lowestList()
                    .get(0)
                    .productPrice())
                    .isEqualTo("10,000");
        });
    }

    @Test
    @DisplayName("구현3 결과없음 테스트")
    void 구현3_조회성공_결과없음() throws Exception {
        given(categoryJpaRepository.findByCategoryNm(any(String.class)))
                .willReturn(Optional.empty());

        HighestPriceAndLowestPriceByCategoryNmResponse highestPriceAndLowestPriceByCategoryNmResponse =
                fetchService.fetchHighestPriceAndLowestPriceByCategoryNm("상의");
        then(productFetchQueryDslImpl).should(never()).findRankProductPriceByCtgSeq(any(Long.class));
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(highestPriceAndLowestPriceByCategoryNmResponse.getCode())
                    .isEqualTo(ApiResponseCode.SUCCESS.getCode());
            softAssertions.assertThat(highestPriceAndLowestPriceByCategoryNmResponse.getData())
                    .isNull();
        });
    }
}
