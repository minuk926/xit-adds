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
import org.springframework.util.Base64Utils;

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
public class PackDtoTest {
    @DisplayName("전자결재 pack xml read 테스트")
    @Test
    public void packXmlReadTest() throws IOException {
        XML xml = new XML();
        FileInputStream fileInputStream = new FileInputStream("pack.xml");
        byte[] bytes = fileInputStream.readAllBytes();
        String content = new String(bytes);
        ApiUtil.validateXmlFromXmlStr(content, "src/main/resources/xsd/pack.xsd");

        PackDto dto = xml.parse(content, new TypeReference<PackDto>() {});
        log.info("dto: {}", dto);
        xml.write(System.out, dto, true);

        assertEquals("senderOrgname", new String(Base64Utils.decodeFromString(dto.getHeader().getSenderOrgname())));
        assertEquals("senderSystemname", new String(Base64Utils.decodeFromString(dto.getHeader().getSenderSystemname())));
        assertEquals("filename", new String(Base64Utils.decodeFromString(dto.getContents().getContent().get(0).getFilename())));
        assertEquals("content-value", new String(Base64Utils.decodeFromString(dto.getContents().getContent().get(0).getValue())));
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
            <!DOCTYPE pack SYSTEM "src/main/resources/xsd/pack.dtd">
            """;
        try (FileWriter w = new FileWriter("pack.xml")) {
            XMLStreamWriter sw = factory.createXMLStreamWriter(w);
            sw.writeStartDocument("EUC-KR", "1.0");
            //sw.writeDTD("\n"+dtd);
            sw.writeDTD("\n");
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(sw, dto);

            ApiUtil.validateXmlFromFile("pack.xml", "src/main/resources/xsd/pack.xsd");


        }catch (XMLStreamException e) {
            e.printStackTrace();
        }
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
}
