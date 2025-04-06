package com.musinsa.ohj.common.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@UtilityClass
public class DateUtils {

    public static final String ISO_8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    public static String getNowIso8601Format() {
        ZonedDateTime zonedDateTime = LocalDateTime.now().atZone(TimeZone.getDefault().toZoneId());
        return zonedDateTime.format(DateTimeFormatter.ofPattern(ISO_8601_FORMAT));
    }
}
