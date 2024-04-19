package cokr.xit.adds.inf.nims.model;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import cokr.xit.adds.core.model.ResultCode;
import cokr.xit.adds.core.spring.exception.ApiCustomException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * description :
 *
 * packageName : cokr.xit.adds.inf.nims.model
 * fileName    : NimsApiDto
 * author      : limju
 * date        : 2024-03-21
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-03-21    limju       최초 생성
 *
 * </pre>
 */
@Schema(name = "NimsApiResult", description = "마약류 관리 시스템 API response")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NimsApiResult<T> {
    @Schema(description = "response", requiredMode = REQUIRED)
    @JsonProperty(value = "response", required = true)
    private Response<T> response;

    /**
     * API call 성공시 결과 목록 return
     * 실패시 ApiCustomException throw
     * 결과 코드: 0-성공, 1-실패, 8-인증완료, 9-인증실패
     * @return List<T> or throw ApiCustomException
     */
    @JsonIgnore
    public List<T> getResultOrThrow() {
        if(!ObjectUtils.isEmpty(getResult())) return getResult();
        if(response.header.resultCd == 0 || response.header.resultCd == 8){
            throw ApiCustomException.create(ResultCode.NO_CONTENT);
        }
        throw Objects.requireNonNull(ApiCustomException.of(response.header));
    }

    /**
     * API call 성공시 결과 목록 return
     * 실패시 null return
     * 결과 코드: 0-성공, 1-실패, 8-인증완료, 9-인증실패
     * @return List<T> or null
     */
    @JsonIgnore
    public List<T> getResultOrNull() {
        return getResult();
    }

    /**
     * Header 정보 return
     * @return Header
     */
    public Header getHeader() {
        return response.header;
    }

    /**
     * Body 정보 return
     * @return Body<T>
     */
    public Body<T> getBody() {
        return response.body;
    }

    private List<T> getResult() {
        if(response.header.resultCd == 0 || response.header.resultCd == 8) {
            return response.body.list;
        }
        return null;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response<T> {
        @Schema(description = "header", requiredMode = REQUIRED)
        @JsonProperty(value = "header", required = true)
        private Header header;

        @Schema(description = "body", requiredMode = REQUIRED)
        @JsonProperty(value = "body", required = true)
        private Body<T> body;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Header {
        /**
         * 결과코드
         * 0 : 성공, 1:실패, 8:인증완료, 9:인증실패
         */
        @Schema(description = "결과 코드: 0-성공, 1-실패, 8-인증완료, 9-인증실패", requiredMode = REQUIRED)
        @JsonProperty(value = "RESULT_CODE", required = true)
        private Integer resultCd;
        @Schema(description = "결과메세지", requiredMode = REQUIRED)
        @JsonProperty(value = "RESULT_MSG", required = true)
        private String resultMsg;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Body<T>{
        @Schema(description = "결과 건수", requiredMode = REQUIRED)
        @JsonProperty(value = "TOTAL_COUNT", required = true)
        private Integer totalCount;
        /**
         * 마지막 데이타 여부
         * Y: 마지막 데이타, N : 마지막 데이타 아님
         */
        @Schema(description = "마지막데이타 여부: Y-마지막 데이타, N-아님", requiredMode = REQUIRED)
        @JsonProperty(value = "IS_END_YN", required = true)
        private String isEndYn;

        @Schema(description = "전체 데이타 건수", requiredMode = REQUIRED)
        @JsonProperty(value = "nRecord", required = true)
        private Integer nRecord;

        @Schema(description = "요청 데이타 목록")
        @JsonProperty(value = "list")
        private List<T> list;
    }
}
