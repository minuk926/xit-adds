package cokr.xit.adds.inf.mois.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

import jakarta.xml.bind.annotation.XmlValue;
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
    @JacksonXmlProperty(localName = "header")
    @JsonProperty(required = true)
    protected Header header;

    @JacksonXmlProperty(localName = "contents")
    protected Contents contents;

    @JacksonXmlProperty(localName = "filename", isAttribute = true)
    @JsonProperty(required = true)
    protected String filename;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Header {
        @JacksonXmlProperty
        @JsonProperty(required = true)
        protected Server server;

        @JacksonXmlProperty
        @JsonProperty(required = true)
        protected Sender sender;

        @JacksonXmlProperty
        @JsonProperty(required = true)
        protected Receiver receiver;

        @JacksonXmlProperty
        @JsonProperty(required = true)
        protected String sendersystemname;

        @JacksonXmlProperty
        @JsonProperty(required = true)
        protected Exchangetype exchangetype;

        @JacksonXmlProperty
        @JsonProperty(required = true)
        protected String doctype;

        @JacksonXmlProperty
        @JsonProperty(required = true)
        protected String date;

        @JacksonXmlProperty
        @JsonProperty(required = true)
        protected String administrativenum;

        @JacksonXmlProperty
        @JsonProperty(required = true)
        protected String title;

        @JacksonXmlProperty
        protected Addenda addenda;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Server {
        @JacksonXmlProperty
        @JsonProperty(required = true)
        protected String sendserverid;

        @JacksonXmlProperty
        @JsonProperty(required = true)
        protected String receiveserverid;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Sender {
        @JacksonXmlProperty
        @JsonProperty(required = true)
        protected Organ organ;

        @JacksonXmlProperty
        @JsonProperty(required = true)
        protected String sendkey;

        @JacksonXmlProperty
        protected String username;

        @JacksonXmlProperty
        protected String userposition;

        @JacksonXmlProperty
        protected String email;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Organ {
        @JacksonXmlProperty
        @JsonProperty(required = true)
        protected Orgname orgname;

        @JacksonXmlProperty
        @JsonProperty(required = true)
        protected Deptname deptname;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Orgname {
        @XmlValue
        @JacksonXmlText
        protected String value;

        @JacksonXmlProperty(localName = "orgcode", isAttribute = true)
        @JsonProperty(required = true)
        protected String orgcode;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Deptname {
        @XmlValue
        @JacksonXmlText
        protected String value;

        @JacksonXmlProperty(localName = "deptcode", isAttribute = true)
        @JsonProperty(required = true)
        protected String deptcode;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Receiver {
        @JacksonXmlProperty
        @JsonProperty(required = true)
        protected Organ organ;

        @JacksonXmlProperty
        @JsonProperty(required = true)
        protected String recvkey;

        @JacksonXmlProperty
        protected String username;

        @JacksonXmlProperty
        protected String userposition;

        @JacksonXmlProperty
        protected String email;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Exchangetype {
        @JacksonXmlProperty
        @JsonProperty(required = true)
        protected String maintype;

        @JacksonXmlProperty
        protected String subtype;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Addenda {
        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "addendum")
        protected List<Addendum> addendum;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Addendum {
        @XmlValue
        @JacksonXmlText
        protected String value;

        @JacksonXmlProperty(localName = "Name", isAttribute = true)
        @JsonProperty(required = true)
        protected String name;

        @JacksonXmlProperty(localName = "comment", isAttribute = true)
        @JsonProperty(required = true)
        protected String comment;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Contents {
        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "content")
        protected List<Content> content;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Content {
        @XmlValue
        @JacksonXmlText
        protected String value;

        @JacksonXmlProperty(localName = "content-role", isAttribute = true)
        @JsonProperty(required = true)
        protected String contentRole;

        @JacksonXmlProperty(localName = "sub-role", isAttribute = true)
        @Builder.Default
        protected String subRole = "content";

        @JacksonXmlProperty(localName = "content-transfer-encoding", isAttribute = true)
        @Builder.Default
        protected String contentTransferEncoding = "base64";

        @JacksonXmlProperty(localName = "filename", isAttribute = true)
        @JsonProperty(required = true)
        protected String filename;

        @JacksonXmlProperty(localName = "content-type", isAttribute = true)
        protected String contentType;

        @JacksonXmlProperty(localName = "charset", isAttribute = true)
        protected String charset;

        @JacksonXmlProperty(localName = "attachorder", isAttribute = true)
        protected String attachorder;

        /**
         * <pre>
         * default = non
         * modifiable | modified | non
         * </pre>
         */
        @JacksonXmlProperty(localName = "modify", isAttribute = true)
        @Builder.Default
        protected String modify = "non";
    }
}
