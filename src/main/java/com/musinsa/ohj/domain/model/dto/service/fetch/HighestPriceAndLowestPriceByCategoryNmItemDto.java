package com.musinsa.ohj.domain.model.dto.service.fetch;

import lombok.Builder;

public record HighestPriceAndLowestPriceByCategoryNmItemDto(String brandNm,
                                                            String productPrice) {
    @Builder
    public HighestPriceAndLowestPriceByCategoryNmItemDto {
    }
}
