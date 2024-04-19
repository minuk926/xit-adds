package cokr.xit.adds.inf.mois.model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.glassfish.jaxb.runtime.marshaller.NamespacePrefixMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
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
public class ExchangeMisDtoTest {
    private static final String XML_NAMESPACE = "<https://kkoon9.tistory.com/>";
    private static final String PREFIX = "kkoon9";

    String xml = """
        <?xml version="1.0" encoding="EUC-KR"?>
        <!DOCTYPE EXCHANGE SYSTEM "exchange_mis.dtd">
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
                 <BODY_MODIFICATIONFLAG>
                   <BODY_MODIFIABLE modifyflag="yes"/>
                   <BODY_MODIFIED>2007-01-25 14:45:34</BODY_MODIFIED>
                 </BODY_MODIFICATIONFLAG>
                 <BODY_TEXT>
              <![CDATA[업무관리시스템과 행정정보시스템간 샘플 기안문서 본문
        		본 문서는 업무관리시스템과 행정정보시스템 간 기안문서의 본문 샘플 내용임
        		본문은 수정된 본문의 text를 추출하여 전송하는 내용임
                        끝.]]>
                 </BODY_TEXT>
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
           <ADMINISTRATIVE_INFO>
             <FORM_INFO sys_id="sys001" biz_id="biz001" seq="001" />
             <FORM_DATA>
               <DATA_FIELD modifyflag="no">
                 <FIELD_NAME><![CDATA[전표번호]]></FIELD_NAME>
                 <FIELD_VALUE><![CDATA[1-1]]></FIELD_VALUE>
               </DATA_FIELD>
               <DATA_FIELD modifyflag="no">
                 <FIELD_NAME><![CDATA[회계일자]]></FIELD_NAME>
                 <FIELD_VALUE><![CDATA[2007-01-25 14:45:34]]></FIELD_VALUE>
               </DATA_FIELD>
             </FORM_DATA>
           </ADMINISTRATIVE_INFO>
         </EXCHANGE>
        """;

    @DisplayName("전자문서 xml read 테스트")
    @Test
    public void exchangeMisXmlReadTest() throws IOException {
        JacksonXmlModule jacksonXmlModule = new JacksonXmlModule();
        jacksonXmlModule.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(jacksonXmlModule);

        xmlMapper.enable(ToXmlGenerator.Feature.WRITE_XML_1_1); // XML 1.1 지원
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

        ExchangeMisDto dto
            = xmlMapper.readValue(xml, ExchangeMisDto.class);
        log.info("dto: {}", dto);

        assertNotNull(dto);

        xmlMapper.writeValue(System.out, dto);
    }

    @DisplayName("전자문서 xml write 테스트")
    @Test
    public void exchangeMisXmlWriteTest() throws IOException {
        ExchangeMisDto dto = getMisDto();

        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper mapper = new XmlMapper(module);

        XMLOutputFactory factory = mapper.getFactory().getXMLOutputFactory();

        String dtd = """
            <!DOCTYPE EXCHANGE SYSTEM "exchange_mis.dtd">
            """;
        // FIXME: 파일명 생성
        try (FileWriter w = new FileWriter("open_exchange_exchange_mis_1.xml")) {
            XMLStreamWriter sw = factory.createXMLStreamWriter(w);
            sw.writeStartDocument("EUC-KR", "1.0");
            sw.writeDTD("\n"+dtd);

            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(sw, dto);

        }catch (XMLStreamException e) {
            e.printStackTrace();
        }


    }

    private static ExchangeMisDto getMisDto() {
        ExchangeMisDto.Common common = ExchangeMisDto.Common.builder()
            .sender(
                ExchangeMisDto.Sender.builder()
                    .serverid("ADM131000040")
                    .userid("hongkildong")
                    .email("ttt@g.co.kr")
                    .build())
            .receiver(
                ExchangeMisDto.Receiver.builder()
                    .serverid("ADM131000040")
                    .userid("hongkildong")
                    .email("ldlldld@k.r")
                    .build())
            .title("업무관리시스템과 행정정보시스템간 샘플문서")
            .createdDate("2007-01-24 14:45:34")
            .attachnum(2)
            .administrativeNum("APP20060000000004075")
            .build();

        ExchangeMisDto.Direction direction = ExchangeMisDto.Direction.builder()
            .toDocumentSystem(
                ExchangeMisDto.ToDocumentSystem.builder()
                    .notification("all")
                    .modificationFlag(
                        ExchangeMisDto.ModificationFlag.builder()
                            .modifiable(
                                ExchangeMisDto.Modifiable.builder()
                                    .modifyflag("yes")
                                    .build())
                            .build())
                    .build()
            )
            .build();

        ExchangeMisDto.Header header = ExchangeMisDto.Header.builder()
            .common(common)
            .direction(direction)
            .build();

        ExchangeMisDto dto = ExchangeMisDto.builder()
            .header(header)
            .body(
                ExchangeMisDto.Body.builder()
                    .value("업무관리시스템과 행정정보시스템간 샘플 기안문서 본문")
                    .build())
            .build();
        return dto;
    }

