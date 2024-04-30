package cokr.xit.adds.inf.iros.model;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cokr.xit.adds.core.model.ResultCode;
import cokr.xit.adds.core.spring.exception.ApiCustomException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * description :
 *
 * packageName : cokr.xit.adds.inf.iros.model
 * fileName    : IrosResponse
 * author      : limju
 * date        : 2024-03-20
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-03-20    limju       최초 생성
 *
 * </pre>
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IrosResponse<T> {
    Header header;
    Body<T> body;

    /**
     * API call 성공시 결과 목록 return
     * 실패시 ApiCustomException throw
     * 결과 코드: 0-성공, 1-실패, 8-인증완료, 9-인증실패
     * @return List<T> or throw ApiCustomException
     */
    @JsonIgnore
    public List<T> getResultOrThrow() {
        if("00".equals(header.resultCode)){
            if(ObjectUtils.isEmpty(body.items)) {
                throw ApiCustomException.create(ResultCode.NO_CONTENT);
            }
            return body.items;
        }
        throw Objects.requireNonNull(ApiCustomException.of(header.resultCode, header.resultMsg));
    }

    /**
     * API call 성공시 결과 목록 return
     * 실패시 null return
     * 결과 코드: 0-성공, 1-실패, 8-인증완료, 9-인증실패
     * @return List<T> or null
     */
    @JsonIgnore
    public List<T> getResult() {
        if("00".equals(header.resultCode)) {
            return body.items;
        }
        throw Objects.requireNonNull(ApiCustomException.of(header.resultCode, header.resultMsg));
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Header {
        String resultCode;
        String resultMsg;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Body<T> {
        String pageNo;
        String numOfRows;
        String totalCount;
        List<T> items;
    }
}
// public record IrosResponse<T>(
//     Header header,
//     Body<T> body
// ) {
//     public static record Header(
//         String resultCode,
//         String resultMsg
//     ) {
//     }
//
//     public static record Body<T>(
//         String pageNo,
//         String numOfRows,
//         String totalCount,
//         List<T> items
//     ) {
//     }
// }
