package com.musinsa.ohj.presentation.request.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record CreateProductRequest(@NotBlank(message = "값을 확인해주세요") String productNm,
                                   @NotNull @Min(value = 1, message = "올바른 값을 입력해주세요") Long productPrice) {
    @Builder
    public CreateProductRequest {
    }
}