    @Test
    @DisplayName("unmarshal 테스트")
    public void jaxbUnmarshallerTest() throws JAXBException, IOException {

        InputStream is = new ByteArrayInputStream(xml.getBytes("EUC-KR"));

        // Given
        //FileInputStream fileInputStream = new FileInputStream("test-data/data1.xml");
        JAXBContext jaxbContext = JAXBContext.newInstance(ExchangeMisDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        // When
        ExchangeMisDto dto = (ExchangeMisDto)unmarshaller.unmarshal(is);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(dto, new File("open_exchange_exchange2.xml"));

        // ExchangeDto dto2 = (ExchangeDto) unmarshaller.unmarshal(is2);
        // ExchangeDto dto3 = (ExchangeDto) unmarshaller.unmarshal(is3);
        is.close();

        // Then
        assertNotNull(dto);
    }

    @Test
    @DisplayName("marshal 테스트")
    public void jaxbMarshalTest() throws JAXBException, IOException {
        final String XML_NAMESPACE = "<https://kkoon9.tistory.com/>";
        // Given
        JAXBContext jaxbContext = JAXBContext.newInstance(ExchangeMisDto.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "EUC-KR");
        marshaller.setProperty("org.glassfish.jaxb.namespacePrefixMapper", new NamespacePrefixMapperImpl());

        ExchangeMisDto dto = getExchangeMisDto();

        // When

        File file = new File("aaaa.xml");
        marshaller.marshal(dto, file);

        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = fileInputStream.readAllBytes();
        String content = new String(bytes);

        StringWriter sw = new StringWriter();
        marshaller.marshal(dto, sw);
        String xml = sw.toString();
        System.out.println(xml);
    }

    // XML 문자열에 DOCTYPE을 추가하는 메서드
    private static String addDocType(String xmlString) {
        StringWriter sw = new StringWriter();
        sw.append("<?xml version=\"1.0\" encoding=\"EUC-KR\"?>\n");
        sw.append("<!DOCTYPE EXCHANGE SYSTEM \"exchange_mis.dtd\">\n");
        sw.append(xmlString);
        return sw.toString();
    }

    private static void writeToFile(String content, File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(file, content);
    }

    private static class NamespacePrefixMapperImpl extends NamespacePrefixMapper {
        private Map<String, String> prefixMap = new HashMap<>();

        public NamespacePrefixMapperImpl() {
            prefixMap.put(XML_NAMESPACE, PREFIX);
        }

        @Override
        public String getPreferredPrefix(String namespaceUri, String suggestion,
            boolean requirePrefix) {
            return prefixMap.getOrDefault(namespaceUri, suggestion);
        }
    }

    private ExchangeMisDto getExchangeMisDto() {
        ExchangeMisDto.Common common = ExchangeMisDto.Common.builder()
            .sender(
                ExchangeMisDto.Sender.builder()
                    .serverid("ADM131000040")
                    .userid("hongkildong")
                    .email("ttt@g.co.kr")
                    .build())
            .receiver(
                ExchangeMisDto.Receiver.builder()
                    .serverid("ADM131000040")
                    .userid("hongkildong")
                    .email("ldlldld@k.r")
                    .build())
            .title("업무관리시스템과 행정정보시스템간 샘플문서")
            .createdDate("2007-01-24 14:45:34")
            .attachnum(2)
            .administrativeNum("APP20060000000004075")
            .build();

        ExchangeMisDto.Direction direction = ExchangeMisDto.Direction.builder()
            .toDocumentSystem(
                ExchangeMisDto.ToDocumentSystem.builder()
                    .notification("all")
                    .modificationFlag(
                        ExchangeMisDto.ModificationFlag.builder()
                            .modifiable(
                                ExchangeMisDto.Modifiable.builder()
                                    .modifyflag("yes")
                                    .build())
                            .build())
                    .build()
            )
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

        List<ExchangeCommon.Attachment> listAttch = List.of(
            ExchangeDto.Attachment.builder()
                .filename("attach_attach_291ddc46bf184029ffe4070328020703.hwp")
                .desc("001")
                .value("업무관리시스템과 행정정보시스템간 샘플문서_첨부화일_1.hwp")
                .build(),
            ExchangeDto.Attachment.builder()
                .filename("attach_attach_101bbc46bf184029ffe4070328010291.hwp")
                .desc("002")
                .value("업무관리시스템과 행정정보시스템간 샘플문서_첨부화일_2.hwp")
                .build());

        ExchangeMisDto.Header header = ExchangeMisDto.Header.builder()
            .common(common)
            .direction(direction)
            .build();

        return ExchangeMisDto.builder()
            .header(header)
            .body(
                ExchangeMisDto.Body.builder()
                    .value("업무관리시스템과 행정정보시스템간 샘플 기안문서 본문")
                    .build())
            //.attachments(listAttch)
            .attachments(attachment.build())
            .build();
    }
}
