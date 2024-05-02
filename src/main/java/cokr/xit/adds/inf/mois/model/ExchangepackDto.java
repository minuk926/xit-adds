package cokr.xit.adds.inf.mois.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlValue;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * description : 
 * packageName : cokr.xit.adds.inf.mois.model
 * fileName    : ExchangepackDto
 * author      : limju
 * date        : 2024-05-02
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-05-02   limju       최초 생성
 *
 * </pre>
 */
@JacksonXmlRootElement(localName = "exchangepack") //, namespace = "https://www.nims.or.kr/schema/nims")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExchangepackDto {
    @XmlElement(required = true)
    protected Header header;

    protected Contents contents;

    @XmlAttribute(name = "filename", required = true)
    protected String filename;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Header {
        @XmlElement(required = true)
        protected Server server;

        @XmlElement(required = true)
        protected Sender sender;

        @XmlElement(required = true)
        protected Receiver receiver;

        @XmlElement(required = true)
        protected String sendersystemname;

        @XmlElement(required = true)
        protected Exchangetype exchangetype;

        @XmlElement(required = true)
        protected String doctype;

        @XmlElement(required = true)
        protected String date;

        @XmlElement(required = true)
        protected String administrativenum;

        @XmlElement(required = true)
        protected String title;

        protected Addenda addenda;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Server {
        @XmlElement(required = true)
        protected String sendserverid;

        @XmlElement(required = true)
        protected String receiveserverid;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Sender {
        @XmlElement(required = true)
        protected Organ organ;

        @XmlElement(required = true)
        protected String sendkey;

        protected String username;

        protected String userposition;

        protected String email;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Organ {
        @XmlElement(required = true)
        protected Orgname orgname;

        @XmlElement(required = true)
        protected Deptname deptname;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Orgname {
        @XmlValue
        protected String value;

        @XmlAttribute(name = "orgcode", required = true)
        protected String orgcode;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Deptname {
        @XmlValue
        protected String value;

        @XmlAttribute(name = "deptcode", required = true)
        protected String deptcode;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Receiver {
        @XmlElement(required = true)
        protected Organ organ;

        @XmlElement(required = true)
        protected String recvkey;

        protected String username;

        protected String userposition;

        protected String email;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Exchangetype {
        @XmlElement(required = true)
        protected String maintype;

        protected String subtype;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Addenda {
        protected List<Addendum> addendum;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Addendum {
        @XmlValue
        protected String value;

        @XmlAttribute(name = "Name", required = true)
        protected String name;

        @XmlAttribute(name = "comment", required = true)
        protected String comment;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Contents {
        protected List<Content> content;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Content {
        @XmlValue
        protected String value;

        @XmlAttribute(name = "content-role", required = true)
        protected String contentRole;

        @XmlAttribute(name = "sub-role")
        @Builder.Default
        protected String subRole = "content";

        @XmlAttribute(name = "content-transfer-encoding")
        @Builder.Default
        protected String contentTransferEncoding = "base64";

        @XmlAttribute(name = "filename", required = true)
        protected String filename;

        @XmlAttribute(name = "content-type")
        protected String contentType;

        @XmlAttribute(name = "charset")
        protected String charset;

        @XmlAttribute(name = "attachorder")
        protected String attachorder;

        /**
         * <pre>
         * default = non
         * modifiable | modified | non
         * </pre>
         */
        @XmlAttribute(name = "modify")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @Builder.Default
        protected String modify = "non";
    }
}
