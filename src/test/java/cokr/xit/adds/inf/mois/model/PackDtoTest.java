package cokr.xit.adds.inf.mois.model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.xml.stream.XMLOutputFactory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

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
            <type doc-type="docType"/>
            <date>2024-03-14</date>
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
        PackDto dto = xml.parse(packXml, new TypeReference<PackDto>() {});
        log.info("dto: {}", dto);
        xml.write(System.out, dto, true);
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
            <!DOCTYPE EXCHANGE SYSTEM "exchange.dtd">
            """;
        // FIXME: 파일명 생성
        try (FileOutputStream fos = new FileOutputStream("pack.xml");) {
            XML xml = new XML();
            xml.write(fos, dto, true);
            fos.flush();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static PackDto getPackDto() {

        PackDto.Header header = PackDto.Header.builder()
            .type(PackDto.Type.builder()
                .docType("docType")
                .build())
            .date("2024-03-14")
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
