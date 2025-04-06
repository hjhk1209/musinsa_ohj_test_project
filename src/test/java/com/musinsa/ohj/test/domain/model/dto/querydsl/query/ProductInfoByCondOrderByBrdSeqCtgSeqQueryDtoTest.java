package com.musinsa.ohj.test.domain.model.dto.querydsl.query;

import com.musinsa.ohj.domain.model.dto.querydsl.query.ProductInfoByCondOrderByBrdSeqCtgSeqQueryDto;
import com.musinsa.ohj.domain.model.dto.service.fetch.SumLowestPriceByBrdLowestCtgDto;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

public class ProductInfoByCondOrderByBrdSeqCtgSeqQueryDtoTest {

    @Test
    void toSumLowestPriceByBrdLowestCtgDtoTest() {
        ProductInfoByCondOrderByBrdSeqCtgSeqQueryDto productInfoByCondOrderByBrdSeqCtgSeqQueryDto =
                ProductInfoByCondOrderByBrdSeqCtgSeqQueryDto.builder()
                        .brandSeq(1l)
                        .categorySeq(1l)
                        .productSeq(1l)
                        .categoryNm("상의")
                        .brandNm("A")
                        .productNm("A-상의")
                        .productPrice(1000l)
                        .build();
        SumLowestPriceByBrdLowestCtgDto sumLowestPriceByBrdLowestCtgDto =
                productInfoByCondOrderByBrdSeqCtgSeqQueryDto.toSumLowestPriceByBrdLowestCtgDto();
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(sumLowestPriceByBrdLowestCtgDto.categoryNm())
                    .isEqualTo(productInfoByCondOrderByBrdSeqCtgSeqQueryDto.categoryNm());
            softAssertions.assertThat(sumLowestPriceByBrdLowestCtgDto.productPrice())
                    .isEqualTo("1,000");
        });
    }
}
