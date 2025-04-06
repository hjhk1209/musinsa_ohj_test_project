package com.musinsa.ohj.infrastructure.database.repository;

import com.musinsa.ohj.domain.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryNm(String categoryNm);
}
