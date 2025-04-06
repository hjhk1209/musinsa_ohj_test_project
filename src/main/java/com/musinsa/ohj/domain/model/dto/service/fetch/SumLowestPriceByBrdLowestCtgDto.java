package com.musinsa.ohj.domain.model.dto.service.fetch;

import lombok.Builder;

public record SumLowestPriceByBrdLowestCtgDto(String categoryNm,
                                              String productPrice) {
    @Builder
    public SumLowestPriceByBrdLowestCtgDto {
    }
}
