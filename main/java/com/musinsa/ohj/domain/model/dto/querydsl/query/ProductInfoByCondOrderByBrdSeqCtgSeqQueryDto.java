package com.musinsa.ohj.domain.model.dto.querydsl.query;

import com.musinsa.ohj.domain.model.dto.service.fetch.SumLowestPriceByBrdLowestCtgDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

public record ProductInfoByCondOrderByBrdSeqCtgSeqQueryDto(Long productSeq,
                                                           Long brandSeq,
                                                           Long categorySeq,
                                                           String brandNm,
                                                           String categoryNm,
                                                           String productNm,
                                                           Long productPrice) {

    @Builder
    @QueryProjection
    public ProductInfoByCondOrderByBrdSeqCtgSeqQueryDto {

    }

    public SumLowestPriceByBrdLowestCtgDto toSumLowestPriceByBrdLowestCtgDto() {
        return SumLowestPriceByBrdLowestCtgDto.builder()
                .categoryNm(categoryNm)
                .productPrice(String.format("%,d", productPrice))
                .build();
    }
}
