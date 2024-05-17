package cokr.xit.adds.inf.mois.model;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Base64Utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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
@Getter
public class PackDto {
    /**
     * 헤더 정보 - 필수
     */
    @JacksonXmlProperty
    @JsonProperty(required = true)
    protected Header header;

    /**
     * 본문  - 필수
     */
    @JacksonXmlProperty
    @JsonProperty(required = true)
    protected Contents contents;

    /**
     * <pre>
     * 전송 파일명  - 필수
     * </pre>
     */
    @JacksonXmlProperty(localName = "filename", isAttribute = true)
    @JsonProperty(required = true)
    protected String filename;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor
    @SuperBuilder
    @Getter
    @Setter
    public static class Header {
        /**
         * <pre>
         * docType 속성  - 필수
         * send | fail |arrive | receive | invalid | submit | return |approval
         * 발송 | 실패  | 도달  | 수신     |사용자없음| 상신    | 반송   |결재
         *</pre>
         */
        @JacksonXmlProperty
        @JsonProperty(required = true)
        protected Type type;

        /**
         * <pre>
         * 날자 - 필수
         * YYYY-MM-DD HH:mm:ss
         *</pre>
         */
        @JacksonXmlProperty
        @JsonProperty(required = true)
        protected String date;

        /**
         * 송신시스템  - 필수
         */
        @JacksonXmlProperty
        @JsonProperty(required = true)
        protected String sender;

        /**
         * 수신시스템  - 필수
         */
        @JacksonXmlProperty
        @JsonProperty(required = true)
        protected String receiver;

        /**
         * 송신자 ID  - 필수
         */
        @JacksonXmlProperty(localName = "sender_userid")
        @JsonProperty(required = true)
        protected String senderUserid;

        /**
         * 수신자 ID - 필수
         */
        @JacksonXmlProperty(localName = "receiver_userid")
        @JsonProperty(required = true)
        protected String receiverUserid;

        /**
         * <pre>
         * 송신자 이메일
         * 0 ~ n
         *</pre>
         */
        @JacksonXmlProperty(localName = "sender_email")
        protected String senderEmail;

        /**
         * <pre>
         * 송신 기관명  - 필수
         * base64 encoding
         *</pre>
         */
        @JacksonXmlProperty(localName = "sender_orgname")
        @JsonProperty(required = true)
        protected String senderOrgname;

        /**
         * <pre>
         * 송신 시스템명 - 필수
         * base64 encoding
         * </pre>
         */
        @JacksonXmlProperty(localName = "sender_systemname")
        @JsonProperty(required = true)
        protected String senderSystemname;

        /**
         * 행정정보처리번호 - 필수
         */
        @JacksonXmlProperty(localName = "administrative_num")
        @JsonProperty(required = true)
        protected String administrativeNum;

        /**
         * <pre>
         * TODO: senderOrgname, senderSystemname base64 encoding 여부 확인
         * @param type
         * @param date
         * @param sender
         * @param receiver
         * @param senderUserid
         * @param receiverUserid
         * @param senderEmail
         * @param senderOrgname
         * @param senderSystemname
         * @param administrativeNum
         * </pre>
         */
        public Header(
            Type type,
            String date,
            String sender,
            String receiver,
            String senderUserid,
            String receiverUserid,
            String senderEmail,
            String senderOrgname,
            String senderSystemname,
            String administrativeNum) {
            this.type = type;
            this.date = date;
            this.sender = sender;
            this.receiver = receiver;
            this.senderUserid = senderUserid;
            this.receiverUserid = receiverUserid;
            this.senderEmail = senderEmail;
            this.senderOrgname = StringUtils.isEmpty(senderOrgname)? senderOrgname : Base64Utils.encodeToString(senderOrgname.getBytes(StandardCharsets.UTF_8));
            this.senderSystemname = StringUtils.isEmpty(senderSystemname)? senderSystemname : Base64Utils.encodeToString(senderSystemname.getBytes(StandardCharsets.UTF_8));
            this.administrativeNum = administrativeNum;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class Type {
        /**
         * <pre>
         * docType 속성 - 필수
         * send|fail|arrive|receive|invalid|submit|return|approval
         *</pre>
         */
        @JacksonXmlProperty(localName = "doc-type", isAttribute = true)
        @JsonProperty(required = true)
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
    @Builder
    @Getter
    public static class Content {
        /**
         * <pre>
         * content 내용 - 필수
         * base64 encoding
         * </pre>
         */
        @XmlValue
        @JacksonXmlText
        protected String value;

        /**
         * <pre>
         * content 내용의 의미 - 필수
         * exchange - 연계 본문 문서 파일
         * notification - 결재 처리 정보 통보 문서 파일
         * attch - 일반 첨부 파일
         * attch_xml - 행정 정보 XML 파일
         * attch_xsl - 행정 정보 XSL 파일
         * fail - 연계 모듈 에서 발송 실패 내용(text)
         * </pre>
         */
        @JacksonXmlProperty(localName = "content-role", isAttribute = true)
        @JsonProperty(required = true)
        @JacksonXmlCData
        protected String contentRole;

        /**
         * 인코딩 방식 - default base64
         */
        @JacksonXmlProperty(localName = "content-transfer-encoding", isAttribute = true)
        @JacksonXmlCData
        protected String contentTransferEncoding = "base64";

        /**
         * 파일이름 - 필수
         * base64 encoding
         */
        @JacksonXmlProperty(localName = "filename", isAttribute = true)
        @JsonProperty(required = true)
        @JacksonXmlCData
        protected String filename;

        /**
         * content-type(MIME)
         */
        @JacksonXmlProperty(localName = "content-type", isAttribute = true)
        @JacksonXmlCData
        protected String contentType;

        /**
         * charset
         */
        @JacksonXmlProperty(localName = "charset", isAttribute = true)
        @JacksonXmlCData
        protected String charset;

        public Content(
            String value,
            String contentRole,
            String contentTransferEncoding,
            String filename,
            String contentType,
            String charset) {
            this.value = StringUtils.isEmpty(value)? value : Base64Utils.encodeToString(value.getBytes(StandardCharsets.UTF_8));
            this.contentRole = contentRole;
            if(!StringUtils.isEmpty(contentTransferEncoding)){
                this.contentTransferEncoding = contentTransferEncoding;
            }
            this.filename = StringUtils.isEmpty(filename)? filename : Base64Utils.encodeToString(filename.getBytes(StandardCharsets.UTF_8));
            this.contentType = contentType;
            this.charset = charset;
        }
    }

    @Schema(name = "MoisPackRes", description = "전자결재 결과 조회 response DTO")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @SuperBuilder
    @EqualsAndHashCode(callSuper = true)
    public static class MoisPackRes extends Header {
        private String docType;

    }
}
