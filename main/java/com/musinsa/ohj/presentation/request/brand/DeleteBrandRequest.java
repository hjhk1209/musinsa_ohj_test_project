package com.musinsa.ohj.presentation.request.brand;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record DeleteBrandRequest(@NotNull Boolean isProductBulkDelete) {
    @Builder
    public DeleteBrandRequest {}
}
