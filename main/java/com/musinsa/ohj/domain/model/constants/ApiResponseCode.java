package com.musinsa.ohj.domain.model.constants;

import com.musinsa.ohj.presentation.response.ApiResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.function.Function;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum ApiResponseCode implements EnumCodeConverter {

    SUCCESS("S000", "성공했습니다", v -> ResponseEntity.ok().body(v)),
    BAD_REQUEST("E400", "잘못 요청된 파라미터", v -> ResponseEntity.badRequest().body(v)),
    NOT_EXIST_RESOURCE("E404", "요청하신 리소스를 찾을 수 없습니다", v -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(v)),
    METHOD_NOT_ALLOWED("E405", "지원되지 않는 HTTP 메서드", v -> ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(v)),
    UNSUPPORTED_MEDIA_TYPE("E415", "지원되지 않는 Media Type", v -> ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(v)),
    INTERNAL_SERVER_ERROR("E500", "서버 내부 오류", v -> ResponseEntity.internalServerError().body(v)),
    NOT_FOUND_DATA("E600", "요청 하신 데이터가 없습니다", v -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(v)),
    ALREADY_EXISTS_DATA("E601", "이미 존재하는 데이터입니다", v -> ResponseEntity.status(HttpStatus.CONFLICT).body(v));

    String code;
    String message;
    Function<ApiResponse, ResponseEntity<ApiResponse>> convertToResponseExpression;

    ApiResponseCode(String code,
                    String message,
                    Function<ApiResponse, ResponseEntity<ApiResponse>> convertToResponseExpression) {
        this.code = code;
        this.message = message;
        this.convertToResponseExpression = convertToResponseExpression;
    }

    public ResponseEntity<ApiResponse> getResponseEntity(ApiResponse apiResponse) {
        return convertToResponseExpression.apply(apiResponse);
    }
}
