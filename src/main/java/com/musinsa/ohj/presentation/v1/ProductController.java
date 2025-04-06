package com.musinsa.ohj.presentation.v1;

import com.musinsa.ohj.application.ProductService;
import com.musinsa.ohj.presentation.request.product.CreateProductRequest;
import com.musinsa.ohj.presentation.request.product.UpdateProductRequest;
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
public class ProductController {

    ProductService productService;

    @PostMapping("/v1/brand/{brandSeq}/category/{categorySeq}/product")
    public ResponseEntity<ApiResponse> createProduct(@PathVariable Long brandSeq,
                                                     @PathVariable Long categorySeq,
                                                     @RequestBody @Validated CreateProductRequest createProductRequest) {
        return productService.createProduct(brandSeq, categorySeq, createProductRequest)
                .toResponse();
    }

    @PutMapping("/v1/brand/{brandSeq}/category/{categorySeq}/product/{productSeq}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long brandSeq,
                                                     @PathVariable Long categorySeq,
                                                     @PathVariable Long productSeq,
                                                     @RequestBody @Validated UpdateProductRequest updateProductRequest) {
        return productService.updateProduct(brandSeq, categorySeq, productSeq, updateProductRequest)
                .toResponse();
    }

    @DeleteMapping("/v1/brand/{brandSeq}/category/{categorySeq}/product/{productSeq}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long brandSeq,
                                                     @PathVariable Long categorySeq,
                                                     @PathVariable Long productSeq) {
        return productService.deleteProduct(brandSeq, categorySeq, productSeq)
                .toResponse();
    }

}
