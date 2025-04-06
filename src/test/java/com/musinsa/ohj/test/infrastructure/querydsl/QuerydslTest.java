package com.musinsa.ohj.test.infrastructure.querydsl;

import com.musinsa.ohj.infrastructure.database.querydsl.ProductFetchQueryDslImpl;
import com.musinsa.ohj.infrastructure.database.repository.ProductJpaRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(QueryDslImpTestConfig.class)
@DataJpaTest
public class QuerydslTest {
    @Autowired
    protected ProductFetchQueryDslImpl productFetchQueryDslImpl;
    @Autowired
    protected ProductJpaRepositoryImpl productJpaRepositoryImpl;
}
