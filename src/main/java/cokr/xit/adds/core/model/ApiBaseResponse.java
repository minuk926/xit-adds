package cokr.xit.adds.core.model;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Objects;

import org.apache.commons.lang3.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonRootName;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * description :
 *
 * packageName : kr.xit.core.model
 * fileName    : ApiBaseResponse
 * author      : limju
 * date        : 2024-03-15
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-03-15    limju       최초 생성
 *
 * </pre>
 */
@Schema(name = "ApiBaseResponse", description = "Api 결과 DTO")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@JsonRootName("result")
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiBaseResponse<T> {
    @Schema(requiredMode = REQUIRED, title = "결과 코드", description = "결과 코드", example = "200")
    private Integer code = ResultCode.SUCCESS.getStatusCode();

    @Schema(requiredMode = REQUIRED, title = "결과 메세지", description = "결과 메세제-에러인 경우 필수", example = " ")
    private String message = ResultCode.SUCCESS.getMessage();

    @Schema(requiredMode = REQUIRED, title = "API 실행 결과", description = "정상 실행시 결과 값", example = " ")
    private T data;

    @Schema(requiredMode = REQUIRED, title = "실행 결과 건수", description = "실행 결과 건수", example = " ")
    private int totalCount;

    @Schema(requiredMode = REQUIRED, title = "API 실행 성공 or 실패", description = "API 실행 성공(true) or 실패(false)", example = " ")
    private Boolean success = Objects.equals(code, ResultCode.SUCCESS.getStatusCode());

    @Schema(requiredMode = REQUIRED, title = "API 실행 시간", description = "API 실행 시간", example = " ")
    @Builder.Default
    private String responseTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    public static <T> ApiBaseResponse<T> of() {
        return new ApiBaseResponse<>();
    }

    public static <T> ApiBaseResponse<T> of(T data) {
        return new ApiBaseResponse<>(data);
    }

    public static <T> ApiBaseResponse<T> empty() {
        return new ApiBaseResponse<>(null, ResultCode.NO_CONTENT);
    }

    private ApiBaseResponse(T data) {

        if(ObjectUtils.isEmpty(data)) {
            this.code = ResultCode.NO_CONTENT.getStatusCode();
            this.message = ResultCode.NO_CONTENT.getMessage();
            this.data = null;
        }else{
            this.code = ResultCode.SUCCESS.getStatusCode();
            this.message = ResultCode.findByStatusCode(code).getMessage();
            this.data = data;
        }
    }

    private ApiBaseResponse(T data, ResultCode resultCode) {
        this.success = true;
        this.code = resultCode.getStatusCode();
        this.message = resultCode.getMessage();
        this.data = null;
    }

    public ApiBaseResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiBaseResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getTotalCount() {
        if(data == null){
            this.totalCount = 0;

        }else {

            if (Collection.class.isAssignableFrom(data.getClass())) {
                this.totalCount = ((Collection<?>) data).size();

            } else {
                this.totalCount =  1;
            }
        }
        return totalCount;
    }
}
