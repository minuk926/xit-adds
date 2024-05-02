package cokr.xit.adds.inf.mois.model;

import java.util.ArrayList;
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
    @XmlElement(required = true)
    protected Header header;

    @XmlElement(required = true)
    protected Contents contents;

    @XmlAttribute(name = "filename", required = true)
    protected String filename;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Header {
        @XmlElement(required = true)
        protected Type type;

        @XmlElement(required = true)
        protected String date;

        @XmlElement(required = true)
        protected String sender;

        @XmlElement(required = true)
        protected String receiver;

        @XmlElement(name = "sender_userid", required = true)
        protected String senderUserid;

        @XmlElement(name = "receiver_userid", required = true)
        protected String receiverUserid;

        @XmlElement(name = "sender_email")
        protected String senderEmail;

        @XmlElement(name = "sender_orgname", required = true)
        protected String senderOrgname;

        @XmlElement(name = "sender_systemname", required = true)
        protected String senderSystemname;

        @XmlElement(name = "administrative_num", required = true)
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
        @XmlAttribute(name = "doc-type", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String docType;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Contents {
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
        protected String value;

        @XmlAttribute(name = "content-role", required = true)
        protected String contentRole;

        @XmlAttribute(name = "content-transfer-encoding")
        protected String contentTransferEncoding = "base64";

        @XmlAttribute(name = "filename", required = true)
        protected String filename;

        @XmlAttribute(name = "content-type")
        protected String contentType;

        @XmlAttribute(name = "charset")
        protected String charset;
    }
}
