package cokr.xit.adds.core.model;

import java.util.Arrays;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ResultCode{

    // 2xx
    SUCCESS(HttpStatus.OK.value(), "성공"), // 200
    NO_CONTENT(HttpStatus.NO_CONTENT.value(), "데이터가 없습니다"), // 204

    // 4xx
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "항목이 올바르지 않습니다"), // 400
    INVALID_DATA(HttpStatus.BAD_REQUEST.value(), "데이터 처리 오류 발생"), // 400
    LOGIN_ERROR(HttpStatus.BAD_REQUEST.value(), "아이디 혹은 비밀번호를 다시 확인하세요."), // 400
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST.value(), "필수 파라미터가 누락되었습니다"), // 500
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "인증이 필요합니다"), // 401
    FORBIDDEN(HttpStatus.FORBIDDEN.value(), "권한이 없습니다"), // 403
    TOKEN_EXPIRED(HttpStatus.FORBIDDEN.value(), "토큰이 만료되었습니다"), // 403
    INVALID_ACCESS_TOKEN(HttpStatus.FORBIDDEN.value(), "토큰이 유효하지 않습니다"), // 403
    NOT_FOUND(HttpStatus.NOT_FOUND.value(), "요청한 api를 찾을 수 없습니다."), // 404

    // 5xx
    INVALID_JSON(HttpStatus.INTERNAL_SERVER_ERROR.value(), "전달된 JSON 형식이 올바르지 않습니다"), // 500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 에러가 발생했습니다"), // 500
    ;

    private final Integer statusCode;
    private final String message;

    ResultCode(Integer statusCode, String message){
        this.statusCode = statusCode;
        this.message = message;
    }

    public static ResultCode findByStatusCode(int statusCode){
        return Arrays.stream(ResultCode.values())
            .filter(ssc -> ssc.getStatusCode() == statusCode)
            .findFirst()
            .orElse(INVALID_DATA);
    }
}
