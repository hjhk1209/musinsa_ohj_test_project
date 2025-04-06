package com.musinsa.ohj.test.common;

import com.musinsa.ohj.common.utils.StrUtils;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StrUtilsTest {
    @Test
    @DisplayName("랜덤 알파벳 및 문자열 테스트")
    void getRandomAlphanumericTest() {
        int size = 20;
        String randomStr = StrUtils.getRandomAlphanumeric(size);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(randomStr).isNotEmpty();
            softAssertions.assertThat(randomStr).matches("\\w+");
            softAssertions.assertThat(randomStr).hasSize(size);
            softAssertions.assertThat(randomStr).isNotEqualTo(StrUtils.getRandomAlphanumeric(size));
        });
    }
}
