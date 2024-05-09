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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Common {
        @JacksonXmlProperty(localName = "SENDER")
        @JsonProperty(required = true)
        private Sender sender;

        @JacksonXmlProperty(localName = "RECEIVER")
        @JsonProperty(required = true)
        private Receiver receiver;

        @JacksonXmlProperty(localName = "TITLE")
        @JsonProperty(required = true)
        private String title;

        @JacksonXmlProperty(localName = "CREATED_DATE")
        @JsonProperty(required = true)
        private String createdDate;

        @JacksonXmlProperty(localName = "ATTACHNUM")
        @JsonProperty(required = true)
        private int attachnum;

        @JacksonXmlProperty(localName = "ADMINISTRATIVE_NUM")
        @JsonProperty(required = true)
        private String administrativeNum;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Sender {
        @JacksonXmlProperty(localName = "SERVERID")
        @JsonProperty(required = true)
        private String serverid;

        @JacksonXmlProperty(localName = "USERID")
        @JsonProperty(required = true)
        private String userid;

        @JacksonXmlProperty(localName = "EMAIL")
        private String email;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Receiver {
        @JacksonXmlProperty(localName = "SERVERID")
        @JsonProperty(required = true)
        private String serverid;

        @JacksonXmlProperty(localName = "USERID")
        @JsonProperty(required = true)
        private String userid;

        @JacksonXmlProperty(localName = "EMAIL")
        private String email;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Docnum {
        @JacksonXmlProperty(localName = "docnumcode", isAttribute = true)
        @JsonProperty(required = true)
        @JacksonXmlCData
        private String docnumcode;

        @XmlValue
        @JacksonXmlText
        private String value;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SanctionInfo {
        /**
         * 진행| 완료
         */
        @JacksonXmlProperty(localName = "status", isAttribute = true)
        @JsonProperty(required = true)
        private String status;

        @JacksonXmlProperty(localName = "LINES")
        @JsonProperty(required = true)
        private Lines lines;
    }

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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Line {
        @JacksonXmlProperty(localName = "LEVEL")
        @JsonProperty(required = true)
        private String level;

        @JacksonXmlProperty(localName = "SANCTION")
        @JsonProperty(required = true)
        private Sanction sanction;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Sanction {
        /**
         * <pre>
         *     상신 | 기안취소 | 승인 | 반려 | 미결
         * </pre>
         */
        @JacksonXmlProperty(localName = "result", isAttribute = true)
        @JsonProperty(required = true)
        private String result;

        /**
         * <pre>
         *     기안 | 검토 | 협조 | 전결 | 대결 | 결재
         * </pre>
         */
        @JacksonXmlProperty(localName = "type", isAttribute = true)
        @JsonProperty(required = true)
        private String type;

        @JacksonXmlProperty(localName = "PERSON")
        @JsonProperty(required = true)
        private Person person;

        @JacksonXmlProperty(localName = "COMMENT")
        private String comment;

        @JacksonXmlProperty(localName = "DATE")
        @JsonProperty(required = true)
        private String date;
    }

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

        @JacksonXmlProperty(localName = "POSITION")
        @JsonProperty(required = true)
        private String position;

        @JacksonXmlProperty(localName = "DEPT")
        @JsonProperty(required = true)
        private String dept;

        @JacksonXmlProperty(localName = "ORG")
        @JsonProperty(required = true)
        private String org;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ModificationFlag {
        @JacksonXmlProperty(localName = "MODIFIABLE")
        @JsonProperty(required = true)
        private Modifiable modifiable;

        @JacksonXmlProperty(localName = "MODIFIED")
        private String modified;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Modifiable {
        /**
         * yes | no
         */
        @JacksonXmlProperty(localName = "modifyflag", isAttribute = true)
        @JsonProperty(required = true)
        @Builder.Default
        private String modifyflag = "no";
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Addenda {
        @JacksonXmlProperty(localName = "ADDENDUM")
        private Addendum addendum;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Addendum {
        @JacksonXmlProperty(localName = "comment", isAttribute = true)
        @JsonProperty(required = true)
        @JacksonXmlCData
        private String comment;

        @JacksonXmlProperty(localName = "name", isAttribute = true)
        @JsonProperty(required = true)
        @JacksonXmlCData
        private String name;

        @XmlValue
        @JacksonXmlText
        private String value;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Attachments {
        @JacksonXmlProperty(localName = "ADMINISTRATIVE_DB")
        private AdministrativeDB administrativeDB;

        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "ATTACHMENT")
        @JsonProperty(required = true)
        private List<Attachment> attachment;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AdministrativeDB {
        @JacksonXmlProperty(localName = "XMLFILE")
        private XMLFile xmlfile;

        @JacksonXmlProperty(localName = "XSLFILE")
        private XSLFile xslfile;
    }

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
        @JsonProperty(required = true)
        @JacksonXmlCData
        private String desc;

        @XmlValue
        @JacksonXmlText
        @JsonProperty(required = true)
        private String value;
    }

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
        @JsonProperty(required = true)
        @JacksonXmlCData
        private String desc;

        @XmlValue
        @JacksonXmlText
        @JsonProperty(required = true)
        private String value;
    }

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
        @JsonProperty(required = true)
        @JacksonXmlCData
        private String desc;

        @XmlValue
        @JacksonXmlText
        @JsonProperty(required = true)
        private String value;
    }
}
