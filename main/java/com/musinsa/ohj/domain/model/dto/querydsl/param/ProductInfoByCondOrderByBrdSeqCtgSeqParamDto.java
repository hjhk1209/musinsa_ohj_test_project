package com.musinsa.ohj.domain.model.dto.querydsl.param;

import lombok.Builder;

import java.util.List;

public record ProductInfoByCondOrderByBrdSeqCtgSeqParamDto(List<Long> brandSeqs) {
    @Builder
    public ProductInfoByCondOrderByBrdSeqCtgSeqParamDto {
    }
}
