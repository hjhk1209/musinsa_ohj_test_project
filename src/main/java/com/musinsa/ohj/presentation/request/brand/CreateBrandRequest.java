package com.musinsa.ohj.presentation.request.brand;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

public record CreateBrandRequest(@NotBlank(message = "값을 확인해주세요") String brandNm) {
    @Builder
    public CreateBrandRequest {}
}
