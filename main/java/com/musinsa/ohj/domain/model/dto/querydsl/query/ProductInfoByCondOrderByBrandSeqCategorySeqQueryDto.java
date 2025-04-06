package com.musinsa.ohj.domain.model.dto.querydsl.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

public record ProductInfoByCondOrderByBrandSeqCategorySeqQueryDto(Long productSeq,
                                                                  Long brandSeq,
                                                                  Long categorySeq,
                                                                  String brandNm,
                                                                  String categoryNm,
                                                                  String productNm,
                                                                  Long productPrice) {

    @Builder
    @QueryProjection
    public ProductInfoByCondOrderByBrandSeqCategorySeqQueryDto {

    }
}
