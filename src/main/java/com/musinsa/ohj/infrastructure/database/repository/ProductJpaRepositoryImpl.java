package com.musinsa.ohj.infrastructure.database.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static com.musinsa.ohj.domain.model.entity.QBrand.brand;
import static com.musinsa.ohj.domain.model.entity.QProduct.product;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProductJpaRepositoryImpl implements ProductJpaRepositoryCustom {
    JPAQueryFactory jpaQueryFactory;

    @Override
    public boolean notExistProductByBrand(Long brandSeq) {
        Integer fetchOne = jpaQueryFactory
                .selectOne()
                .from(product)
                .innerJoin(product.brand, brand)
                .where(brand.brandSeq.eq(brandSeq))
                .fetchFirst();
        return fetchOne == null;
    }

    @Override
    public Long deleteProductByBrand(Long brandSeq) {
        return jpaQueryFactory.delete(product)
                .where(product.brand.brandSeq.eq(brandSeq))
                .execute();
    }
}
