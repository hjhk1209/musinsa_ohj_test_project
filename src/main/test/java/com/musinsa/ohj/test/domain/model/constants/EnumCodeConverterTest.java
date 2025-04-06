package com.musinsa.ohj.test.domain.model.constants;

import com.musinsa.ohj.domain.model.constants.ApiResponseCode;
import com.musinsa.ohj.domain.model.constants.EnumCodeConverter;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class EnumCodeConverterTest {

    @Test
    @DisplayName("enum을 map형태로 변환 (순서유지)")
    void convertEnumToMapTest() throws Exception {
        Map<String, String> map = EnumCodeConverter.convertEnumToMap(ApiResponseCode.values());
        Map<String, String> descMap = EnumCodeConverter.convertEnumToMap(ApiResponseCode.values(), false);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(map.entrySet()
                    .stream()
                    .findFirst()
                    .get()
                    .getKey())
                    .isEqualTo(ApiResponseCode.SUCCESS.getCode());
            softAssertions.assertThat(descMap.entrySet()
                    .stream()
                    .findFirst()
                    .get()
                    .getKey())
                    .isEqualTo(ApiResponseCode.ALREADY_EXISTS_DATA.getCode());
        });
    }
}
