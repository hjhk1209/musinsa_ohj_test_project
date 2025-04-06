package com.musinsa.ohj.presentation.response.brand;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.musinsa.ohj.domain.model.constants.ApiResponseCode;
import com.musinsa.ohj.presentation.response.ApiResponse;
import com.musinsa.ohj.presentation.response.CommonApiResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@ToString(callSuper = true)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UpdateBrandResponse extends CommonApiResponse implements ApiResponse<Optional> {

    ApiResponseCode apiResponseCode;

    @Builder
    private UpdateBrandResponse(ApiResponseCode apiResponseCode,
                                String code,
                                String message) {
        super(apiResponseCode, code, message);
        this.apiResponseCode = apiResponseCode;
    }

    public static UpdateBrandResponseBuilder builder(ApiResponseCode apiResponseCode) {
        return new UpdateBrandResponseBuilder().apiResponseCode(apiResponseCode);
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Override
    public Optional getData() {
        return Optional.empty();
    }

    @Override
    public ResponseEntity<ApiResponse> toResponse() {
        return apiResponseCode.getResponseEntity(this);
    }
}
