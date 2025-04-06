package com.musinsa.ohj.domain.model.dto.service.fetch;

import lombok.Builder;

import java.util.List;

public record SummaryLowestPriceByCtgAndBrdDto(List<SummaryLowestPriceByCtgAndBrdItemDto> list,
                                               String totalProductPrice) {
    @Builder
    public SummaryLowestPriceByCtgAndBrdDto {
    }
}
