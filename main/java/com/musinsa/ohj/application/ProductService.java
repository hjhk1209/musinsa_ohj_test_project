package com.musinsa.ohj.application;

import com.musinsa.ohj.domain.model.constants.ApiResponseCode;
import com.musinsa.ohj.domain.model.constants.ConstantValues;
import com.musinsa.ohj.domain.model.entity.Brand;
import com.musinsa.ohj.domain.model.entity.Category;
import com.musinsa.ohj.domain.model.entity.Product;
import com.musinsa.ohj.infrastructure.database.repository.BrandJpaRepository;
import com.musinsa.ohj.infrastructure.database.repository.CategoryJpaRepository;
import com.musinsa.ohj.infrastructure.database.repository.ProductJpaRepository;
import com.musinsa.ohj.presentation.request.product.CreateProductRequest;
import com.musinsa.ohj.presentation.request.product.UpdateProductRequest;
import com.musinsa.ohj.presentation.response.product.CreateProductResponse;
import com.musinsa.ohj.presentation.response.product.DeleteProductResponse;
import com.musinsa.ohj.presentation.response.product.UpdateProductResponse;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProductService {

    BrandJpaRepository brandJpaRepository;

    CategoryJpaRepository categoryJpaRepository;

    ProductJpaRepository productJpaRepository;

    @Transactional
    public CreateProductResponse createProduct(Long brandSeq,
                                               Long categorySeq,
                                               CreateProductRequest createProductRequest) {
        Optional<Brand> brandOptional = brandJpaRepository.findById(brandSeq);
        if (!brandOptional.isPresent()) {
            return CreateProductResponse.builder(ApiResponseCode.NOT_FOUND_DATA)
                    .message("브랜드 정보가 없습니다")
                    .build();
        }
        Optional<Category> categoryOptional = categoryJpaRepository.findById(categorySeq);
        if (!categoryOptional.isPresent()) {
            return CreateProductResponse.builder(ApiResponseCode.NOT_FOUND_DATA)
                    .message("카테고리 정보가 없습니다")
                    .build();
        }
        productJpaRepository.save(Product.builder()
                .brand(brandOptional.get())
                .category(categoryOptional.get())
                .productNm(createProductRequest.productNm())
                .productPrice(createProductRequest.productPrice())
                .updateId(ConstantValues.UPDATER_ID)
                .build());
        return CreateProductResponse.builder(ApiResponseCode.SUCCESS)
                .build();
    }

    @Transactional
    public UpdateProductResponse updateProduct(Long brandSeq,
                                               Long categorySeq,
                                               Long productSeq,
                                               UpdateProductRequest updateProductRequest) {
        Optional<Product> productOptional =
                productJpaRepository.findByProductSeqAndBrandSeqAndCategorySeq(productSeq, brandSeq, categorySeq);
        if (!productOptional.isPresent()) {
            return UpdateProductResponse.builder(ApiResponseCode.NOT_FOUND_DATA)
                    .build();
        }
        Optional<Brand> brandOptional = brandJpaRepository.findById(updateProductRequest.brandSeq());
        if (!brandOptional.isPresent()) {
            return UpdateProductResponse.builder(ApiResponseCode.NOT_FOUND_DATA)
                    .message("브랜드 정보가 없습니다")
                    .build();
        }
        Optional<Category> categoryOptional = categoryJpaRepository.findById(updateProductRequest.categorySeq());
        if (!categoryOptional.isPresent()) {
            return UpdateProductResponse.builder(ApiResponseCode.NOT_FOUND_DATA)
                    .message("카테고리 정보가 없습니다")
                    .build();
        }
        Product product = productOptional.get();
        product.updateProduct(updateProductRequest.productNm(),
                updateProductRequest.productPrice(),
                brandOptional.get(),
                categoryOptional.get());
        return UpdateProductResponse.builder(ApiResponseCode.SUCCESS)
                .build();
    }


    @Transactional
    public DeleteProductResponse deleteProduct(Long brandSeq,
                                               Long categorySeq,
                                               Long productSeq) {
        Optional<Product> productOptional =
                productJpaRepository.findByProductSeqAndBrandSeqAndCategorySeq(productSeq, brandSeq, categorySeq);
        if (!productOptional.isPresent()) {
            return DeleteProductResponse.builder(ApiResponseCode.NOT_FOUND_DATA)
                    .build();
        }
        productJpaRepository.delete(productOptional.get());
        return DeleteProductResponse.builder(ApiResponseCode.SUCCESS)
                .build();
    }
}
