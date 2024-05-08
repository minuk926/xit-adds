package cokr.xit.adds.inf.mois.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

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
 * fileName    : PackDto
 * author      : limju
 * date        : 2024-05-02
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-05-02   limju       최초 생성
 *
 * </pre>
 */
@JacksonXmlRootElement(localName = "pack")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PackDto {
    @JacksonXmlProperty
    @JsonProperty(required = true)
    protected Header header;

    @JacksonXmlProperty
    @JsonProperty(required = true)
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
        protected Type type;

        @JacksonXmlProperty
        @JsonProperty(required = true)
        protected String date;

        @JacksonXmlProperty
        @JsonProperty(required = true)
        protected String sender;

        @JacksonXmlProperty
        @JsonProperty(required = true)
        protected String receiver;

        @JacksonXmlProperty(localName = "sender_userid")
        @JsonProperty(required = true)
        protected String senderUserid;

        @JacksonXmlProperty(localName = "receiver_userid")
        @JsonProperty(required = true)
        protected String receiverUserid;

        @JacksonXmlProperty(localName = "sender_email")
        protected String senderEmail;

        @JacksonXmlProperty(localName = "sender_orgname")
        @JsonProperty(required = true)
        protected String senderOrgname;

        @JacksonXmlProperty(localName = "sender_systemname")
        @JsonProperty(required = true)
        protected String senderSystemname;

        @JacksonXmlProperty(localName = "administrative_num")
        @JsonProperty(required = true)
        protected String administrativeNum;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Type {
        /**
         * <pre>
         * docType 속성
         * send|fail|arrive|receive|invalid|submit|return|approval
         *</pre>
         */
        @JacksonXmlProperty(localName = "doc-type", isAttribute = true)
        @JsonProperty(required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String docType;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Contents {
        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "content")
        protected List<Content> content;

        public List<Content> getContent() {
            if (content == null) {
                content = new ArrayList<Content>();
            }
            return this.content;
        }
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

        @JacksonXmlProperty(localName = "content-transfer-encoding", isAttribute = true)
        protected String contentTransferEncoding = "base64";

        @JacksonXmlProperty(localName = "filename", isAttribute = true)
        @JsonProperty(required = true)
        protected String filename;

        @JacksonXmlProperty(localName = "content-type", isAttribute = true)
        protected String contentType;

        @JacksonXmlProperty(localName = "charset", isAttribute = true)
        protected String charset;
    }
}
