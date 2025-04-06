package com.musinsa.ohj.domain.model.dto.service.fetch;

import lombok.Builder;

import java.util.Set;

public record SummaryLowestPriceByCtgAndBrdItemDto(String categoryNm,
                                                   Set<String> brandNmList,
                                                   String productPrice) {
    @Builder
    public SummaryLowestPriceByCtgAndBrdItemDto {
    }
}
