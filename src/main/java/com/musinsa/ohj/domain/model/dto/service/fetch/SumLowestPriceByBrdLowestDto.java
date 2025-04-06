package com.musinsa.ohj.domain.model.dto.service.fetch;

import lombok.Builder;

import java.util.List;

public record SumLowestPriceByBrdLowestDto(String brandNm,
                                           List<SumLowestPriceByBrdLowestCtgDto> categoryList,
                                           String totalProductPrice) {
    @Builder
    public SumLowestPriceByBrdLowestDto {
    }
}
