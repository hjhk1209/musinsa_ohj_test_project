package com.musinsa.ohj.test.infrastructure.repository;

import com.musinsa.ohj.test.infrastructure.querydsl.QuerydslTest;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductJpaRepositoryImplTest extends QuerydslTest {

    @Test
    @DisplayName("특정 브랜드 상품이 있는지 체크 쿼리 테스트")
    void notExistProductByBrandTest() {
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(productJpaRepositoryImpl.notExistProductByBrand(10l))
                    .isTrue();
            softAssertions.assertThat(productJpaRepositoryImpl.notExistProductByBrand(1l))
                    .isFalse();
        });
    }

    @Test
    @DisplayName("특정 브랜드 상품이 삭제되는지 확인")
    void deleteProductByBrandTest() {
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(productJpaRepositoryImpl.deleteProductByBrand(10l))
                    .isEqualTo(0);
            softAssertions.assertThat(productJpaRepositoryImpl.deleteProductByBrand(1l))
                    .isEqualTo(8);
        });
    }
}
