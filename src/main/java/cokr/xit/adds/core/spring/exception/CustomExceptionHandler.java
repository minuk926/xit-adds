package cokr.xit.adds.core.spring.exception;

import java.security.SignatureException;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

import cokr.xit.adds.core.model.ApiBaseResponse;
import cokr.xit.adds.core.model.ResultCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

/**
 * <pre>
 * description :
 *
 * author      : limju
 * date        : 2024-03-15
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-03-15    limju       최초 생성
 *
 * </pre>
 */
@RestControllerAdvice(basePackages = {"cokr.xit"})
public class CustomExceptionHandler {
    /**
     * 어노테이션이 사용될때, DTO 검증 실패시 예외처리
     * @param ex MethodArgumentNotValidException
     * @return ApiBaseResponse
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiBaseResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        final String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return makeEntity(ResultCode.BAD_REQUEST.getStatusCode(), message, HttpStatus.BAD_REQUEST);
    }


    /**
     * 매개변수 값이 올바르게 처리 되지 않았을때 에러처리
     * @param ex IllegalArgumentException
     * @return BaseResponse
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiBaseResponse<?>> illegalArgumentException(IllegalArgumentException ex) {
        return makeEntity(ResultCode.BAD_REQUEST.getStatusCode(), ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Null 값이 발생했을때 예외처리
     * @param ex NullPointerException
     * @return BaseResponse
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiBaseResponse<?>> handle(NullPointerException ex) {
        return makeEntity(ResultCode.BAD_REQUEST.getStatusCode(), ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * 외부 API 호출시 에러처리
     * @param ex HttpClientErrorException
     * @return BaseResponse
     */
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ApiBaseResponse<?>> clientErrorHandle(HttpClientErrorException ex) {
        if (ex.getStatusCode().value() == HttpStatus.UNAUTHORIZED.value()) {
            //"Login 페이지 이동??"
            return makeEntity(ResultCode.UNAUTHORIZED.getStatusCode(), ex.getMessage(), HttpStatus.UNAUTHORIZED);

        }
        return makeEntity(ResultCode.BAD_REQUEST.getStatusCode(), ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * 잘못된 Request Method(GET, POST, PUT..)으로 호출되었을때 예외처리
     * @param ex HttpRequestMethodNotSupportedException
     * @param req HttpServletRequest
     * @return BaseResponse
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiBaseResponse<?>> httpRequestMethodNotSupportedException(
        HttpRequestMethodNotSupportedException ex,
        HttpServletRequest req
        ) {
        return makeEntity(
            ResultCode.INTERNAL_SERVER_ERROR.getStatusCode(),
            "Does not support request method '" + req.getMethod() + "'",
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }


    /**
     * 기본적인 에러 처리
     * @param ex Exception
     * @return BaseResponse
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiBaseResponse<?>> handleException(Exception ex) {
        ResultCode resultCode = null;

		if (ex instanceof BindException e) {
			BindingResult bindingResult = e.getBindingResult();
			StringBuilder sb = new StringBuilder();

			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				sb.append(fieldError.getField()).append(":");
				sb.append(fieldError.getDefaultMessage());
				sb.append(", ");
			}
			return makeEntity(ResultCode.INVALID_DATA.getStatusCode(), sb.toString(), HttpStatus.valueOf(ResultCode.INVALID_DATA.getStatusCode()));

		} else if (ex instanceof HttpMessageNotReadableException) {
			resultCode = ResultCode.INVALID_DATA;
		} else if (ex instanceof SignatureException || ex instanceof SecurityException
			|| ex instanceof MalformedJwtException) {
			resultCode = ResultCode.INVALID_ACCESS_TOKEN;
		} else if (ex instanceof ExpiredJwtException) {
			resultCode = ResultCode.TOKEN_EXPIRED;
		} else if (ex instanceof NoHandlerFoundException) {
			resultCode = ResultCode.NOT_FOUND;
		} else {
			resultCode = ResultCode.INTERNAL_SERVER_ERROR;
		}

        return makeEntity(resultCode.getStatusCode(), resultCode.getMessage(), HttpStatus.valueOf(resultCode.getStatusCode()));
    }

    /**
     * 커스텀 에러 처리
     * @param ex ApiCustomException
     * @return BaseResponse
     */
    @ExceptionHandler(ApiCustomException.class)
    public ResponseEntity<ApiBaseResponse<?>> apiCustomException(ApiCustomException ex) {
        if (Objects.equals(ex.getCode(), ResultCode.NO_CONTENT.getStatusCode())){
			return new ResponseEntity<>(
				ApiBaseResponse.builder()
					.success(true)
					.code(ex.getCode())
					.message(ex.getMessage())
					.build(),
				// headers,
				HttpStatus.OK
			);
        }
        return makeEntity(ex.getCode(), ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ApiBaseResponse<?>> makeEntity(final Integer code, final String message, final HttpStatus status) {
        ApiBaseResponse<?> build = ApiBaseResponse.builder()
            .success(false)
            .code(code)
            .message(message)
            .build();
		// HttpHeaders headers = new HttpHeaders();
		// headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(
            build,
			// headers,
            status
        );
    }
}
