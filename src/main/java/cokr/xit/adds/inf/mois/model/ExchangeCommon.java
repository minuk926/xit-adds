package cokr.xit.adds.inf.mois.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
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
        private Sender sender;

        @JacksonXmlProperty(localName = "RECEIVER")
        private Receiver receiver;

        @JacksonXmlProperty(localName = "TITLE")
        private String title;

        @JacksonXmlProperty(localName = "CREATED_DATE")
        private String createdDate;

        @JacksonXmlProperty(localName = "ATTACHNUM")
        private int attachnum;

        @JacksonXmlProperty(localName = "ADMINISTRATIVE_NUM")
        private String administrativeNum;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Sender {
        @JacksonXmlProperty(localName = "SERVERID")
        private String serverid;

        @JacksonXmlProperty(localName = "USERID")
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
        private String serverid;

        @JacksonXmlProperty(localName = "USERID")
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
        @JacksonXmlProperty(localName = "status", isAttribute = true)
        private String status;

        @JacksonXmlProperty(localName = "LINES")
        private Lines lines;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Lines {
        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "LINE")
        private List<Line> line;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Line {
        @JacksonXmlProperty(localName = "LEVEL")
        private String level;

        @JacksonXmlProperty(localName = "SANCTION")
        private Sanction sanction;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Sanction {
        @JacksonXmlProperty(localName = "result", isAttribute = true)
        private String result;

        @JacksonXmlProperty(localName = "type", isAttribute = true)
        private String type;

        @JacksonXmlProperty(localName = "PERSON")
        private Person person;

        @JacksonXmlProperty(localName = "COMMENT")
        private String comment;

        @JacksonXmlProperty(localName = "DATE")
        private String date;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Person {
        @JacksonXmlProperty(localName = "USERID")
        private String userid;

        @JacksonXmlProperty(localName = "NAME")
        private String name;

        @JacksonXmlProperty(localName = "POSITION")
        private String position;

        @JacksonXmlProperty(localName = "DEPT")
        private String dept;

        @JacksonXmlProperty(localName = "ORG")
        private String org;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ModificationFlag {
        @JacksonXmlProperty(localName = "MODIFIABLE")
        private Modifiable modifiable;

        @JacksonXmlProperty(localName = "MODIFIED")
        private String modified;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Modifiable {
        @JacksonXmlProperty(localName = "modifyflag", isAttribute = true)
        private String modifyflag;
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
        private String comment;

        @JacksonXmlProperty(localName = "name", isAttribute = true)
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
        String filename;

        @JacksonXmlProperty(localName = "desc", isAttribute = true)
        String desc;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class XSLFile {
        @JacksonXmlProperty(localName = "filename", isAttribute = true)
        String filename;

        @JacksonXmlProperty(localName = "desc", isAttribute = true)
        String desc;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Attachment {
        @JacksonXmlProperty(localName = "filename", isAttribute = true)
        private String filename;

        @JacksonXmlProperty(localName = "desc", isAttribute = true)
        private String desc;

        @XmlValue
        @JacksonXmlText
        private String value;
    }
}
