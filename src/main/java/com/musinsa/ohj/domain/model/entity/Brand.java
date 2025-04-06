package com.musinsa.ohj.domain.model.entity;

import com.musinsa.ohj.domain.model.constants.ConstantValues;
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
@Table(name = "BRAND")
@SequenceGenerator(
        name = "brand_seq_generator",
        sequenceName = "brand_seq",
        allocationSize = 1
)
@Getter
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brand_seq_generator")
    Long brandSeq;
    String brandNm;
    @CreatedDate
    LocalDateTime createDt;
    String createId;
    @LastModifiedDate
    LocalDateTime updateDt;
    String updateId;

    @OneToMany(mappedBy = "brand")
    List<Product> productList = new ArrayList<>();

    @Builder
    private Brand(Long brandSeq,
                  String brandNm,
                  String createId,
                  String updateId) {
        this.brandSeq = brandSeq;
        this.brandNm = brandNm;
        this.createId = createId;
        this.updateId = updateId;
    }

    public void updateBrand(String brandNm) {
        this.brandNm = brandNm;
        this.updateId = ConstantValues.UPDATER_ID;
    }
}
