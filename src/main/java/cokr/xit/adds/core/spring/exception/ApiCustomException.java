package cokr.xit.adds.core.spring.exception;

import cokr.xit.adds.core.model.ResultCode;
import cokr.xit.adds.core.util.CoreSpringUtils;
import cokr.xit.adds.inf.nims.model.NimsApiResult;
import lombok.Getter;

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
@Getter
public class ApiCustomException extends RuntimeException {
    private Integer code = ResultCode.INVALID_DATA.getStatusCode();
    private String message = ResultCode.findByStatusCode(code).getMessage();

    private ApiCustomException() {
        super(ResultCode.INVALID_DATA.getMessage());
    }
    private ApiCustomException(String message) {
        super(message);
        this.message = message;
    }

    private ApiCustomException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    private ApiCustomException(String msgCode, String  message) {
        super(message);
        this.code = ResultCode.INVALID_DATA.getStatusCode();
        this.message = message;
    }

    /**
     * code로 ApiCustomException 생성
     * @param code Integer
     * @return ApiCustomException
     */
    public static ApiCustomException create(ResultCode code) {
        return new ApiCustomException(code.getStatusCode(), ResultCode.findByStatusCode(code.getStatusCode()).getMessage());
    }

    /**
     * message로 ApiCustomException 생성
     * @param message String
     * @return ApiCustomException
     */
    public static ApiCustomException create(String message) {
        return new ApiCustomException(message);
    }

    /**
     * code, message로 ApiCustomException 생성
     * @param code Integer
     * @param message String
     * @return ApiCustomException
     */
    public static ApiCustomException create(Integer code, String message) {
        return new ApiCustomException(code, message);
    }

    /**
     * code로 ApiCustomException 생성
     * @param code String
     * @return ApiCustomException
     */
    public static ApiCustomException of(String code) {
        return new ApiCustomException(code, CoreSpringUtils.getMessageUtil().getMessage(code));
    }

    /**
     * code, message로 ApiCustomException 생성
     * @param code String
     * @param message String
     * @return ApiCustomException
     */
    public static ApiCustomException of(String code, String message) {
        return new ApiCustomException(Integer.parseInt(code), message);
    }

    /**
     * NimsApiResult.Header로 ApiCustomException 생성
     * @param header NimsApiResult.Header
     * @return ApiCustomException
     */
    public static ApiCustomException of(NimsApiResult.Header header) {
        if(header.getResultCd() == 0) return null;
        return new ApiCustomException(header.getResultCd(), header.getResultMsg());
    }
}
