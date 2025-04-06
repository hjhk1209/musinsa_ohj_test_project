package com.musinsa.ohj.domain.model.constants;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public interface EnumCodeConverter {
    String getCode();
    String getMessage();
    int ordinal();

    static Map<String, String> convertEnumToMap(EnumCodeConverter[] codeEnumTypes) {
        return convertEnumToMap(codeEnumTypes, true);
    }

    static Map<String, String> convertEnumToMap(EnumCodeConverter[] codeEnumTypes, boolean isAsc) {
        return Arrays.stream(codeEnumTypes)
                .sorted(isAsc ?
                        Comparator.comparing(EnumCodeConverter::ordinal) :
                        Comparator.comparing(EnumCodeConverter::ordinal).reversed()
                )
                .collect(Collectors.toMap(
                        EnumCodeConverter::getCode,
                        EnumCodeConverter::getMessage,
                        (o1, o2) -> o1,
                        () -> new LinkedHashMap<>()));
    }
}
