package com.musinsa.ohj.domain.model.dto.service.fetch;

import lombok.Builder;

import java.util.List;

public record HighestPriceAndLowestPriceByCategoryNmDto(String categoryNm,
                                                        List<HighestPriceAndLowestPriceByCategoryNmItemDto> lowestList,
                                                        List<HighestPriceAndLowestPriceByCategoryNmItemDto> highestList) {
    @Builder
    public HighestPriceAndLowestPriceByCategoryNmDto {
    }
}
