package cokr.xit.adds.core.spring.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

/**
 * <pre>
 * description : WebClient 4xx 에러 : 에러 핸들러에서 사용
 *
 * author      : limju
 * date        : 2023-05-25
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2023-05-25    limju       최초 생성
 *
 * </pre>
 */
@Getter
public class ClientErrorException extends RuntimeException {
    private final HttpStatus status;
    private final String body;

    public ClientErrorException(HttpStatus status, String body) {
        this.status = status;
        this.body = body;
    }
}
