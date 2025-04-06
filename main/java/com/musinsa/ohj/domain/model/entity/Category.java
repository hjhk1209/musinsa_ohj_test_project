package com.musinsa.ohj.domain.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CATEGORY")
@SequenceGenerator(
        name = "category_seq_generator",
        sequenceName = "category_seq",
        allocationSize = 1
)
@Getter
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq_generator")
    Long categorySeq;
    String categoryNm;
    @CreatedDate
    LocalDateTime createDt;
    String createId;
    @LastModifiedDate
    LocalDateTime updateDt;
    String updateId;

    @OneToMany(mappedBy = "category")
    List<Product> productList = new ArrayList<>();

    @Builder
    private Category(Long categorySeq,
                     String categoryNm,
                     String createId,
                     String updateId) {
        this.categorySeq = categorySeq;
        this.categoryNm = categoryNm;
        this.createId = createId;
        this.updateId = updateId;
    }
}
