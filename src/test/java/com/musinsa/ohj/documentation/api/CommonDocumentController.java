package com.musinsa.ohj.documentation.api;

import com.musinsa.ohj.documentation.api.response.CommonDocumentApiResponse;
import com.musinsa.ohj.documentation.api.response.CommonDocumentDataDto;
import com.musinsa.ohj.domain.model.constants.ApiResponseCode;
import com.musinsa.ohj.domain.model.constants.EnumCodeConverter;
import com.musinsa.ohj.presentation.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/common")
public class CommonDocumentController {

    @GetMapping("/")
    public ApiResponse getCommon() {
        return CommonDocumentApiResponse.builder()
                .data(CommonDocumentDataDto.builder()
                        .responseCodeMap(EnumCodeConverter.convertEnumToMap(ApiResponseCode.values()))
                        .build())
                .build();

    }
}
