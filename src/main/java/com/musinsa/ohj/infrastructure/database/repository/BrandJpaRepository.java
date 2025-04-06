package com.musinsa.ohj.infrastructure.database.repository;

import com.musinsa.ohj.domain.model.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandJpaRepository extends JpaRepository<Brand, Long> {
    Optional<Brand> findByBrandNm(String brandNm);
}
