package cokr.xit.adds.inf.mois.model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * description :
 *
 * packageName : cokr.xit.adds.inf.mois.model
 * fileName    : ExchangeDtoTest
 * author      : limju
 * date        : 2024-03-14
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-03-14    limju       최초 생성
 *
 * </pre>
 */
@Slf4j
@ExtendWith(SpringExtension.class)
public class ExchangeDtoTest {
    String xml = """
        <?xml version="1.0" encoding="EUC-KR"?>
        <!DOCTYPE EXCHANGE SYSTEM "exchange.dtd">
         <EXCHANGE>
           <HEADER>
             <COMMON>
               <SENDER>
                 <SERVERID>ADM131000040</SERVERID>
                 <USERID>hongkildong</USERID>
                 <EMAIL>hongkildong@sample.gcc.go.kr</EMAIL>
               </SENDER>
               <RECEIVER>
                 <SERVERID>ADM131000001</SERVERID>
                 <USERID>hongkildong</USERID>
                 <EMAIL>hongkildong@sample.gcc.go.kr</EMAIL>
               </RECEIVER>
               <TITLE><![CDATA[업무관리시스템과 행정정보시스템간 샘플문서]]></TITLE>
               <CREATED_DATE>2007-01-25 14:45:34</CREATED_DATE>
               <ATTACHNUM>2</ATTACHNUM>
               <ADMINISTRATIVE_NUM>APP20060000000004075</ADMINISTRATIVE_NUM>
             </COMMON>
             <DIRECTION>
               <TO_ADMINISTRATIVE_SYSTEM>
                 <DOCNUM docnumcode="1310000012699"><![CDATA[고도화팀-2699]]></DOCNUM>
                 <SANCTION_INFO status="완료">
                 <LINES>
                   <LINE>
                     <LEVEL>1</LEVEL>
                     <SANCTION result="상신" type="기안">
                       <PERSON>
                         <USERID>hongkildong</USERID>
                         <NAME>홍길동</NAME>
                         <POSITION>전산주사</POSITION>
                         <DEPT><![CDATA[고도화팀]]></DEPT>
                         <ORG><![CDATA[행정안전부]]></ORG>
                       </PERSON>
                       <COMMENT><![CDATA[보고자 의견입니다.]]></COMMENT>
                       <DATE>2007-01-25 14:45:34</DATE>
                     </SANCTION>
                   </LINE>
                   <LINE>
                     <LEVEL>final</LEVEL>
                     <SANCTION result="결재" type="결재">
                       <PERSON>
                         <USERID>parkchulsoo</USERID>
                         <NAME>박철수</NAME>
                         <POSITION>팀장</POSITION>
                         <DEPT><![CDATA[고도화팀]]></DEPT>
                         <ORG><![CDATA[행정안전부]]></ORG>
                       </PERSON>
                       <COMMENT><![CDATA[결재완료 의견입니다.]]></COMMENT>
                       <DATE>2007-01-25 14:45:34</DATE>
                     </SANCTION>
                   </LINE>
                 </LINES>
                 </SANCTION_INFO>
                 <MODIFICATION_FLAG>
                   <MODIFIABLE modifyflag="no"/>
                 </MODIFICATION_FLAG>
               </TO_ADMINISTRATIVE_SYSTEM>
             </DIRECTION>
             <ADDENDA>
               <ADDENDUM comment="문서관리카드ID" name="DCTDOCID">
                                <![CDATA[DCT434ACE11DD5DC11979D5D70BFFFF8002]]></ADDENDUM>
             </ADDENDA>
           </HEADER>
           <BODY filename="attach_body_CFDD119D6EC2410e86E19045E53267A8.hwp" desc="body">
              <![CDATA[업무관리시스템과 행정정보시스템간 샘플문서.hwp]]>
           </BODY>
           <ATTACHMENTS>
             <ATTACHMENT filename="attach_attach_291ddc46bf184029ffe4070328020703.hwp" desc="attach">
               <![CDATA[업무관리시스템과 행정정보시스템간 샘플문서_첨부화일_1.hwp]]>
             </ATTACHMENT>
             <ATTACHMENT filename="attach_attach_101bbc46bf184029ffe4070328010291.hwp" desc="attach">
               <![CDATA[업무관리시스템과 행정정보시스템간 샘플문서_첨부화일_2.hwp]]>
             </ATTACHMENT>
           </ATTACHMENTS>
         </EXCHANGE>
        """;

