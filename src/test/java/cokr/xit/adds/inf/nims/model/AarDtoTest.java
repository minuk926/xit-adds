package cokr.xit.adds.inf.nims.model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * description :
 *
 * packageName : cokr.xit.adds.inf.nims.model
 * fileName    : AarDtoTest
 * author      : limju
 * date        : 2024-03-26
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-03-26    limju       최초 생성
 *
 * </pre>
 */
@Slf4j
@ExtendWith(SpringExtension.class)
public class AarDtoTest {

    @DisplayName("마약류폐기연계 xml read 테스트")
    @Test
    public void aarXmlReadTest() throws IOException {
        String fileName = "/src/test/resources/test_data/123456789AAR20240326120248_0001.XML";

        JacksonXmlModule jacksonXmlModule = new JacksonXmlModule();
        jacksonXmlModule.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(jacksonXmlModule);
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

        Path cur = Paths.get("");	//현재 디렉토리 정보 '상대 경로' 형태로 담긴 인스턴스 생성
        String cdir;

        if(cur.isAbsolute())	//절대 경로 일 경우.
            cdir=cur.toString();
        else
            cdir = cur.toAbsolutePath().toString();

        Path path = Paths.get(cdir, fileName);
        Aar dto
            = xmlMapper.readValue(Files.readString(path), Aar.class);

        assertNotNull(dto);

        xmlMapper.writeValue(System.out, dto);
    }

    @DisplayName("마약류폐기연계 xml write 테스트")
    @Test
    public void aarXmlWriteTest() throws IOException {
        Aar dto = getAar();

        XmlMapper mapper = new XmlMapper();
        mapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        String xmlString = mapper.writeValueAsString(dto);
        xmlString = xmlString.replaceFirst("nims\">", "nims\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
        xmlString = xmlString.replaceFirst(" xmlns=\"\"", StringUtils.EMPTY);
        System.out.println(xmlString);

        // FIXME: 파일명 생성
        // filename: BSSH_CD + RPT_SE_CD + YYYYMMDDHHMMSS + _ + 0001.XML
        try (FileWriter w = new FileWriter("aar_001.xml")) {
            w.write(xmlString);
            w.flush();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private static Aar getAar() {
        Aar.Line line = Aar.Line.builder()
            .usrRptIdNo("123456789")
            .usrRptLnIdNo("123456789")
            .storgeNo("123456789")
            .mvmnTyCd("123456789")
            .prductCd("123456789")
            .build();
        Aar.Line line2 = Aar.Line.builder()
            .usrRptIdNo("123456789-1")
            .usrRptLnIdNo("123456789-1")
            .storgeNo("123456789-1")
            .mvmnTyCd("123456789-1")
            .prductCd("123456789-1")
            .build();

        Aar.Lines lines = Aar.Lines.builder()
            .line(List.of(line, line2))
            .build();

        Aar.AtchFiles atchFiles = Aar.AtchFiles.builder()
            .atchFileNm(List.of("file-1.txt","file-2.txt"))
            .build();

        Aar.Header header = Aar.Header.builder()
            .hdrDe("20240326")
            .bsshCd("123456789")
            .lines(lines)
            .atchFileCo("2")
            .atchFiles(atchFiles)
            .build();

        Aar.ReportSet reportSet = Aar.ReportSet.builder()
            .header(List.of(header))
            .build();

        Aar dto = Aar.builder()
            .reportSet(reportSet)
            .build();
        return dto;
    }
}
