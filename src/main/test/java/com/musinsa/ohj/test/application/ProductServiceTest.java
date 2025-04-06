package com.musinsa.ohj.test.application;

import com.musinsa.ohj.application.ProductService;
import com.musinsa.ohj.domain.model.constants.ApiResponseCode;
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
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    BrandJpaRepository brandJpaRepository;
    @Mock
    CategoryJpaRepository categoryJpaRepository;
    @Mock
    ProductJpaRepository productJpaRepository;
    @InjectMocks
    ProductService productService;

    @Test
    @DisplayName("상품등록 성공 테스트")
    void 상품등록_성공() throws Exception {
        given(brandJpaRepository.findById(any(Long.class)))
                .willReturn(Optional.ofNullable(Brand.builder()
                        .brandSeq(1l)
                        .build()));
        given(categoryJpaRepository.findById(any(Long.class)))
                .willReturn(Optional.ofNullable(Category.builder()
                        .categorySeq(1l)
                        .build()));

        CreateProductResponse createProductResponse = productService.createProduct(1l, 1l, CreateProductRequest.builder()
                .productNm("new 상품")
                .productPrice(1000l)
                .build());
        then(productJpaRepository).should(times(1)).save(any());
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(createProductResponse.getCode())
                    .isEqualTo(ApiResponseCode.SUCCESS.getCode());
        });
    }

    @Test
    @DisplayName("상품등록 실패 브랜드없음 테스트")
    void 상품등록_실패_브랜드없음() throws Exception {
        given(brandJpaRepository.findById(any(Long.class)))
                .willReturn(Optional.empty());

        CreateProductResponse createProductResponse = productService.createProduct(1l, 1l, CreateProductRequest.builder()
                .productNm("new 상품")
                .productPrice(1000l)
                .build());
        then(categoryJpaRepository).should(never()).findById(any());
        then(productJpaRepository).should(never()).save(any());
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(createProductResponse.getCode())
                    .isEqualTo(ApiResponseCode.NOT_FOUND_DATA.getCode());
        });
    }

    @Test
    @DisplayName("상품등록 실패 카테고리없음 테스트")
    void 상품등록_실패_카테고리없음() throws Exception {
        given(brandJpaRepository.findById(any(Long.class)))
                .willReturn(Optional.ofNullable(Brand.builder()
                        .brandSeq(1l)
                        .build()));
        given(categoryJpaRepository.findById(any(Long.class)))
                .willReturn(Optional.empty());

        CreateProductResponse createProductResponse = productService.createProduct(1l, 1l, CreateProductRequest.builder()
                .productNm("new 상품")
                .productPrice(1000l)
                .build());
        then(productJpaRepository).should(never()).save(any());
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(createProductResponse.getCode())
                    .isEqualTo(ApiResponseCode.NOT_FOUND_DATA.getCode());
        });
    }

    @Test
    @DisplayName("상품수정 성공 테스트")
    void 상품수정_성공() {
        given(productJpaRepository.findByProductSeqAndBrandSeqAndCategorySeq(any(Long.class), any(Long.class), any(Long.class)))
                .willReturn(Optional.ofNullable(Product.builder()
                        .productSeq(1l)
                        .brand(Brand.builder().brandSeq(1l).build())
                        .category(Category.builder().categorySeq(1l).build())
                        .productNm("old 상품")
                        .productPrice(5000l)
                        .build()));
        given(brandJpaRepository.findById(any(Long.class)))
                .willReturn(Optional.ofNullable(Brand.builder()
                        .brandSeq(1l)
                        .build()));
        given(categoryJpaRepository.findById(any(Long.class)))
                .willReturn(Optional.ofNullable(Category.builder()
                        .categorySeq(1l)
                        .build()));

        UpdateProductResponse updateProductResponse = productService.updateProduct(1l, 1l, 1l, UpdateProductRequest.builder()
                .categorySeq(1l)
                .brandSeq(1l)
                .productNm("상품 new 업데이트")
                .productPrice(2000l)
                .build());
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(updateProductResponse.getCode())
                    .isEqualTo(ApiResponseCode.SUCCESS.getCode());
        });
    }

    @Test
    @DisplayName("상품수정 실패 수정할상품없음 테스트")
    void 상품수정_실패_수정할상품없음() {
        given(productJpaRepository.findByProductSeqAndBrandSeqAndCategorySeq(any(Long.class), any(Long.class), any(Long.class)))
                .willReturn(Optional.empty());

        UpdateProductResponse updateProductResponse = productService.updateProduct(1l, 1l, 1l, UpdateProductRequest.builder()
                .categorySeq(1l)
                .brandSeq(1l)
                .productNm("상품 new 업데이트")
                .productPrice(2000l)
                .build());
        then(brandJpaRepository).should(never()).findById(any());
        then(categoryJpaRepository).should(never()).findById(any());
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(updateProductResponse.getCode())
                    .isEqualTo(ApiResponseCode.NOT_FOUND_DATA.getCode());
        });
    }

    @Test
    @DisplayName("상품수정 실패 수정할브랜드없음 테스트")
    void 상품수정_실패_수정할브랜드없음() {
        given(productJpaRepository.findByProductSeqAndBrandSeqAndCategorySeq(any(Long.class), any(Long.class), any(Long.class)))
                .willReturn(Optional.ofNullable(Product.builder()
                        .productSeq(1l)
                        .brand(Brand.builder().brandSeq(1l).build())
                        .category(Category.builder().categorySeq(1l).build())
                        .productNm("old 상품")
                        .productPrice(5000l)
                        .build()));
        given(brandJpaRepository.findById(any(Long.class)))
                .willReturn(Optional.empty());

        UpdateProductResponse updateProductResponse = productService.updateProduct(1l, 1l, 1l, UpdateProductRequest.builder()
                .categorySeq(1l)
                .brandSeq(1l)
                .productNm("상품 new 업데이트")
                .productPrice(2000l)
                .build());
        then(categoryJpaRepository).should(never()).findById(any());
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(updateProductResponse.getCode())
                    .isEqualTo(ApiResponseCode.NOT_FOUND_DATA.getCode());
        });
    }

    @Test
    @DisplayName("상품수정 실패 수정할카테고리없음 테스트")
    void 상품수정_실패_수정할카테고리없음() {
        given(productJpaRepository.findByProductSeqAndBrandSeqAndCategorySeq(any(Long.class), any(Long.class), any(Long.class)))
                .willReturn(Optional.ofNullable(Product.builder()
                        .productSeq(1l)
                        .brand(Brand.builder().brandSeq(1l).build())
                        .category(Category.builder().categorySeq(1l).build())
                        .productNm("old 상품")
                        .productPrice(5000l)
                        .build()));
        given(brandJpaRepository.findById(any(Long.class)))
                .willReturn(Optional.ofNullable(Brand.builder()
                        .brandSeq(1l)
                        .build()));
        given(categoryJpaRepository.findById(any(Long.class)))
                .willReturn(Optional.empty());

        UpdateProductResponse updateProductResponse = productService.updateProduct(1l, 1l, 1l, UpdateProductRequest.builder()
                .categorySeq(1l)
                .brandSeq(1l)
                .productNm("상품 new 업데이트")
                .productPrice(2000l)
                .build());
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(updateProductResponse.getCode())
                    .isEqualTo(ApiResponseCode.NOT_FOUND_DATA.getCode());
        });
    }

    @Test
    @DisplayName("상품삭제 성공")
    void 상품삭제_성공() {
        given(productJpaRepository.findByProductSeqAndBrandSeqAndCategorySeq(any(Long.class), any(Long.class), any(Long.class)))
                .willReturn(Optional.ofNullable(Product.builder()
                        .productSeq(1l)
                        .brand(Brand.builder().brandSeq(1l).build())
                        .category(Category.builder().categorySeq(1l).build())
                        .productNm("old 상품")
                        .productPrice(5000l)
                        .build()));

        DeleteProductResponse deleteProductResponse = productService.deleteProduct(1l, 1l, 1l);
        then(productJpaRepository).should(times(1)).delete(any());
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(deleteProductResponse.getCode())
                    .isEqualTo(ApiResponseCode.SUCCESS.getCode());
        });
    }

    @Test
    @DisplayName("상품삭제 실패 삭제할상품없음")
    void 상품삭제_실패_삭제할상품없음() {
        given(productJpaRepository.findByProductSeqAndBrandSeqAndCategorySeq(any(Long.class), any(Long.class), any(Long.class)))
                .willReturn(Optional.empty());

        DeleteProductResponse deleteProductResponse = productService.deleteProduct(1l, 1l, 1l);
        then(productJpaRepository).should(never()).delete(any());
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(deleteProductResponse.getCode())
                    .isEqualTo(ApiResponseCode.NOT_FOUND_DATA.getCode());
        });
    }
}
