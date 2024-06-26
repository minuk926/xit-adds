package cokr.xit.adds.inf.mois.model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import cokr.xit.adds.core.util.ApiUtil;
import cokr.xit.foundation.data.XML;
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
        try (FileWriter w = new FileWriter("exchange.xml")) {
            XMLStreamWriter sw = factory.createXMLStreamWriter(w);
            sw.writeStartDocument("EUC-KR", "1.0");
            //sw.writeDTD("\n"+dtd);
            sw.writeDTD("\n");
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(sw, dto);

            ApiUtil.validateXmlFromFile("exchange.xml", "src/main/resources/xsd/exchange.xsd");

        }catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    @DisplayName("전자결재 response xml read 테스트")
    @Test
    public void exchangeResXmlReadTest() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("src/test/resources/test_data/notification.xml");
        byte[] bytes = fileInputStream.readAllBytes();
        String content = new String(bytes, "EUC-KR");

        JacksonXmlModule jacksonXmlModule = new JacksonXmlModule();
        jacksonXmlModule.setDefaultUseWrapper(false);
        //ObjectMapper xmlMapper = new XmlMapper(jacksonXmlModule);
        XmlMapper xmlMapper = new XmlMapper(jacksonXmlModule);
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

        ExchangeDto dto
            = xmlMapper.readValue(content, ExchangeDto.class);

        assertNotNull(dto);

        xmlMapper.writeValue(System.out, dto);

        XML xml = new XML();
        //ApiUtil.validateXmlFromXmlStr(content, "src/main/resources/xsd/pack.xsd");

        ExchangeDto dto2 = xml.parse(content, new TypeReference<ExchangeDto>() {});
        log.info("dto: {}", dto2);
        xml.write(System.out, dto2, true);

        // assertEquals("senderOrgname", new String(Base64Utils.decodeFromString(dto.getHeader().getSenderOrgname())));
        // assertEquals("senderSystemname", new String(Base64Utils.decodeFromString(dto.getHeader().getSenderSystemname())));
        // assertEquals("filename", new String(Base64Utils.decodeFromString(dto.getContents().getContent().get(0).getFilename())));
        // assertEquals("content-value", new String(Base64Utils.decodeFromString(dto.getContents().getContent().get(0).getValue())));
    }

    @DisplayName("전자결재 send xml read 테스트")
    @Test
    public void exchangeSendXmlReadTest() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("D:\\data\\exchange\\data\\sendtemp\\cskimADM405000069DOC13100000120240513710121294549\\exchange.xml");
        byte[] bytes = fileInputStream.readAllBytes();
        String content = new String(bytes, "EUC-KR");

        JacksonXmlModule jacksonXmlModule = new JacksonXmlModule();
        jacksonXmlModule.setDefaultUseWrapper(false);
        //ObjectMapper xmlMapper = new XmlMapper(jacksonXmlModule);
        XmlMapper xmlMapper = new XmlMapper(jacksonXmlModule);
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

        ExchangeDto dto
            = xmlMapper.readValue(content, ExchangeDto.class);

        assertNotNull(dto);

        xmlMapper.writeValue(System.out, dto);

        XML xml = new XML();
        //ApiUtil.validateXmlFromXmlStr(content, "src/main/resources/xsd/pack.xsd");

        ExchangeDto dto2 = xml.parse(content, new TypeReference<ExchangeDto>() {});
        log.info("dto: {}", dto2);
        xml.write(System.out, dto2, true);

        // assertEquals("senderOrgname", new String(Base64Utils.decodeFromString(dto.getHeader().getSenderOrgname())));
        // assertEquals("senderSystemname", new String(Base64Utils.decodeFromString(dto.getHeader().getSenderSystemname())));
        // assertEquals("filename", new String(Base64Utils.decodeFromString(dto.getContents().getContent().get(0).getFilename())));
        // assertEquals("content-value", new String(Base64Utils.decodeFromString(dto.getContents().getContent().get(0).getValue())));
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


        ExchangeDto.Docnum docnum = ExchangeCommon.Docnum.builder()
            .docnumcode("1310000012699")
            .value("고도화팀-2699")
            .build();

        ExchangeCommon.Line line1 = ExchangeCommon.Line.builder()
            .level("1")
            .sanction(
                ExchangeCommon.Sanction.builder()
                    .result("상신")
                    .type("기안")
                    .person(
                        ExchangeCommon.Person.builder()
                            .userid("hongkildong")
                            .name("홍길동")
                            .position("전산주사")
                            .dept("고도화팀")
                            .org("행정안전부")
                            .build())
                    .comment("보고자 의견입니다.")
                    .date("2007-01-25 14:45:34")
                    .build())
            .build();
        ExchangeCommon.Line line2 = ExchangeCommon.Line.builder()
            .level("1")
            .sanction(
                ExchangeCommon.Sanction.builder()
                    .result("상신")
                    .type("기안")
                    .person(
                        ExchangeCommon.Person.builder()
                            .userid("hongkildong1")
                            .name("홍길동1")
                            .position("전산주사1")
                            .dept("고도화팀1")
                            .org("행정안전부1")
                            .build())
                    .comment("보고자 의견입니다1.")
                    .date("2007-01-25 14:45:34")
                    .build())
            .build();


        ExchangeDto.ToAdministrativeSystem administrativeSystem = ExchangeDto.ToAdministrativeSystem.builder()
            .docnum(docnum)
            .sanctionInfo(
                ExchangeDto.SanctionInfo.builder()
                    .status("완료")
                    .lines(ExchangeDto.Lines.builder()
                            .line(List.of(line1, line2))
                            .build())
                    .build())
            .modificationFlag(
                ExchangeDto.ModificationFlag.builder()
                    .modifiable(
                        ExchangeDto.Modifiable.builder()
                            .modifyflag("no")
                            .build())
                    .build())
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
            //.toAdministrativeSystem(administrativeSystem)
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
