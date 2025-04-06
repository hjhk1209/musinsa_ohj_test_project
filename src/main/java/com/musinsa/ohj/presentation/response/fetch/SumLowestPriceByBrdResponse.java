package com.musinsa.ohj.presentation.response.fetch;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.musinsa.ohj.domain.model.constants.ApiResponseCode;
import com.musinsa.ohj.presentation.response.ApiResponse;
import com.musinsa.ohj.presentation.response.CommonApiResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;

import java.util.List;

@ToString(callSuper = true)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SumLowestPriceByBrdResponse<SumLowestPriceByBrdDto> extends CommonApiResponse
        implements ApiResponse<List<SumLowestPriceByBrdDto>> {
    ApiResponseCode apiResponseCode;
    List<SumLowestPriceByBrdDto> data;

    @Builder
    private SumLowestPriceByBrdResponse(ApiResponseCode apiResponseCode,
                                       String code,
                                       String message,
                                       List<SumLowestPriceByBrdDto> data) {
        super(apiResponseCode, code, message);
        this.apiResponseCode = apiResponseCode;
        this.data = data;
    }

    public static SumLowestPriceByBrdResponseBuilder builder(ApiResponseCode apiResponseCode) {
        return new SumLowestPriceByBrdResponseBuilder().apiResponseCode(apiResponseCode);
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Override
    public List<SumLowestPriceByBrdDto> getData() {
        return data;
    }

    @Override
    public ResponseEntity<ApiResponse> toResponse() {
        return apiResponseCode.getResponseEntity(this);
    }
}
