package com.musinsa.ohj.presentation.v1;

import com.musinsa.ohj.application.BrandService;
import com.musinsa.ohj.presentation.request.brand.CreateBrandRequest;
import com.musinsa.ohj.presentation.request.brand.UpdateBrandRequest;
import com.musinsa.ohj.presentation.request.brand.DeleteBrandRequest;
import com.musinsa.ohj.presentation.response.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BrandController {

    BrandService brandService;

    @PostMapping("/v1/brand")
    public ResponseEntity<ApiResponse> createBrand(@RequestBody @Validated CreateBrandRequest createBrandRequest) {
        return brandService.createBrand(createBrandRequest)
                .toResponse();
    }

    @PutMapping("/v1/brand/{brandSeq}")
    public ResponseEntity<ApiResponse> updateBrand(@PathVariable Long brandSeq,
                                                   @RequestBody @Validated UpdateBrandRequest updateBrandRequest) {
        return brandService.updateBrand(brandSeq, updateBrandRequest)
                .toResponse();
    }

    @DeleteMapping("/v1/brand/{brandSeq}")
    public ResponseEntity<ApiResponse> deleteBrand(@PathVariable Long brandSeq,
                                                   @RequestBody @Validated DeleteBrandRequest deleteBrandRequest) {
        return brandService.deleteBrand(brandSeq, deleteBrandRequest)
                .toResponse();
    }
}
