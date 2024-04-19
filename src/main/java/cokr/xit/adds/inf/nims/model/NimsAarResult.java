package cokr.xit.adds.inf.nims.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * description :
 *
 * packageName : cokr.xit.adds.inf.nims.model
 * fileName    : NimsAarResult
 * author      : limju
 * date        : 2024-03-21
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-03-21    limju       최초 생성
 *
 * </pre>
 */
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NimsAarResult {
        /**
        * 결과코드
        * 0000 : 정상 보고 등록 되었습니다
        * 9999 : 보고 실패 되었습니다
        */
        @JsonProperty(value = "RESULT_CD", required = true)
        String resultCd;

        /**
        * 결과메시지
        * 0000 : 정상 보고 등록 되었습니다
        * 9999 : 보고 실패 되었습니다
        */
        @JsonProperty(value = "RESULT_MSG", required = true)
        String resultMsg;

        /**
        * 보고자식별ID
        * 보고자를 식별할 수 있는 고유 식별자
        * 시스템 내부에서 사용하는 식별자
        */
        @JsonProperty(value = "UID", required = true)
        String  uid;
        /**
        * 마약류취급자식별번호
        * 마약류통합관리 시스템에서 부여한 고유 코드
        */
        @JsonProperty(value = "BSSH_CD", required = true)
        String bsshCd;
        /**
        * 보고구분코드
        */
        @JsonProperty(value = "RPT_SE_CD", required = true)
        String rptSeCd;
        /**
        * 사용자보고식별번호
        */
        String usrPrtIdNo;
        /**
        * 보고접수번호
        */
        String rptRceptNo;
        /**
        * 에러코드
        */
        String errorCd;
        /**
        * 에러메시지
        */
        String errorMsg;
}
