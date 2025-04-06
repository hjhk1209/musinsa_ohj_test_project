package com.musinsa.ohj.documentation.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.musinsa.ohj.domain.model.constants.ApiResponseCode;
import com.musinsa.ohj.presentation.response.ApiResponse;
import com.musinsa.ohj.presentation.response.CommonApiResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CommonDocumentApiResponse extends CommonApiResponse implements ApiResponse<CommonDocumentDataDto> {

    ApiResponseCode apiResponseCode;
    CommonDocumentDataDto data;

    @Builder
    public CommonDocumentApiResponse(@JsonProperty("code") String code,
                                     @JsonProperty("message") String message,
                                     @JsonProperty("data") CommonDocumentDataDto data) {
        super(ApiResponseCode.SUCCESS, code, message);
        this.apiResponseCode = ApiResponseCode.SUCCESS;
        this.data = data;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Override
    public CommonDocumentDataDto getData() {
        return data;
    }

    @Override
    public ResponseEntity<ApiResponse> toResponse() {
        return apiResponseCode.getResponseEntity(this);
    }
}
