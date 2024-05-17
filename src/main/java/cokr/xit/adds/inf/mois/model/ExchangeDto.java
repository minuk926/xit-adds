package cokr.xit.adds.inf.mois.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <pre>
 * description : 온나라전자결재연계 exchange.dtd 구조 정의
 *
 * packageName : cokr.xit.adds.inf.mois.model
 * fileName    : ExchangeDto
 * author      : limju
 * date        : 2024-03-14
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-03-14    limju       최초 생성
 *
 * </pre>
 */
@JacksonXmlRootElement(localName = "EXCHANGE")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExchangeDto extends ExchangeCommon {
    @JacksonXmlProperty(localName =  "HEADER")
    @JsonProperty(required = true)
    private Header header;

    /**
     * <pre>
     * 행정정보시스템 -> 전자문서시스템 전송
     * - 결재문서의 본문으로 전자문서시스템 기안문의 본문
     *   선택사항이며, 포함되지 않을 경우 전자문서시스템에서 작성
     *   MODIFICATION_FLAG와 상관없이 수정 가능
     *
     * 전자문서시스템 -> 행정정보시스템 전송
     * - 결재 처리한 기안문의 본문
     *   -> 전송한 연계본문을 수정한 경우
     *      연계본문, 행정정보문서, 일반첨부문서를 포함하여 전송
     *   -> 전송한 연계본문을 수정하지 않은 경우
     *      결재처리정보만 전송
     * </pre>
     */
    @JacksonXmlProperty(localName =  "BODY")
    @JacksonXmlCData
    private String body;

    @JacksonXmlProperty(localName =  "ATTACHMENTS")
    private Attachments attachments;

    /**
     * 헤더 정보
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Header {
        /**
         * 공통 헤더 정보
         */
        @JacksonXmlProperty(localName = "COMMON")
        @JsonProperty(required = true)
        private Common common;

        /**
         * 연계 방향
         */
        @JacksonXmlProperty(localName = "DIRECTION")
        @JsonProperty(required = true)
        private Direction direction;

        /**
         * 추가 헤더 정보
         */
        @JacksonXmlProperty(localName =  "ADDENDA")
        private Addenda addenda;
    }

    /**
     * <pre>
     *     연계 방향
     *     ToAdministrativeSystem : 전자문서시스템 -> 행정정보시스템
     *     ToDocumentSystem : 행정정보시스템 -> 전자문서시스템
     * </pre>
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Direction {
        /**
         * 행정정보시스템으로 보내는 정보
         */
        @JacksonXmlProperty(localName =  "TO_ADMINISTRATIVE_SYSTEM")
        private ToAdministrativeSystem toAdministrativeSystem;

        /**
         * 전자문서시스템으로 보내는 정보
         */
        @JacksonXmlProperty(localName =  "TO_DOCUMENT_SYSTEM")
        private ToDocumentSystem toDocumentSystem;
    }

    /**
     * <pre>
     * 전자문서시스템 -> 행정정보시스템으로 보내는 정보
     * </pre>
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ToAdministrativeSystem {
        /**
         * <pre>
         * 결재 문서 정보
         * 결재 완료된 기록물등록대장의 문서 번호
         * </pre>
         */
        @JacksonXmlProperty(localName = "DOCNUM")
        @JsonProperty(required = true)
        private Docnum docnum;

        /**
         * 결재 정보
         */
        @JacksonXmlProperty(localName = "SANCTION_INFO")
        @JsonProperty(required = true)
        private SanctionInfo sanctionInfo;

        /**
         * 수정 여부
         */
        @JacksonXmlProperty(localName = "MODIFICATION_FLAG")
        @JsonProperty(required = true)
        private ModificationFlag modificationFlag;
    }

    /**
     * <pre>
     * 행정정보시스템 -> 전자문서시스템으로 보내는 정보
     * </pre>
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class ToDocumentSystem {
        /**
         * <pre>
         * 결재 처리 결과 통보 방식(최종 결재후 통보, 결재 단계별 통보) 알림
         * all | final
         * </pre>
         */
        @JacksonXmlProperty(isAttribute = true, localName = "notification")
        @JsonProperty(required = true)
        @Builder.Default
        String notification = "all";

        /**
         * 전체 결재선 정보
         */
        @JacksonXmlProperty(localName =  "LINES")
        private Lines lines;

        /**
         * <pre>
         * 수정 여부
         * 전자문서시스템에서 연계요청한 문서와 첨부파일의 수정가능 여부
         * </pre>
         */
        @JacksonXmlProperty(localName =  "MODIFICATION_FLAG")
        @JsonProperty(required = true)
        private ModificationFlag modificationFlag;
    }


}
