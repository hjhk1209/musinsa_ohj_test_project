package com.musinsa.ohj.infrastructure.database.repository;

public interface ProductJpaRepositoryCustom {
    boolean notExistProductByBrand(Long brandSeq);
    Long deleteProductByBrand(Long brandSeq);
}
