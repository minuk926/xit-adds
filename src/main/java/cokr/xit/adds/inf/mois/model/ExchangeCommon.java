package cokr.xit.adds.inf.mois.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

import jakarta.xml.bind.annotation.XmlValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * description : 온나라시스템연계 dtd 구조 정의
 *               온나라전자문서(exchange_mis.dtd)와 온나라전자결재(exchange.dtd)의 공통 구조 정의
 *
 * packageName : cokr.xit.adds.inf.mois.model
 * fileName    : ExchangeCommon
 * author      : limju
 * date        : 2024-03-22
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-03-22    limju       최초 생성
 *
 * </pre>
 */
public class ExchangeCommon {

    /**
     * <pre>
     *     전자결재연계 공통헤더 정보
     * </pre>
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Common {
        /**
         * 송신자 정보
         */
        @JacksonXmlProperty(localName = "SENDER")
        @JsonProperty(required = true)
        private Sender sender;

        /**
         * 수신자 정보
         */
        @JacksonXmlProperty(localName = "RECEIVER")
        @JsonProperty(required = true)
        private Receiver receiver;

        /**
         * <pre>
         *     연계 본문 제목
         *     기안문의 제목으로 이용
         * </pre>>
         */
        @JacksonXmlProperty(localName = "TITLE")
        @JsonProperty(required = true)
        @JacksonXmlCData
        private String title;

        /**
         * 연계 본문 생성 일시 : YYYY-MM-DD HH:mm:ss
         */
        @JacksonXmlProperty(localName = "CREATED_DATE")
        @JsonProperty(required = true)
        private String createdDate;

        /**
         * 첨부 문서 갯수
         */
        @JacksonXmlProperty(localName = "ATTACHNUM")
        @JsonProperty(required = true)
        private int attachnum;

