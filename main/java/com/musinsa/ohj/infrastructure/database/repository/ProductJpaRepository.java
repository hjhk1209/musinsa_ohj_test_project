package com.musinsa.ohj.infrastructure.database.repository;

import com.musinsa.ohj.domain.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductJpaRepository extends JpaRepository<Product, Long>, ProductJpaRepositoryCustom {

    @Query("select p from Product p join fetch p.brand join fetch p.category " +
            "where p.productSeq = :productSeq and p.brand.brandSeq = :brandSeq and p.category.categorySeq = :categorySeq")
    Optional<Product> findByProductSeqAndBrandSeqAndCategorySeq(Long productSeq,
                                                                Long brandSeq,
                                                                Long categorySeq);
}
