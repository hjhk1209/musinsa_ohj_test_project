package com.musinsa.ohj.test.common;

import com.musinsa.ohj.common.utils.DateUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.assertj.core.api.Assertions.assertThat;

public class DateUtilsTest {

    @Test
    @DisplayName("ISO8601 포맷 테스트")
    void getNowIso8601FormatTest() {
        assertThat(isValidFormat(DateUtils.ISO_8601_FORMAT, DateUtils.getNowIso8601Format())).isTrue();
    }
    private boolean isValidFormat(String pattern, String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        try {
            LocalDateTime.parse(dateTimeString, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
