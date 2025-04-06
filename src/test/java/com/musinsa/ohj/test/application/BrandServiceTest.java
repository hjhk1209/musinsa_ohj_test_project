package com.musinsa.ohj.test.application;

import com.musinsa.ohj.application.BrandService;
import com.musinsa.ohj.domain.model.constants.ApiResponseCode;
import com.musinsa.ohj.domain.model.entity.Brand;
import com.musinsa.ohj.infrastructure.database.repository.BrandJpaRepository;
import com.musinsa.ohj.infrastructure.database.repository.ProductJpaRepository;
import com.musinsa.ohj.presentation.request.brand.CreateBrandRequest;
import com.musinsa.ohj.presentation.request.brand.DeleteBrandRequest;
import com.musinsa.ohj.presentation.request.brand.UpdateBrandRequest;
import com.musinsa.ohj.presentation.response.brand.CreateBrandResponse;
import com.musinsa.ohj.presentation.response.brand.DeleteBrandResponse;
import com.musinsa.ohj.presentation.response.brand.UpdateBrandResponse;
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
public class BrandServiceTest {
    @Mock
    BrandJpaRepository brandJpaRepository;
    @Mock
    ProductJpaRepository productJpaRepository;
    @InjectMocks
    BrandService brandService;

    @Test
    @DisplayName("브랜드 생성 성공 테스트")
    void 브랜드생성_성공() throws Exception {
        given(brandJpaRepository.findByBrandNm("new브랜드"))
                .willReturn(Optional.empty());
        CreateBrandResponse createBrandResponse = brandService.createBrand(CreateBrandRequest.builder()
                .brandNm("new브랜드")
                .build());
        then(brandJpaRepository).should(times(1)).save(any());
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(createBrandResponse.getCode())
                    .isEqualTo(ApiResponseCode.SUCCESS.getCode());
        });
    }

    @Test
    @DisplayName("브랜드 생성 실패 (중복브랜드명존재) 테스트")
    void 브랜드생성_실패_중복브랜드명존재() throws Exception {
        given(brandJpaRepository.findByBrandNm("new브랜드"))
                .willReturn(Optional.ofNullable(Brand.builder().build()));
        CreateBrandResponse createBrandResponse = brandService.createBrand(CreateBrandRequest.builder()
                .brandNm("new브랜드")
                .build());
        then(brandJpaRepository).should(never()).save(any());
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(createBrandResponse.getCode())
                    .isEqualTo(ApiResponseCode.ALREADY_EXISTS_DATA.getCode());
        });
    }

    @Test
    @DisplayName("브랜드 변경 성공 테스트")
    void 브랜드변경_성공() throws Exception {
        given(brandJpaRepository.findById(any(Long.class)))
                .willReturn(Optional.ofNullable(Brand.builder()
                        .brandSeq(1l)
                        .brandNm("old브랜드")
                        .build()));
        given(brandJpaRepository.findByBrandNm("new브랜드"))
                .willReturn(Optional.empty());

        UpdateBrandResponse updateBrandResponse = brandService.updateBrand(1l, UpdateBrandRequest.builder()
                .brandNm("new브랜드")
                .build());
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(updateBrandResponse.getCode())
                    .isEqualTo(ApiResponseCode.SUCCESS.getCode());
        });
    }

    @Test
    @DisplayName("브랜드 변경 실패 변경할브랜드없음 테스트")
    void 브랜드변경_실패_변경할브랜드없음() throws Exception {
        given(brandJpaRepository.findById(any(Long.class)))
                .willReturn(Optional.empty());
        UpdateBrandResponse updateBrandResponse = brandService.updateBrand(1l, UpdateBrandRequest.builder()
                .brandNm("new브랜드")
                .build());
        then(brandJpaRepository).should(never()).findByBrandNm(any());
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(updateBrandResponse.getCode())
                    .isEqualTo(ApiResponseCode.NOT_FOUND_DATA.getCode());
        });
    }

    @Test
    @DisplayName("브랜드 변경 실패 중복브랜드명존재 테스트")
    void 브랜드변경_실패_중복브랜드명존재() throws Exception {
        given(brandJpaRepository.findById(any(Long.class)))
                .willReturn(Optional.ofNullable(Brand.builder()
                        .brandSeq(1l)
                        .brandNm("old브랜드")
                        .build()));
        given(brandJpaRepository.findByBrandNm("new브랜드"))
                .willReturn(Optional.ofNullable(Brand.builder().build()));

        UpdateBrandResponse updateBrandResponse = brandService.updateBrand(1l, UpdateBrandRequest.builder()
                .brandNm("new브랜드")
                .build());
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(updateBrandResponse.getCode())
                    .isEqualTo(ApiResponseCode.ALREADY_EXISTS_DATA.getCode());
        });
    }

    @Test
    @DisplayName("브랜드 삭제 성공 브랜드만 삭제 테스트")
    void 브랜드삭제_성공_브랜드만삭제() {
        given(brandJpaRepository.findById(any(Long.class)))
                .willReturn(Optional.ofNullable(Brand.builder().brandSeq(1l).build()));
        given(productJpaRepository.notExistProductByBrand(any(Long.class)))
                .willReturn(true);

        DeleteBrandResponse deleteBrandResponse = brandService.deleteBrand(1l, DeleteBrandRequest.builder()
                .isProductBulkDelete(false)
                .build());
        then(brandJpaRepository).should(times(1)).delete(any());
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(deleteBrandResponse.getCode())
                    .isEqualTo(ApiResponseCode.SUCCESS.getCode());
        });
    }

    @Test
    @DisplayName("브랜드 삭제 성공 상품까지 삭제 테스트")
    void 브랜드삭제_성공_상품까지삭제() {
        given(brandJpaRepository.findById(any(Long.class)))
                .willReturn(Optional.ofNullable(Brand.builder().brandSeq(1l).build()));

        DeleteBrandResponse deleteBrandResponse = brandService.deleteBrand(1l, DeleteBrandRequest.builder()
                .isProductBulkDelete(true)
                .build());
        then(productJpaRepository).should(times(1)).deleteProductByBrand(any());
        then(brandJpaRepository).should(times(1)).delete(any());
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(deleteBrandResponse.getCode())
                    .isEqualTo(ApiResponseCode.SUCCESS.getCode());
        });
    }

    @Test
    @DisplayName("브랜드 삭제 실패 삭제할브랜드없음 테스트")
    void 브랜드삭제_실패_삭제할브랜드없음() {
        given(brandJpaRepository.findById(any(Long.class)))
                .willReturn(Optional.empty());

        DeleteBrandResponse deleteBrandResponse = brandService.deleteBrand(1l, DeleteBrandRequest.builder()
                .isProductBulkDelete(false)
                .build());
        then(brandJpaRepository).should(never()).delete(any());
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(deleteBrandResponse.getCode())
                    .isEqualTo(ApiResponseCode.NOT_FOUND_DATA.getCode());
        });
    }

    @Test
    @DisplayName("브랜드 삭제 실패 해당브랜드상품존재 테스트")
    void 브랜드삭제_실패_해당브랜드상품존재() {
        given(brandJpaRepository.findById(any(Long.class)))
                .willReturn(Optional.ofNullable(Brand.builder().brandSeq(1l).build()));
        given(productJpaRepository.notExistProductByBrand(any(Long.class)))
                .willReturn(false);

        DeleteBrandResponse deleteBrandResponse = brandService.deleteBrand(1l, DeleteBrandRequest.builder()
                .isProductBulkDelete(false)
                .build());
        then(brandJpaRepository).should(never()).delete(any());
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(deleteBrandResponse.getCode())
                    .isEqualTo(ApiResponseCode.ALREADY_EXISTS_DATA.getCode());
        });
    }
}
