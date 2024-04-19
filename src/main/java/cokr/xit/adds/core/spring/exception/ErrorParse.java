package cokr.xit.adds.core.spring.exception;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import cokr.xit.adds.core.model.ApiBaseResponse;
import cokr.xit.adds.core.model.ResultCode;

/**
 * <pre>
 * description :
 *
 * author      : limju
 * date        : 2023-06-01
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2023-06-01    limju       최초 생성
 *
 * </pre>
 */
public class ErrorParse {

    @SuppressWarnings("rawtypes")
    public static ApiBaseResponse extractError(final Throwable e){
        Integer errCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        String message = ObjectUtils.isNotEmpty(e) ? e.getLocalizedMessage() : StringUtils.EMPTY;

        if(e instanceof ApiCustomException be) {
            return new ApiBaseResponse(be.getCode(), be.getMessage());
        }

        if(e instanceof ClientErrorException ce) {
            return new ApiBaseResponse(ResultCode.INTERNAL_SERVER_ERROR.getStatusCode(), ce.getBody());
        }

        if(e instanceof ServerErrorException ce) {
            return new ApiBaseResponse(ResultCode.INTERNAL_SERVER_ERROR.getStatusCode(), ce.getBody());
        }

        if(ObjectUtils.isNotEmpty(e.getCause())) {
            message = e.getCause().getMessage();
        }

        return new ApiBaseResponse(errCode, message);
    }

    @SuppressWarnings("rawtypes")
    private static  ApiBaseResponse getTimeoutException(){
        return new ApiBaseResponse(
                HttpStatus.REQUEST_TIMEOUT.value(),
                HttpStatus.REQUEST_TIMEOUT.getReasonPhrase());
    }
}
