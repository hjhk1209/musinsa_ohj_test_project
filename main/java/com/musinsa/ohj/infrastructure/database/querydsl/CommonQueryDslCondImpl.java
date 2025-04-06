package com.musinsa.ohj.infrastructure.database.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.musinsa.ohj.domain.model.entity.QBrand.brand;

public class CommonQueryDslCondImpl {
    public BooleanExpression inBrandSeq(List<Long> brandSeqs) {
        if (CollectionUtils.isEmpty(brandSeqs)) {
            return null;
        }
        return brand.brandSeq.in(brandSeqs);
    }
}
