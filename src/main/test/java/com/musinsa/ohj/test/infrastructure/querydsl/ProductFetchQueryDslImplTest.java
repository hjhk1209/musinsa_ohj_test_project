package com.musinsa.ohj.test.infrastructure.querydsl;

import com.musinsa.ohj.domain.model.dto.querydsl.param.ProductInfoByCondOrderByBrdSeqCtgSeqParamDto;
import com.musinsa.ohj.domain.model.dto.querydsl.query.ProductInfoByCondOrderByBrdSeqCtgSeqQueryDto;
import com.musinsa.ohj.domain.model.dto.querydsl.query.Rank1SumOfBrdLowestPriceQueryDto;
import com.musinsa.ohj.domain.model.dto.querydsl.query.RankProductPriceByCtgSeqQueryDto;
import com.musinsa.ohj.domain.model.dto.querydsl.query.SummaryLowestPriceByCtgAndBrdQueryDto;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ProductFetchQueryDslImplTest extends QuerydslTest {

    @Test
    @DisplayName("카테고리별 최저가격 상품 및 브랜드 목록 (정렬은 카테고리 오름차순) 쿼리 테스트")
    void findSummaryLowestPriceByCtgAndBrdTest() {
        List<SummaryLowestPriceByCtgAndBrdQueryDto> list = productFetchQueryDslImpl.findSummaryLowestPriceByCtgAndBrd();
        Map<Long, SummaryLowestPriceByCtgAndBrdQueryDto> summary
                = list.stream().collect(Collectors.toMap(SummaryLowestPriceByCtgAndBrdQueryDto::categorySeq,
                Function.identity(),
                (key1, key2) -> key1));
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(list.size()).isEqualTo(9);
            softAssertions.assertThat(list.get(0).categorySeq())
                    .isEqualTo(1);
            softAssertions.assertThat(list.get(list.size() - 1).categorySeq())
                    .isEqualTo(8);
            softAssertions.assertThat(summary.size()).isEqualTo(8);
            softAssertions.assertThat(summary.entrySet()
                    .stream()
                    .mapToLong(v -> v.getValue().productPrice())
                    .sum())
                    .isEqualTo(34100l);
        });
    }

    @Test
    @DisplayName("각 브랜드에서 판매하는 모든 상품들의 합계가 최저가인 1순위 브랜드들 목록 쿼리 테스트")
    void findRank1SumOfBrdLowestPriceTest() {
        List<Rank1SumOfBrdLowestPriceQueryDto> list = productFetchQueryDslImpl.findRank1SumOfBrdLowestPrice();
        Rank1SumOfBrdLowestPriceQueryDto data = list.stream().findAny().get();
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(list.size()).isEqualTo(1);
            softAssertions.assertThat(data.rank()).isEqualTo(1);
            softAssertions.assertThat(data.brandSeq()).isEqualTo(4);
            softAssertions.assertThat(data.sumProductPrice()).isEqualTo(36100);
        });
    }

    @Test
    @DisplayName("각종 조건으로 상품 정보 목록 (브랜드, 카테고리 오름차순) 쿼리 테스트")
    void findProductInfoByCondOrderByBrdSeqCtgSeqTest() {
        List<ProductInfoByCondOrderByBrdSeqCtgSeqQueryDto> list
                = productFetchQueryDslImpl.findProductInfoByCondOrderByBrdSeqCtgSeq(
                ProductInfoByCondOrderByBrdSeqCtgSeqParamDto.builder()
                        .brandSeqs(List.of(4l, 5l))
                        .build());
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(list.size()).isEqualTo(16);
            softAssertions.assertThat(list.get(0).brandSeq()).isEqualTo(4);
            softAssertions.assertThat(list.get(0).categorySeq()).isEqualTo(1);
            softAssertions.assertThat(list.get(list.size() - 1).brandSeq()).isEqualTo(5);
            softAssertions.assertThat(list.get(list.size() - 1).categorySeq()).isEqualTo(8);
        });
    }

    @Test
    @DisplayName("특정 카테고리에서 상품 가격이 낮은 순 랭킹 및 정보 목록 쿼리 테스트")
    void findRankProductPriceByCtgSeqTest() {
        List<RankProductPriceByCtgSeqQueryDto> list =
                productFetchQueryDslImpl.findRankProductPriceByCtgSeq(1l);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(list.size()).isEqualTo(9);
            softAssertions.assertThat(list.get(0).rank()).isEqualTo(1);
            softAssertions.assertThat(list.get(0).brandNm()).isEqualTo("C");
            softAssertions.assertThat(list.get(0).productPrice()).isEqualTo(10000);
            softAssertions.assertThat(list.get(list.size() - 1).rank()).isEqualTo(9);
            softAssertions.assertThat(list.get(list.size() - 1).brandNm()).isEqualTo("I");
            softAssertions.assertThat(list.get(list.size() - 1).productPrice()).isEqualTo(11400);
        });
    }
}
