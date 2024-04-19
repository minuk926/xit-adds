package cokr.xit.adds.inf.mois.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * description : 온나라전자문서연계 exchange_mis.dtd 구조 정의
 *
 * packageName : cokr.xit.adds.inf.mois.model
 * fileName    : ExchangeMisDto
 * author      : limju
 * date        : 2024-03-14
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-03-14    limju       최초 생성
 *
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "EXCHANGE")
@XmlRootElement(name = "EXCHANGE", namespace = "https://www.nims.or.kr/schema/nims")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExchangeMisDto extends ExchangeCommon {
    @JacksonXmlProperty(localName = "HEADER")
    private Header header;

    @JacksonXmlProperty(localName = "BODY")
    private Body body;

    @JacksonXmlProperty(localName = "ATTACHMENTS")
    private Attachments attachments;

    @JacksonXmlProperty(localName = "ADMINISTRATIVE_INFO")
    private AdminstrativeInfo administrativeInfo;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Header {
        @JacksonXmlProperty(localName =   "COMMON")
        private Common common;

        @JacksonXmlProperty(localName =   "DIRECTION")
        private Direction direction;

        @JacksonXmlProperty(localName = "ADDENDA")
        private Addenda addenda;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Direction {
        @JacksonXmlProperty(localName = "TO_DOCUMENT_SYSTEM")
        private ToDocumentSystem toDocumentSystem;

        @JacksonXmlProperty(localName = "TO_ADMINISTRATIVE_SYSTEM")
        private ToAdministrativeSystem toAdministrativeSystem;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ToAdministrativeSystem {
        @JacksonXmlProperty(localName = "DOCNUM")
        private Docnum docnum;

        @JacksonXmlProperty(localName = "SANCTION_INFO")
        private SanctionInfo sanctionInfo;

        @JacksonXmlProperty(localName = "MODIFICATION_FLAG")
        private ModificationFlag modificationFlag;

        @JacksonXmlProperty(localName = "BODY_MODIFICATIONFLAG")
        private BodyModificationflag bodyModificationflag;

        @JacksonXmlProperty(localName = "BODY_TEXT")
        private String bodyText;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BodyModificationflag {
        @JacksonXmlProperty(localName = "BODY_MODIFIABLE")
        private BodyModifiable bodyModifiable;

        @JacksonXmlProperty(localName = "BODY_MODIFIED")
        private String bodyModified;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BodyModifiable {
        @JacksonXmlProperty(isAttribute = true, localName = "modifyflag")
        private String modifyflag;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ToDocumentSystem {
        @JacksonXmlProperty(isAttribute = true, localName = "notification")
        String notification;

        @JacksonXmlProperty(localName = "LINES")
        private Lines lines;

        @JacksonXmlProperty(localName = "MODIFICATION_FLAG")
        private ModificationFlag modificationFlag;

        @JacksonXmlProperty(localName = "BODY_MODIFICATIONFLAG")
        private BodyModificationflag bodyModificationflag;

        @JacksonXmlProperty(localName = "DOC_INFO")
        private DocInfo docInfo;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DocInfo {
        @JacksonXmlProperty(localName = "SUMMARY")
        private String summary;

        @JacksonXmlProperty(localName = "DOCGUBUN")
        private Docgubun docgubun;

        @JacksonXmlProperty(localName = "READINGBOUND")
        private Readingbound readingbound;

        @JacksonXmlProperty(localName = "READINGLIMIT")
        private Readinglimit readinglimit;

        @JacksonXmlProperty(localName = "PUBLICATION")
        private Publication publication;

        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "TASKCARDS")
        private List<Taskcard> taskcards;

        @JacksonXmlProperty(localName = "SOURCES")
        private Sources sources;

        @JacksonXmlProperty(localName = "TO_KMS")
        private ToKms toKms;

        @JacksonXmlProperty(localName = "TO_UNIKMS")
        private ToUnikms toUnikms;

        @JacksonXmlProperty(localName = "SENDORGNAME")
        private String sendorgname;

        @JacksonXmlProperty(localName = "ENFORCEMENT")
        private Enforcement enforcement;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Taskcard {
        @JacksonXmlProperty(isAttribute = true, localName = "type")
        private String type;

        @JacksonXmlProperty(localName = "CARDNAME")
        private String cardname;

        @JacksonXmlProperty(localName = "CARDID")
        private String cardid;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Docgubun {
        @JacksonXmlProperty(isAttribute = true, localName = "type")
        String type;

        @XmlValue
        @JacksonXmlText
        String value;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Readingbound {
        @JacksonXmlProperty(isAttribute = true, localName = "type")
        String type;

        @XmlValue
        @JacksonXmlText
        String value;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Readinglimit {
        @JacksonXmlProperty(isAttribute = true, localName = "type")
        String type;

        @XmlValue
        @JacksonXmlText
        String value;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Publication {
        @JacksonXmlProperty(isAttribute = true, localName = "code")
        String code;

        @JacksonXmlProperty(isAttribute = true, localName = "reason")
        String reason;

        @XmlValue
        @JacksonXmlText
        String value;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Sources {
        @JacksonXmlProperty(localName = "DIRECT")
        private Direct direct;

        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "INSTRUCTION")
        private List<Instruction> instruction;

        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "DOCUMENT")
        private List<Document> document;

        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "MEMO")
        private List<Memo> memo;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Direct {
        @JacksonXmlProperty(localName = "TITLE")
        private String title;

        @JacksonXmlProperty(localName = "INSTRUCTED")
        private Instructed instructed;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Instructed {
        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "FILE")
        private List<File> file;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class File {
        @JacksonXmlProperty(localName = "ORDER")
        private String order;

        @JacksonXmlProperty(localName = "FILENAME")
        private String filename;

        @JacksonXmlProperty(localName = "DISPLAYNAME")
        private String displayname;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Instruction{
        @JacksonXmlProperty(localName = "TITLE")
        private String title;

        @JacksonXmlProperty(localName = "INSTRUCTIONID")
        private String instructionid;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Document {
        @JacksonXmlProperty(isAttribute = true, localName = "type")
        private String type;

        @JacksonXmlProperty(localName = "TITLE")
        private String title;

        @JacksonXmlProperty(localName = "DOCID")
        private String docid;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Memo {
        @JacksonXmlProperty(localName = "TITLE")
        private String title;

        @JacksonXmlProperty(localName = "MEMOID")
        private String memoid;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ToKms {
        @JacksonXmlProperty(isAttribute = true, localName = "sendflag")
        private String sendflag;

        @JacksonXmlProperty(localName = "MAPCODE")
        private String mapcode;

        @JacksonXmlProperty(localName = "MAPNAME")
        private String mapname;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ToUnikms {
        @JacksonXmlProperty(isAttribute = true, localName = "sendflag")
        private String sendflag;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Enforcement {
        @JacksonXmlProperty(localName = "SENDNAME")
        private String sendname;

        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "RECEIPT")
        private List<Receipt> receipt;

        @JacksonXmlProperty(localName = "EXRECEIPT")
        private ExReceipt exreceipt;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Receipt {
        @JacksonXmlProperty(isAttribute = true, localName = "refer")
        private String refer;

        @JacksonXmlProperty(localName = "ORG")
        private String org;

        @JacksonXmlProperty(localName = "ORGCODE")
        private String orgcode;

        @JacksonXmlProperty(localName = "DEPT")
        private String dept;

        @JacksonXmlProperty(localName = "DEPTCODE")
        private String deptcode;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ExReceipt {
        @JacksonXmlProperty(localName = "EXTERNAL")
        private External external;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class External {
        @JacksonXmlProperty(isAttribute = true, localName = "refer")
        private String refer;

        @JacksonXmlProperty(localName = "ORG")
        private String org;

        @JacksonXmlProperty(localName = "ORGCODE")
        private String orgcode;

        @JacksonXmlProperty(localName = "DEPT")
        private String dept;

        @JacksonXmlProperty(localName = "DEPTCODE")
        private String deptcode;

        @JacksonXmlProperty(localName = "ORGTYPE")
        private Orgtype orgtype;

        @JacksonXmlProperty(localName = "SYSTEMTYPE")
        private Systemtype systemtype;

        @JacksonXmlProperty(localName = "FLOWTYPE")
        private Flowtype flowtype;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Orgtype {
        @JacksonXmlProperty(isAttribute = true, localName = "typecode")
        private String typecode;

        @XmlValue
        @JacksonXmlText
        private String value;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Systemtype {
        @JacksonXmlProperty(isAttribute = true, localName = "typecode")
        private String typecode;

        @XmlValue
        @JacksonXmlText
        private String value;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Flowtype {
        @JacksonXmlProperty(isAttribute = true, localName = "typecode")
        private String typecode;

        @XmlValue
        @JacksonXmlText
        private String value;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Body {
        @JacksonXmlProperty(isAttribute = true, localName = "filename")
        private String filename;

        @JacksonXmlProperty(isAttribute = true, localName = "desc")
        private String desc;

        @XmlValue
        @JacksonXmlText
        private String value;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AdminstrativeInfo {
        @JacksonXmlProperty(localName = "FORM_FILE")
        private FormFile formFile;

        @JacksonXmlProperty(localName = "FORM_INFO")
        private FormInfo formInfo;

        @JacksonXmlProperty(localName = "FORM_DATA")
        private FormData formData;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FormFile {
        @JacksonXmlProperty(isAttribute = true, localName = "filename")
        String filename;

        @JacksonXmlProperty(isAttribute = true, localName = "desc")
        String desc;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FormInfo {
        @JacksonXmlProperty(isAttribute = true, localName = "sys_id")
        private String sysId;

        @JacksonXmlProperty(isAttribute = true, localName = "biz_id")
        private String bizId;

        @JacksonXmlProperty(isAttribute = true, localName = "seq")
        private String seq;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FormData {
        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "DATA_FIELD")
        private List<DataField> dataField;

        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "REPETITION_DATA_FIELD")
        private List<RepetitionDataField> repetitionDataField;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DataField {
        @JacksonXmlProperty(isAttribute = true, localName = "modifyflag")
        private String modifyflag;

        @JacksonXmlProperty(localName = "FIELD_NAME")
        private String fieldName;

        @JacksonXmlProperty(localName = "FIELD_VALUE")
        private String fieldValue;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RepetitionDataField {
        @JacksonXmlProperty(isAttribute = true, localName = "modifyflag")
        String modifyFlag;

        @JacksonXmlProperty(localName = "FIELD_NAME")
        String fieldName;

        @JacksonXmlProperty(localName = "FIELD_VALUE")
        String fieldValue;

        @JacksonXmlProperty(localName = "ROW")
        String row;

        @JacksonXmlProperty(localName = "COLUMN")
        String column;
    }
}