    @DisplayName("전자결재 xml read 테스트")
    @Test
    public void exchangeXmlReadTest() throws IOException {
        JacksonXmlModule jacksonXmlModule = new JacksonXmlModule();
        jacksonXmlModule.setDefaultUseWrapper(false);
        //ObjectMapper xmlMapper = new XmlMapper(jacksonXmlModule);
        XmlMapper xmlMapper = new XmlMapper(jacksonXmlModule);
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

        ExchangeDto dto
            = xmlMapper.readValue(xml, ExchangeDto.class);

        assertNotNull(dto);

        xmlMapper.writeValue(System.out, dto);
    }

    @DisplayName("전자결재 xml write 테스트")
    @Test
    public void exchangeXmlWriteTest() throws IOException {
        ExchangeDto dto = getExchangeDto();

        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(true);
        XmlMapper mapper = new XmlMapper(module);

        XMLOutputFactory factory = mapper.getFactory().getXMLOutputFactory();

        String dtd = """
            <!DOCTYPE EXCHANGE SYSTEM "exchange.dtd">
            """;
        // FIXME: 파일명 생성
        try (FileWriter w = new FileWriter("exchange_exchange_1.xml")) {
            XMLStreamWriter sw = factory.createXMLStreamWriter(w);
            sw.writeStartDocument("EUC-KR", "1.0");
            sw.writeDTD("\n"+dtd);
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            //mapper.writeValue(sw, dto);



            StringWriter swr = new StringWriter();
            mapper.writeValue(swr, dto);
            System.out.println(swr.toString());

        }catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    private static ExchangeDto getExchangeDto() {
        ExchangeDto.Common common = ExchangeDto.Common.builder()
            .sender(
                ExchangeDto.Sender.builder()
                    .serverid("ADM131000040")
                    .userid("hongkildong")
                    .email("ttt@g.co.kr")
                    .build())
            .receiver(
                ExchangeDto.Receiver.builder()
                    .serverid("ADM131000040")
                    .userid("hongkildong")
                    .email("ldlldld@k.r")
                    .build())
            .title("업무관리시스템과 행정정보시스템간 샘플문서")
            .createdDate("2007-01-24 14:45:34")
            .attachnum(2)
            .administrativeNum("APP20060000000004075")
            .build();

        ExchangeDto.Direction direction = ExchangeDto.Direction.builder()
            .toDocumentSystem(
                ExchangeDto.ToDocumentSystem.builder()
                    .notification("all")
                    .modificationFlag(
                        ExchangeDto.ModificationFlag.builder()
                            .modifiable(
                                ExchangeDto.Modifiable.builder()
                                    .modifyflag("yes")
                                    .build())
                            .build())
                    .build()
            )
            .build();

        ExchangeDto.Header header = ExchangeDto.Header.builder()
            .common(common)
            .direction(direction)
            .build();

        ExchangeCommon.Attachments.AttachmentsBuilder attachment = ExchangeDto.Attachments.builder()
            .attachment(List.of(
                    ExchangeDto.Attachment.builder()
                        .filename("attach_attach_291ddc46bf184029ffe4070328020703.hwp")
                        .desc("001")
                        .value("업무관리시스템과 행정정보시스템간 샘플문서_첨부화일_1.hwp")
                        .build(),
                    ExchangeDto.Attachment.builder()
                        .filename("attach_attach_101bbc46bf184029ffe4070328010291.hwp")
                        .desc("002")
                        .value("업무관리시스템과 행정정보시스템간 샘플문서_첨부화일_2.hwp")
                        .build()
                )
            );

        ExchangeDto dto = ExchangeDto.builder()
            .header(header)
            .body("업무관리시스템과 행정정보시스템간 샘플 기안문서 본문")
            .attachments(attachment.build())
            .build();
        return dto;
    }
}
