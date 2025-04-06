package com.musinsa.ohj.presentation.response.fetch;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.musinsa.ohj.domain.model.constants.ApiResponseCode;
import com.musinsa.ohj.domain.model.dto.service.fetch.SummaryLowestPriceByCtgAndBrdDto;
import com.musinsa.ohj.presentation.response.ApiResponse;
import com.musinsa.ohj.presentation.response.CommonApiResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;

@ToString(callSuper = true)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SummaryLowestPriceByCtgAndBrdResponse extends CommonApiResponse
        implements ApiResponse<SummaryLowestPriceByCtgAndBrdDto> {
    ApiResponseCode apiResponseCode;
    SummaryLowestPriceByCtgAndBrdDto data;

    @Builder
    private SummaryLowestPriceByCtgAndBrdResponse(ApiResponseCode apiResponseCode,
                                                 String code,
                                                 String message,
                                                 SummaryLowestPriceByCtgAndBrdDto data) {
        super(apiResponseCode, code, message);
        this.apiResponseCode = apiResponseCode;
        this.data = data;
    }

    public static SummaryLowestPriceByCtgAndBrdResponseBuilder builder(ApiResponseCode apiResponseCode) {
        return new SummaryLowestPriceByCtgAndBrdResponseBuilder().apiResponseCode(apiResponseCode);
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Override
    public SummaryLowestPriceByCtgAndBrdDto getData() {
        return data;
    }

    @Override
    public ResponseEntity<ApiResponse> toResponse() {
        return apiResponseCode.getResponseEntity(this);
    }
}