        /**
         * <pre>
         *     행정 처리 번호
         *     행정정보시스템의 유일키(폐기관리 ID)
         * </pre>
         */
        @JacksonXmlProperty(localName = "ADMINISTRATIVE_NUM")
        @JsonProperty(required = true)
        private String administrativeNum;
    }

    /**
     * 송신자 정보
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Sender {
        /**
         * 송신 시스템 ID
         */
        @JacksonXmlProperty(localName = "SERVERID")
        @JsonProperty(required = true)
        private String serverid;

        /**
         * 송신 사용자 ID
         */
        @JacksonXmlProperty(localName = "USERID")
        @JsonProperty(required = true)
        private String userid;

        /**
         * 송신 사용자 메일
         */
        @JacksonXmlProperty(localName = "EMAIL")
        private String email;
    }

    /**
     * 수신자  정보
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Receiver {
        /**
         * 수신 시스템 ID
         */
        @JacksonXmlProperty(localName = "SERVERID")
        @JsonProperty(required = true)
        private String serverid;

        /**
         * 수신 사용자 ID
         */
        @JacksonXmlProperty(localName = "USERID")
        @JsonProperty(required = true)
        private String userid;

        /**
         * 수신 사용자 메일
         */
        @JacksonXmlProperty(localName = "EMAIL")
        private String email;
    }

    /**
     * <pre>
     * 결재 문서 정보
     * 결재 완료된 기록물등록대장의 문서 번호
     * </pre>
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Docnum {
        /**
         * <pre>
         * 전자문서시스템의 기록물등록대장에서 관리하는 등록번호의 내부 코드정보
         * - 부서코드 7자리 + 문서번호 6자리로 기록물등록대장에 등록된 13자리 코드
         * 예) 행정정보화팀-36의 경우 : 1310505000036
         * </pre>
         */
        @JacksonXmlProperty(localName = "docnumcode", isAttribute = true)
        @JsonProperty(required = true)
        @JacksonXmlCData
        private String docnumcode;

        @XmlValue
        @JacksonXmlText
        private String value;
    }

    /**
     * <pre>
     * 결재 정보 - 결재 진행 상태
     * </pre>
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SanctionInfo {
        /**
         * 진행 | 완료
         */
        @JacksonXmlProperty(localName = "status", isAttribute = true)
        @JsonProperty(required = true)
        private String status;

        /**
         * 전체 결재선 정보
         */
        @JacksonXmlProperty(localName = "LINES")
        @JsonProperty(required = true)
        private Lines lines;
    }

    /**
     * 전체 결재선 정보
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Lines {
        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "LINE")
        @JsonProperty(required = true)
        private List<Line> line;
    }

    /**
     * 개별 결재선 정보
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Line {
        /**
         * <pre>
         * 결재 단계
         * 기안 - 1
         * 중간 결재 - 2, 3, 4
         * 최종 결재(전결, 대결, 결재) - final
         * </pre>
         */
        @JacksonXmlProperty(localName = "LEVEL")
        @JsonProperty(required = true)
        private String level;

        /**
         * 결재
         */
        @JacksonXmlProperty(localName = "SANCTION")
        @JsonProperty(required = true)
        private Sanction sanction;
    }

    /**
     * 결재 정보
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Sanction {
        /**
         * <pre>
         * 결재 결과
         * 상신 | 기안취소 | 승인 | 반려 | 미결
         * </pre>
         */
        @JacksonXmlProperty(localName = "result", isAttribute = true)
        @JsonProperty(required = true)
        private String result;

        /**
         * <pre>
         * 결재 유형
         * 기안 | 검토 | 협조 | 전결 | 대결 | 결재
         * </pre>
         */
        @JacksonXmlProperty(localName = "type", isAttribute = true)
        @JsonProperty(required = true)
        private String type;

        /**
         * <pre>
         * 결재선에 있는 사용자 정보
         * 기안자 / 검토자 / 협조자 / 대결자 / 전결자 / 결재자
         * </pre>
         */
        @JacksonXmlProperty(localName = "PERSON")
        @JsonProperty(required = true)
        private Person person;

        /**
         * 결재 의견
         */
        @JacksonXmlProperty(localName = "COMMENT")
        private String comment;

        /**
         * 결재 완료 일시 : YYYY-MM-DD HH:mm:ss
         */
        @JacksonXmlProperty(localName = "DATE")
        @JsonProperty(required = true)
        private String date;
    }

    /**
     * <pre>
     * 결재선에 있는 사용자 정보
     * 기안자 / 검토자 / 협조자 / 대결자 / 전결자 / 결재자
     * </pre>
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Person {
        @JacksonXmlProperty(localName = "USERID")
        @JsonProperty(required = true)
        private String userid;

        @JacksonXmlProperty(localName = "NAME")
        @JsonProperty(required = true)
        private String name;

        /**
         * <pre>
         * 직위(직급)
         * 직위 우선, 직위가 없는 경우 직급 사용
         * </pre>
         */
        @JacksonXmlProperty(localName = "POSITION")
        @JsonProperty(required = true)
        private String position;

        /**
         * 부서
         */
        @JacksonXmlProperty(localName = "DEPT")
        @JsonProperty(required = true)
        private String dept;

        /**
         * 기관
         */
        @JacksonXmlProperty(localName = "ORG")
        @JsonProperty(required = true)
        private String org;
    }

    /**
     * <pre>
     * 첨부 및 연계파일 수정(가능)
     * </pre>
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ModificationFlag {
        /**
         * <pre>
         * 첨부 및 연계파일 수정(가능) 여부
         * 행정정보시스템 -> 전자문서시스템 연계 - 전자문서시스템에서 수정 가능한지 여부
         * 전자문서시스템 -> 행정정보시스템 연계 - 연계 문서 수정 여부
         * </pre>
         */
        @JacksonXmlProperty(localName = "MODIFIABLE")
        @JsonProperty(required = true)
        private Modifiable modifiable;

        /**
         * <pre>
         * 첨부 및 연계파일 수정 시간
         * 전자문서시스템에서 문서를 수정한 후 결재(혹은 협조, 검토)한 일시
         * 전자문서시스템 -> 행정정보시스템 연계인 경우만 의미 있다
         * </pre>
         */
        @JacksonXmlProperty(localName = "MODIFIED")
        private String modified;
    }

    /**
     * <pre>
     *     수정 여부
     * </pre>
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Modifiable {
        /**
         * <pre>
         * 연계 첨부 및 첨부 파일의 수정(가능) 여부 : default no
         * 행정정보시스템 -> 전자문서시스템 연계 - 전자문서시스템에서 수정 가능한지 여부
         * 전자문서시스템 -> 행정정보시스템 연계 - 연계 문서 수정 여부
         * yes | no
         * </pre>
         */
        @JacksonXmlProperty(localName = "modifyflag", isAttribute = true)
        @JsonProperty(required = true)
        @Builder.Default
        private String modifyflag = "no";
    }

    /**
     * <pre>
     * 추가 헤더 정보
     * 필요시 행정정보시스템에서 헤더 정보를 추가적으로 정의
     * 전자문서시스템에서 결과 발송시 반드시 포함하여 전송
     * </pre>
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Addenda {
        /**
         * 개별 추가 헤더 정보
         */
        @JacksonXmlProperty(localName = "ADDENDUM")
        @JsonProperty(required = true)
        private Addendum addendum;
    }

    /**
     * <pre>
     * 개별 헤더 정보
     * 필요시 행정정보시스템에서 헤더 정보를 추가적으로 정의
     * 전자문서시스템에서 결과 발송시 반드시 포함하여 전송
     * </pre>
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Addendum {
        /**
         * 추가 헤더의 참조 설명
         */
        @JacksonXmlProperty(localName = "comment", isAttribute = true)
        @JsonProperty(required = true)
        @JacksonXmlCData
        private String comment;

        /**
         * 추가 헤더 명
         */
        @JacksonXmlProperty(localName = "name", isAttribute = true)
        @JsonProperty(required = true)
        @JacksonXmlCData
        private String name;

        @XmlValue
        @JacksonXmlText
        private String value;
    }

    /**
     * <pre>
     *     첨부 문서
     * </pre>
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Attachments {
        /**
         * 행정 정보
         */
        @JacksonXmlProperty(localName = "ADMINISTRATIVE_DB")
        private AdministrativeDB administrativeDB;

        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "ATTACHMENT")
        private List<Attachment> attachment;
    }

    /**
     * 행정 정보
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AdministrativeDB {
        /**
         * XML 파일 정보
         */
        @JacksonXmlProperty(localName = "XMLFILE")
        private XMLFile xmlfile;

        /**
         * XSL 파일 정보
         */
        @JacksonXmlProperty(localName = "XSLFILE")
        private XSLFile xslfile;
    }

    /**
     * XML 파일 정보
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class XMLFile {
        @JacksonXmlProperty(localName = "filename", isAttribute = true)
        @JsonProperty(required = true)
        @JacksonXmlCData
        private String filename;

        @JacksonXmlProperty(localName = "desc", isAttribute = true)
        @JacksonXmlCData
        private String desc;

        @XmlValue
        @JacksonXmlText
        @JsonProperty(required = true)
        private String value;
    }

    /**
     * XSL 파일 정보
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class XSLFile {
        @JacksonXmlProperty(localName = "filename", isAttribute = true)
        @JsonProperty(required = true)
        @JacksonXmlCData
        private String filename;

        @JacksonXmlProperty(localName = "desc", isAttribute = true)
        @JacksonXmlCData
        private String desc;

        @XmlValue
        @JacksonXmlText
        @JsonProperty(required = true)
        private String value;
    }

    /**
     * 첨부 문서
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Attachment {
        @JacksonXmlProperty(localName = "filename", isAttribute = true)
        @JsonProperty(required = true)
        @JacksonXmlCData
        private String filename;

        @JacksonXmlProperty(localName = "desc", isAttribute = true)
        @JacksonXmlCData
        private String desc;

        @XmlValue
        @JacksonXmlText
        @JsonProperty(required = true)
        @JacksonXmlCData
        private String value;
    }
}
