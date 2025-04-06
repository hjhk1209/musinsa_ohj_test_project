package com.musinsa.ohj.domain.model.dto.service.fetch;

import lombok.Builder;

public record SumLowestPriceByBrdDto(SumLowestPriceByBrdLowestDto lowest) {
    @Builder
    public SumLowestPriceByBrdDto {
    }
}
