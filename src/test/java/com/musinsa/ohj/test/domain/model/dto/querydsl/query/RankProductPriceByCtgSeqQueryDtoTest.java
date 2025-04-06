package com.musinsa.ohj.test.domain.model.dto.querydsl.query;

import com.musinsa.ohj.domain.model.dto.querydsl.query.RankProductPriceByCtgSeqQueryDto;
import com.musinsa.ohj.domain.model.dto.service.fetch.HighestPriceAndLowestPriceByCategoryNmItemDto;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

public class RankProductPriceByCtgSeqQueryDtoTest {

    @Test
    void toHighestPriceAndLowestPriceByCategoryNmItemDtoTest() {
        RankProductPriceByCtgSeqQueryDto rankProductPriceByCtgSeqQueryDto =
                RankProductPriceByCtgSeqQueryDto.builder()
                        .productSeq(1l)
                        .brandNm("A")
                        .productPrice(1000l)
                        .rank(1)
                        .build();
        HighestPriceAndLowestPriceByCategoryNmItemDto highestPriceAndLowestPriceByCategoryNmItemDto =
                rankProductPriceByCtgSeqQueryDto.toHighestPriceAndLowestPriceByCategoryNmItemDto();
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(highestPriceAndLowestPriceByCategoryNmItemDto.brandNm())
                    .isEqualTo(rankProductPriceByCtgSeqQueryDto.brandNm());
            softAssertions.assertThat(highestPriceAndLowestPriceByCategoryNmItemDto.productPrice())
                    .isEqualTo("1,000");
        });
    }
}
