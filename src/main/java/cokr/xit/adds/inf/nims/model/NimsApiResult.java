package cokr.xit.adds.inf.nims.model;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import cokr.xit.adds.core.model.ResultCode;
import cokr.xit.adds.core.spring.exception.ApiCustomException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NimsApiResult<T> {
    /**
     * Nims API call 결과(ROOT)
     */
    private Response<T> response;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response<T> {
        /**
         * API call 결과 헤더
         */
        private Header header;

        /**
         * API call 결과 바디
         */
        private Body<T> body;

        /**
         * API call 성공시 결과 목록 return
         * 실패시 ApiCustomException throw
         * 결과 코드: 0-성공, 1-실패, 8-인증완료, 9-인증실패
         * @return List<T> or throw ApiCustomException
         */
        @JsonIgnore
        public List<T> getResultOrThrow() {
            if(header.resultCd == 0 || header.resultCd == 8){
                if(ObjectUtils.isEmpty(body.list)) {
                    throw ApiCustomException.create(ResultCode.NO_CONTENT);
                }
                return body.list;
            }
            throw Objects.requireNonNull(ApiCustomException.of(header));
        }

        /**
         * API call 성공시 결과 목록 return
         * 실패시 null return
         * 결과 코드: 0-성공, 1-실패, 8-인증완료, 9-인증실패
         * @return List<T> or null
         */
        @JsonIgnore
        public List<T> getResult() {
            //return getResult();
            if(header.resultCd == 0 || header.resultCd == 8) {
                return body.list;
            }
            throw Objects.requireNonNull(ApiCustomException.of(header));
        }

        @JsonIgnore
        public boolean isEndYn() {
            return "Y".equals(body.isEndYn);
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Header {
        /**
         * 결과코드
         * 0 : 성공, 1:실패, 8:인증완료, 9:인증실패
         */
        @JsonAlias({"RESULT_CODE", "resultCd"})
        private Integer resultCd;

        /**
         * 결과메세지
         */
        @JsonAlias({"RESULT_MSG", "resultMsg"})
        private String resultMsg;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Body<T>{
        /**
         * 실행 결과 건수
         */
        @JsonAlias("TOTAL_COUNT")
        private Integer totalCount;

        /**
         * 마지막 데이타 여부
         * Y: 마지막 데이타, N : 마지막 데이타 아님
         */
        @JsonAlias(value = {"IS_END_YN", "isEndYn"})
        private String isEndYn;

        /**
         * 전체 데이타 건수
         */
        @JsonProperty(value = "nRecord", required = true)
        private Integer nRecord;

        /**
         * 요청 결과 목록
         */
        private List<T> list;
    }
}
