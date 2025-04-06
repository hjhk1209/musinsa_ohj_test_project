package com.musinsa.ohj.common.utils;

import lombok.experimental.UtilityClass;

import java.util.concurrent.ThreadLocalRandom;

@UtilityClass
public class StrUtils {

    public static String getRandomAlphanumeric(int size) {
        int start = 48;
        int end = 122;

        return ThreadLocalRandom.current().ints(start, end + 1)
                .filter(i -> (i <= 57 || (i >= 65 && i <= 90) || i >= 97))
                .limit(size)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
