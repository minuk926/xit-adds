package cokr.xit.adds.inf.mois.model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Base64Utils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import cokr.xit.adds.core.spring.exception.ApiCustomException;
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
public class PackDtoTest {
    String packXml = """
        <?xml version='1.1' encoding='UTF-8'?>
        <pack filename="filename">
          <header>
            <doc-type>send</doc-type>
            <date>20241231</date>
            <sender>sender</sender>
            <receiver>receiver</receiver>
            <sender_userid>senderUserid</sender_userid>
            <receiver_userid>receiverUserid</receiver_userid>
            <sender_email>senderEmail</sender_email>
            <sender_orgname>senderOrgname</sender_orgname>
            <sender_systemname>senderSystemname</sender_systemname>
            <administrative_num>administrativeNum</administrative_num>
          </header>
          <contents>
            <content content-role="contentRole" content-type="constentType" charset="utf8">content-value</content>
          </contents>
        </pack>
        """;

    @DisplayName("전자결재 pack xml read 테스트")
    @Test
    public void packXmlReadTest() throws IOException {
        // JacksonXmlModule jacksonXmlModule = new JacksonXmlModule();
        // jacksonXmlModule.setDefaultUseWrapper(false);
        // //ObjectMapper xmlMapper = new XmlMapper(jacksonXmlModule);
        // XmlMapper xmlMapper = new XmlMapper(jacksonXmlModule);
        // xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        //
        // PackDto dto
        //     = xmlMapper.readValue(packXml, PackDto.class);
        //
        // assertNotNull(dto);
        //
        // xmlMapper.writeValue(System.out, dto);

        XML xml = new XML();
        // Consumer<XmlMapper> configurer = xmlMapper -> {
        //     // 필수 필드 체크를 활성화
        //     xmlMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, true);
        // };
        // xml.configure(configurer);
        FileInputStream fileInputStream = new FileInputStream("pack.xml");
        byte[] bytes = fileInputStream.readAllBytes();
        String content = new String(bytes);

        PackDto dto = xml.parse(content, new TypeReference<PackDto>() {});
        log.info("dto: {}", dto);
        xml.write(System.out, dto, true);

        assertEquals("senderOrgname", new String(Base64Utils.decodeFromString(dto.getHeader().getSenderOrgname())));
        assertEquals("senderSystemname", new String(Base64Utils.decodeFromString(dto.getHeader().getSenderSystemname())));
        assertEquals("filename", new String(Base64Utils.decodeFromString(dto.getContents().getContent().get(0).getFilename())));
        assertEquals("content-value", new String(Base64Utils.decodeFromString(dto.getContents().getContent().get(0).getValue())));


        // XmlMapper xmlMapper = new XmlMapper();
        // xmlMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, true);
        // PackDto dto = xmlMapper.readValue(packXml, PackDto.class);
        // System.out.println("Generated XML:");
        // xmlMapper.writeValue(System.out, dto);
    }

    @DisplayName("전자결재 pack xml write 테스트")
    @Test
    public void packXmlWriteTest() throws IOException {
        PackDto dto = getPackDto();

        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(true);
        XmlMapper mapper = new XmlMapper(module);

        XMLOutputFactory factory = mapper.getFactory().getXMLOutputFactory();

        String dtd = """
            <!DOCTYPE pack SYSTEM "pack.dtd">
            """;
        try (FileWriter w = new FileWriter("pack.xml")) {
            XMLStreamWriter sw = factory.createXMLStreamWriter(w);
            sw.writeStartDocument("EUC-KR", "1.0");
            sw.writeDTD("\n"+dtd);
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(sw, dto);

            // StringWriter swr = new StringWriter();
            // mapper.writeValue(swr, dto);
            // System.out.println(swr.toString());

            // FileInputStream fileInputStream = new FileInputStream("pack.xml");
            // byte[] bytes = fileInputStream.readAllBytes();
            // String content = new String(bytes);
            // System.out.println(content);

            // PackDto rtnDto = toObjByXml(content, PackDto.class);
            //
            // StringWriter swr = new StringWriter();
            // mapper.writeValue(swr, content);
            //System.out.println(swr.toString());

        }catch (XMLStreamException e) {
            e.printStackTrace();
        }



        // FIXME: 파일명 생성
        // try (FileOutputStream fos = new FileOutputStream("pack.xml");) {
        //     XML xml = new XML();
        //     //dto.configureXmlMapper(xml.getXmlMapper());
        //     xml.write(fos, dto, true);
        //     xml.write(System.out, dto, true);
        //     // XmlMapper xmlMapper = new XmlMapper();
        //     // dto.configureXmlMapper(xmlMapper);
        //     // //xmlMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, true);
        //     // String str = xmlMapper.writeValueAsString(dto);
        //     // System.out.println("Generated XML:");
        //     // System.out.println(str);
        //     //fos.flush();
        //
        // }catch (Exception e) {
        //     e.printStackTrace();
        // }
    }

    private static PackDto getPackDto() {

        PackDto.Header header = PackDto.Header.builder()
            .type(PackDto.Type.builder()
                 .docType("send")
                 .build())
            .date("20241231")
            .sender("sender")
            .receiver("receiver")
            .senderUserid("senderUserid")
            .receiverUserid("receiverUserid")
            .senderEmail("senderEmail")
            .senderOrgname("senderOrgname")
            .senderSystemname("senderSystemname")
            .administrativeNum("administrativeNum")
            .build();

        PackDto.Content content = PackDto.Content.builder()
            .contentRole("contentRole")
            .contentType("constentType")
            .charset("utf8")
            .filename("filename")
            .value("content-value")
            .build();

        PackDto packDto = PackDto.builder()
            .header(header)
            .contents(PackDto.Contents.builder()
                .content(List.of(content))
                .build())
            .filename("filename")
            .build();


        return packDto;
    }

    private static <T> T toObjByXml(String xml, final Class<T> cls){
        ObjectMapper OM = new ObjectMapper();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xml)));
            return new XML().parse(xml, cls);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw ApiCustomException.create(e.getMessage());
        }
    }
}
