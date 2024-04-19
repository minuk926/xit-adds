package cokr.xit.adds.inf.mois.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

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
    private Header header;

    @JacksonXmlProperty(localName =  "BODY")
    private String body;

    @JacksonXmlProperty(localName =  "ATTACHMENTS")
    private Attachments attachments;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Header {
        @JacksonXmlProperty(localName = "COMMON")
        private Common common;

        @JacksonXmlProperty(localName = "DIRECTION")
        private Direction direction;

        @JacksonXmlProperty(localName =  "ADDENDA")
        private Addenda addenda;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Direction {
        @JacksonXmlProperty(localName =  "TO_DOCUMENT_SYSTEM")
        private ToDocumentSystem toDocumentSystem;

        @JacksonXmlProperty(localName =  "TO_ADMINISTRATIVE_SYSTEM")
        private ExchangeMisDto.ToAdministrativeSystem toAdministrativeSystem;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ToDocumentSystem {
        @JacksonXmlProperty(isAttribute = true, localName = "notification")
        String notification;

        @JacksonXmlProperty(localName =  "LINES")
        private Lines lines;

        @JacksonXmlProperty(localName =  "MODIFICATION_FLAG")
        private ModificationFlag modificationFlag;
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
    }
}
