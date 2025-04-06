package com.musinsa.ohj.domain.model.entity;

import com.musinsa.ohj.domain.model.constants.ConstantValues;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

@Entity
@Table(name = "PRODUCT")
@SequenceGenerator(
        name = "product_seq_generator",
        sequenceName = "product_seq",
        allocationSize = 1
)
@Getter
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq_generator")
    Long productSeq;
    @ManyToOne
    @JoinColumn(name = "brandSeq")
    Brand brand;
    @ManyToOne
    @JoinColumn(name = "categorySeq")
    Category category;
    String productNm;
    Long productPrice;
    @CreatedDate
    LocalDateTime createDt;
    String createId;
    @LastModifiedDate
    LocalDateTime updateDt;
    String updateId;

    @Builder
    private Product(Long productSeq,
                    Brand brand,
                    Category category,
                    String productNm,
                    Long productPrice,
                    String createId,
                    String updateId) {
        this.productSeq = productSeq;
        this.brand = brand;
        this.brand.getProductList().add(this);
        this.category = category;
        this.category.getProductList().add(this);
        this.productNm = productNm;
        this.productPrice = productPrice;
        this.createId = createId;
        this.updateId = updateId;
    }

    public void updateProduct(String productNm,
                              Long productPrice,
                              Brand brand,
                              Category category) {
        this.productNm = productNm;
        this.productPrice = productPrice;
        this.brand = brand;
        this.category = category;
        this.updateId = ConstantValues.UPDATER_ID;
    }
}
