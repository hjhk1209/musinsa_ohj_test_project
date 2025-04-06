package com.musinsa.ohj.domain.model.dto.querydsl.query;

import com.musinsa.ohj.domain.model.dto.service.fetch.HighestPriceAndLowestPriceByCategoryNmItemDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

public record RankProductPriceByCtgSeqQueryDto(Long productSeq,
                                               String brandNm,
                                               Long productPrice,
                                               Integer rank) {

    @Builder
    @QueryProjection
    public RankProductPriceByCtgSeqQueryDto {

    }

    public HighestPriceAndLowestPriceByCategoryNmItemDto toHighestPriceAndLowestPriceByCategoryNmItemDto() {
        return HighestPriceAndLowestPriceByCategoryNmItemDto.builder()
                .brandNm(brandNm)
                .productPrice(String.format("%,d", productPrice))
                .build();
    }
}
