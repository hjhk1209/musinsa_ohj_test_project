package com.musinsa.ohj.test.infrastructure.querydsl;

import com.musinsa.ohj.infrastructure.database.querydsl.ProductFetchQueryDslImpl;
import com.musinsa.ohj.infrastructure.database.repository.ProductJpaRepositoryImpl;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class QueryDslImpTestConfig {
    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public ProductFetchQueryDslImpl productFetchQueryDslImpl() {
        return new ProductFetchQueryDslImpl(jpaQueryFactory());
    }

    @Bean
    public ProductJpaRepositoryImpl productJpaRepositoryImpl() {
        return new ProductJpaRepositoryImpl(jpaQueryFactory());
    }
}
