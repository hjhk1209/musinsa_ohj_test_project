package com.musinsa.ohj.presentation.response;

import com.musinsa.ohj.common.utils.DateUtils;
import com.musinsa.ohj.common.utils.StrUtils;
import com.musinsa.ohj.domain.model.constants.ApiResponseCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.springframework.util.StringUtils;

@ToString
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommonApiResponse {
    String code;
    String message;
    String timestamp;
    String traceId;

    public CommonApiResponse(ApiResponseCode apiResponseCode,
                             String code,
                             String message) {
        this.code = StringUtils.hasText(code) ? code : apiResponseCode.getCode();
        this.message = StringUtils.hasText(message) ? message : apiResponseCode.getMessage();
        this.timestamp = DateUtils.getNowIso8601Format();
        this.traceId = StrUtils.getRandomAlphanumeric(20);
    }


}
