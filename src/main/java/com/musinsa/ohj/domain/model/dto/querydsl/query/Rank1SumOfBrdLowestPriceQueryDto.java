package com.musinsa.ohj.domain.model.dto.querydsl.query;

import com.musinsa.ohj.domain.model.dto.service.fetch.SumLowestPriceByBrdDto;
import com.musinsa.ohj.domain.model.dto.service.fetch.SumLowestPriceByBrdLowestDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record Rank1SumOfBrdLowestPriceQueryDto(Long brandSeq,
                                               Long sumProductPrice,
                                               Integer rank) {
    @Builder
    @QueryProjection
    public Rank1SumOfBrdLowestPriceQueryDto {
    }

    public SumLowestPriceByBrdDto toSumLowestPriceByBrdDto(
            Map<Long, List<ProductInfoByCondOrderByBrdSeqCtgSeqQueryDto>> productInfoByCondMap) {
        List<ProductInfoByCondOrderByBrdSeqCtgSeqQueryDto> list = productInfoByCondMap.get(brandSeq);
        String brandNm = list.stream().findAny().get().brandNm();
        return SumLowestPriceByBrdDto.builder()
                .lowest(SumLowestPriceByBrdLowestDto.builder()
                        .brandNm(brandNm)
                        .categoryList(list.stream()
                                .map(ProductInfoByCondOrderByBrdSeqCtgSeqQueryDto::toSumLowestPriceByBrdLowestCtgDto)
                                .collect(Collectors.toList()))
                        .totalProductPrice(String.format("%,d", sumProductPrice))
                        .build())
                .build();
    }
}
