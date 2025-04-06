package com.musinsa.ohj.domain.model.dto.querydsl.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

public record SummaryLowestPriceByCtgAndBrdQueryDto(Long categorySeq,
                                                    String categoryNm,
                                                    String brandNm,
                                                    Long productPrice) {
    @Builder
    @QueryProjection
    public SummaryLowestPriceByCtgAndBrdQueryDto {

    }
}
