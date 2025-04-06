package com.musinsa.ohj.test.domain.model.dto.querydsl.query;

import com.musinsa.ohj.domain.model.dto.querydsl.query.ProductInfoByCondOrderByBrdSeqCtgSeqQueryDto;
import com.musinsa.ohj.domain.model.dto.querydsl.query.Rank1SumOfBrdLowestPriceQueryDto;
import com.musinsa.ohj.domain.model.dto.service.fetch.SumLowestPriceByBrdDto;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Rank1SumOfBrdLowestPriceQueryDtoTest {

    @Test
    void toSumLowestPriceByBrdDtoTest() {
        Rank1SumOfBrdLowestPriceQueryDto rank1SumOfBrdLowestPriceQueryDto =
                Rank1SumOfBrdLowestPriceQueryDto.builder()
                        .brandSeq(7l)
                        .sumProductPrice(15000l)
                        .rank(1)
                        .build();
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
        SumLowestPriceByBrdDto sumLowestPriceByBrdDto =
                rank1SumOfBrdLowestPriceQueryDto.toSumLowestPriceByBrdDto(productInfoByCondMap);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(sumLowestPriceByBrdDto.lowest().brandNm())
                    .isEqualTo("G");
            softAssertions.assertThat(sumLowestPriceByBrdDto.lowest().totalProductPrice())
                    .isEqualTo("15,000");
            softAssertions.assertThat(sumLowestPriceByBrdDto.lowest().categoryList().size())
                    .isEqualTo(2);
        });
    }
}
