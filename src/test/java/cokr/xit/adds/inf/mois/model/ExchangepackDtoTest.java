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
public class ExchangepackDtoTest {
    String exchangeXml = """
        <?xml version='1.1' encoding='UTF-8'?>
        <exchangepack filename="filename">
          <header>
            <server>
              <sendserverid>ADM131000040</sendserverid>
              <receiveserverid>hongkildong</receiveserverid>
            </server>
            <sender>
              <organ>
                <orgname orgcode="hongkildong">orgname-value</orgname>
                <deptname deptcode="deptcode">deptname-value</deptname>
              </organ>
              <sendkey>sendkey</sendkey>
              <username>username</username>
            </sender>
            <receiver>
              <organ>
                <orgname orgcode="hongkildong">orgname-value</orgname>
                <deptname deptcode="deptcode">deptname-value</deptname>
              </organ>
            </receiver>
            <sendersystemname>sendersystemname</sendersystemname>
            <doctype>doctype</doctype>
            <date>date</date>
            <administrativenum>administrativenum</administrativenum>
            <title>title</title>
          </header>
          <contents>
            <content content-role="contentRole" sub-role="content" content-transfer-encoding="base64" content-type="constentType" charset="utf8" modify="modified">content-value</content>
          </contents>
        </exchangepack>
        """;

    @DisplayName("전자결재 exchangepack xml read 테스트")
    @Test
    public void exchangepackXmlReadTest() throws IOException {
        // JacksonXmlModule jacksonXmlModule = new JacksonXmlModule();
        // jacksonXmlModule.setDefaultUseWrapper(false);
        // //ObjectMapper xmlMapper = new XmlMapper(jacksonXmlModule);
        // XmlMapper xmlMapper = new XmlMapper(jacksonXmlModule);
        // xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        //
        // ExchangepackDto dto
        //     = xmlMapper.readValue(exchangeXml, ExchangepackDto.class);
        //
        // assertNotNull(dto);
        //
        // xmlMapper.writeValue(System.out, dto);

        XML xml = new XML();
        ExchangepackDto dto = xml.parse(exchangeXml, new TypeReference<ExchangepackDto>() {});
        log.info("dto: {}", dto);
        xml.write(System.out, dto, true);
    }

    @DisplayName("전자결재 exchangepack xml write 테스트")
    @Test
    public void exchangepackXmlWriteTest() throws IOException {
        ExchangepackDto dto = getExchangepackDto();

        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(true);
        XmlMapper mapper = new XmlMapper(module);

        XMLOutputFactory factory = mapper.getFactory().getXMLOutputFactory();

        String dtd = """
            <!DOCTYPE EXCHANGE SYSTEM "exchange.dtd">
            """;
        // FIXME: 파일명 생성
        try (FileOutputStream fos = new FileOutputStream("exchangepack.xml");) {
            XML xml = new XML();
            xml.write(fos, dto, true);
            fos.flush();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ExchangepackDto getExchangepackDto() {
        ExchangepackDto.Server server = ExchangepackDto.Server.builder()
            .sendserverid("ADM131000040")
            .receiveserverid("hongkildong")
            .build();




        ExchangepackDto.Orgname orgname = ExchangepackDto.Orgname.builder()
            //.orgid("ADM131000040")
            .orgcode("hongkildong")
            .value("orgname-value")
            .build();
        ExchangepackDto.Deptname deptname = ExchangepackDto.Deptname.builder()
            //.orgid("ADM131000040")
            .deptcode("deptcode")
            .value("deptname-value")
            .build();

        ExchangepackDto.Organ organ = ExchangepackDto.Organ.builder()
            .orgname(orgname)
            .deptname(deptname)
            .build();


        ExchangepackDto.Sender sender = ExchangepackDto.Sender.builder()
            .organ(organ)
            .sendkey("sendkey")
            .username("username")
            .build();

        ExchangepackDto.Receiver receiver = ExchangepackDto.Receiver.builder()
            .organ(organ)
            .build();

        ExchangepackDto.Header header = ExchangepackDto.Header.builder()
            .server(server)
            .sender(sender)
            .receiver(receiver)
            .sendersystemname("sendersystemname")
            .exchangetype(null)
            .doctype("doctype")
            .date("date")
            .administrativenum("administrativenum")
            .title("title")
            //.addenda(null)
            .build();

        ExchangepackDto.Content content = ExchangepackDto.Content.builder()
            .contentRole("contentRole")
            .contentType("constentType")
            .charset("utf8")
            .modify("modified")
            .value("content-value")
            .build();

        ExchangepackDto exchangepackDto = ExchangepackDto.builder()
            .header(header)
            .contents(ExchangepackDto.Contents.builder()
                .content(List.of(content))
                .build())
            .filename("filename")
            .build();


        return exchangepackDto;
    }
}
