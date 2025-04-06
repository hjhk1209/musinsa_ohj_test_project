package com.musinsa.ohj.application;

import com.musinsa.ohj.domain.model.constants.ApiResponseCode;
import com.musinsa.ohj.domain.model.constants.ConstantValues;
import com.musinsa.ohj.domain.model.entity.Brand;
import com.musinsa.ohj.infrastructure.database.repository.BrandJpaRepository;
import com.musinsa.ohj.infrastructure.database.repository.ProductJpaRepository;
import com.musinsa.ohj.presentation.request.brand.CreateBrandRequest;
import com.musinsa.ohj.presentation.request.brand.UpdateBrandRequest;
import com.musinsa.ohj.presentation.request.brand.DeleteBrandRequest;
import com.musinsa.ohj.presentation.response.brand.CreateBrandResponse;
import com.musinsa.ohj.presentation.response.brand.DeleteBrandResponse;
import com.musinsa.ohj.presentation.response.brand.UpdateBrandResponse;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BrandService {

    BrandJpaRepository brandJpaRepository;

    ProductJpaRepository productJpaRepository;

    @Transactional
    public CreateBrandResponse createBrand(CreateBrandRequest createBrandRequest) {
        if (brandJpaRepository.findByBrandNm(createBrandRequest.brandNm()).isPresent()) {
            return CreateBrandResponse.builder(ApiResponseCode.ALREADY_EXISTS_DATA)
                    .message("이미 해당 브랜드명이 존재합니다")
                    .build();
        }
        brandJpaRepository.save(Brand.builder()
                .brandNm(createBrandRequest.brandNm())
                .createId(ConstantValues.UPDATER_ID)
                .build());
        return CreateBrandResponse.builder(ApiResponseCode.SUCCESS)
                .build();
    }

    @Transactional
    public UpdateBrandResponse updateBrand(Long brandSeq,
                                           UpdateBrandRequest updateBrandRequest) {
        Optional<Brand> brandOptional = brandJpaRepository.findById(brandSeq);
        if (!brandOptional.isPresent()) {
            return UpdateBrandResponse.builder(ApiResponseCode.NOT_FOUND_DATA)
                    .build();
        }
        if (brandJpaRepository.findByBrandNm(updateBrandRequest.brandNm()).isPresent()) {
            return UpdateBrandResponse.builder(ApiResponseCode.ALREADY_EXISTS_DATA)
                    .message("이미 해당 브랜드명이 존재합니다")
                    .build();
        }
        Brand brand = brandOptional.get();
        brand.updateBrand(updateBrandRequest.brandNm());
        return UpdateBrandResponse.builder(ApiResponseCode.SUCCESS)
                .build();
    }

    @Transactional
    public DeleteBrandResponse deleteBrand(Long brandSeq,
                                           DeleteBrandRequest deleteBrandRequest) {
        Optional<Brand> brandOptional = brandJpaRepository.findById(brandSeq);
        if (!brandOptional.isPresent()) {
            return DeleteBrandResponse.builder(ApiResponseCode.NOT_FOUND_DATA)
                    .build();
        }
        if (deleteBrandRequest.isProductBulkDelete()) {
            // 상품 삭제
            productJpaRepository.deleteProductByBrand(brandSeq);
        } else {
            if (!productJpaRepository.notExistProductByBrand(brandSeq)) {
                return DeleteBrandResponse.builder(ApiResponseCode.ALREADY_EXISTS_DATA)
                        .message("해당 브랜드에 상품이 존재합니다.")
                        .build();
            }
        }
        brandJpaRepository.delete(brandOptional.get());
        return DeleteBrandResponse.builder(ApiResponseCode.SUCCESS)
                .build();
    }
}
