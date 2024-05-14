package cokr.xit.adds.inf.mois.service.bean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import cokr.xit.adds.core.spring.exception.ApiCustomException;
import cokr.xit.adds.core.util.DateUtils;
import cokr.xit.adds.inf.mois.model.ExchangeCommon;
import cokr.xit.adds.inf.mois.model.ExchangeDto;
import cokr.xit.adds.inf.mois.model.MoisExchangeRequest;
import cokr.xit.adds.inf.mois.model.PackDto;
import cokr.xit.adds.inf.mois.service.InfMoisService;
import cokr.xit.foundation.component.AbstractServiceBean;
import cokr.xit.foundation.data.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * description : 
 * packageName : cokr.xit.adds.inf.mois.service.bean
 * fileName    : InfMoisServiceBean
 * author      : limju
 * date        : 2024-05-08
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-05-08   limju       최초 생성
 *
 * </pre>
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class InfMoisServiceBean extends AbstractServiceBean implements InfMoisService {
    @Value("${app.inf.mois.sender.systemId}")
    private String senderSystemId;

    @Value("${app.inf.mois.sender.systemNm}")
    private String senderSystemNm;

    @Value("${app.inf.mois.sender.orgname}")
    private String senderOrgname;

    @Value("${app.inf.mois.receiver.systemId}")
    private String receiverSystemId;

    @Value("${app.inf.mois.receiver.userId}")
    private String receiverUserId;

    @Value("${app.inf.mois.dataPath.root}")
    private String dataRootPath;

    @Value("${app.inf.mois.dataPath.sendTemp}")
    private String sendTemp;

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private static JSON json = new JSON();

    /**
     * <pre>
     *     전자결재연계
     *     - exchange.dtd -> exchange.xml, pack.dtd ->
     *
     * </pre>
     */
    public void sendExchange(MoisExchangeRequest reqDto) {
        final String pathName = String.format(
            "%s%s%s%s%s",
            receiverUserId,
            senderSystemId,
            receiverSystemId,
            DateUtils.getTodayAndNowTime("YYYYMMDDHHmmss"),
            new Random().nextInt(100000));

        createHeaderFromPackDto(pathName, getPackDto(reqDto));
        createEofFile(pathName);
        createExchangeXml(getExchangeDto(reqDto, pathName), pathName, "exchange");
        //log.info("sendExchange");
    }



    private void createExchangeXml(final ExchangeDto dto, final String pathName, final String filename) {
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(true);
        XmlMapper mapper = new XmlMapper(module);

        XMLOutputFactory factory = mapper.getFactory().getXMLOutputFactory();

        String dtd = """
            <!DOCTYPE EXCHANGE SYSTEM "exchange.dtd">
            """;
        // FIXME: 파일명 생성
        final String path = dataRootPath + sendTemp + pathName;
        try (FileOutputStream w = new FileOutputStream(path + "/" + filename + ".xml")) {
            XMLStreamWriter sw = factory.createXMLStreamWriter(w, "EUC-KR");
            //XMLStreamWriter sw = factory.createXMLStreamWriter(w);
            sw.writeStartDocument("EUC-KR", "1.0");
            sw.writeDTD("\n"+dtd);
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(sw, dto);

//            ApiUtil.validateXmlFromFile(filename + ".xml", "src/main/resources/xsd/exchange.xsd");

        }catch (IOException | XMLStreamException e) {
            throw ApiCustomException.create(e.getMessage());
        }
    }

    public void packXmlWriteTest(MoisExchangeRequest reqDto) throws IOException {
        PackDto dto = getPackDto(reqDto);

        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(true);
        XmlMapper mapper = new XmlMapper(module);

        XMLOutputFactory factory = mapper.getFactory().getXMLOutputFactory();

        String dtd = """
            <!DOCTYPE pack SYSTEM "exchange.dtd">
            """;
        // FIXME: 파일명 생성
        try (FileWriter w = new FileWriter("pack.xml")) {
            XMLStreamWriter sw = factory.createXMLStreamWriter(w);
            sw.writeStartDocument("EUC-KR", "1.0");
            sw.writeDTD("\n"+dtd);
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(sw, dto);

        }catch (IOException | XMLStreamException e) {
            throw ApiCustomException.create(e.getMessage());
        }
    }

    private ExchangeDto getExchangeDto(MoisExchangeRequest reqDto, String pathName) {
        // │ ─ ┃┣┫ ┠ ┨┯┷┏┓┗┛┳⊥┌┐└┘├┤┬┼  ·
        final String bodyText =
            """
            1. 귀 기관의 무궁한 발전을 기원합니다.
            2. 사고마약류 등의 폐기 신청 민원에 대하여 마약류관리에 관한 법률 시행규칙 제23조
               규정에 따라 처리하고 결과를 통보하니 마약류 취급자는 마약류 통합관리시스템
               (www.nims.go.kr)에 폐기 보고를 즉시 하시기 바랍니다.
            3. 보고 시 일련번호 및 제조번호 등 항목 누락 및 오입력이 발생하지 않도록 전산 재고 취급에
               주의를 기울여 주시기 바랍니다.
               
               ※ 마약류 관리법 시행규칙 별표2. 행정처분기준  
            ------------------------------------------------------------------------------
            9. 나. 마약류 취급에 관한 내용을 보고하지 않은 경우(미보고)
               1차 : 업무정지 15일
               2차 : 업무정지 1개월
               3차 : 업무정지 2개월 또는 허가·지정·승인 취소
               4차 : 허가·지정·승인 취소
               
            9. 다. 1) 품명, 수량, 취급연월일과 상대방의 성명, 주민등록번호를 보고하지 않거나 변경보고하지 않은 경우
               1차 : 업무정지 7일
               2차 : 업무정지 15일
               3차 : 업무정지 1개월 또는 허가·지정·승인 취소
               4차 : 허가·지정·승인 취소
            
            9. 다. 2) 그 밖의 보고 항목을 보고하지 않거나 변경보고하지 않은 경우
               1차 : 업무정지 3일
               2차 : 업무정지 7일
               3차 : 업무정지 15일 또는 허가·지정·승인 취소
               4차 : 허가·지정·승인 취소          
            ------------------------------------------------------------------------------
                - 폐기 보고 시 관할 관청 기관 코드는 "경기도 용인시 수지보건소/4050149"로 선택
                - 마약류통합관리시스템 보고 기한: 일반 관리 대상(2024. 56. 10.)
            """;
        final String path = dataRootPath + sendTemp + pathName;
        reqDto.getFiles().forEach(mf -> {
            //try(FileInputStream fis = (FileInputStream) mf.getInputStream();
            //    FileOutputStream fos = new FileOutputStream(new File(pathName+"/"+mf.getOriginalFilename()));){
            try{
                mf.transferTo(new File(path+"/attach_"+mf.getOriginalFilename()));
                //    Files.copy(mf.getInputStream(), fos, StandardCopyOption.REPLACE_EXISTING);
            }catch (IOException e) {
                throw ApiCustomException.create(e.getMessage());
            }
        });

        // 첨부 파일
        List<ExchangeDto.Attachment> files = reqDto.getFiles().stream().map(mf ->
            {
                try {
                    String filename = new String(Objects.requireNonNull(mf.getOriginalFilename()).getBytes("EUC-KR"), "EUC-KR");
                    return ExchangeDto.Attachment.builder()
                        .filename("attach_"+filename)
                        //.desc("001")
                        .value(filename)
                        .build();
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
        ).toList();

        ExchangeDto.Common common = ExchangeDto.Common.builder()
            .sender(
                ExchangeDto.Sender.builder()
                    .serverid(senderSystemId)
                    .userid(reqDto.getUserId())
                    //.email("ttt@g.co.kr")
                    .build())
            .receiver(
                ExchangeDto.Receiver.builder()
                    .serverid(receiverSystemId)
                    .userid(receiverUserId)
                    //.email("ldlldld@k.r")
                    .build())
            .title("마약류 폐기 결과 보고")
            .createdDate(DateUtils.getTodayAndNowTime("-", false))
            .attachnum(files.size())
            //FIXME: 시스템 키 - 폐기관리ID
            .administrativeNum(reqDto.getDscdmngId())
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
                            .userid("userId")
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

        ExchangeDto.Direction direction = ExchangeDto.Direction.builder()
            .toDocumentSystem(
                ExchangeDto.ToDocumentSystem.builder()
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


        ExchangeDto dto = ExchangeDto.builder()
            .header(header)
            .body(bodyText)
            .attachments(ExchangeDto.Attachments.builder()
                .attachment(files).build())
            .build();
        return dto;
    }

    private PackDto getPackDto(MoisExchangeRequest dto) {

        PackDto.Header header = PackDto.Header.builder()
            .type(PackDto.Type.builder()
                .docType("send")
                .build())
            .date(DateUtils.getTodayAndNowTime("-", false))
            .sender(senderSystemId)
            .receiver(receiverSystemId)
            .senderUserid(dto.getUserId())
            .receiverUserid("receiverUserid")
            .senderEmail("senderEmail")
            .senderOrgname(senderOrgname)
            .senderSystemname(senderSystemNm)
            .administrativeNum(dto.getDscdmngId())
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


    private void createHeaderFromPackDto(final String pathName, final PackDto dto) {
        final String path = dataRootPath + sendTemp + pathName;
        File dirPath = new File(path);

        if(!dirPath.exists()){
            dirPath.mkdirs();
        }

        PackDto.Header header = dto.getHeader();
        StringBuilder sb = new StringBuilder();
        sb.append("type=").append(header.getType().getDocType()).append("\n");
        sb.append("date=").append(header.getDate()).append("\n");
        sb.append("sender=").append(header.getSender()).append("\n");
        sb.append("receiver=").append(header.getReceiver()).append("\n");
        sb.append("sender_userid=").append(header.getSenderUserid()).append("\n");
        sb.append("receiver_userid=").append(header.getReceiverUserid()).append("\n");
        sb.append("sender_email=").append(header.getSenderEmail()).append("\n");
        sb.append("sender_orgname=").append(header.getSenderOrgname()).append("\n");
        sb.append("sender_systemname=").append(header.getSenderSystemname()).append("\n");
        sb.append("administrative_num=").append(header.getAdministrativeNum()).append("\n");

        try(PrintWriter fw = new PrintWriter(dirPath+"/header.inf", "euc-kr");){
            fw.write(sb.toString());
            fw.flush();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private void createEofFile(final String pathName) {
        final String path = dataRootPath + sendTemp + pathName;
        File dirPath = new File(path);

        try(PrintWriter fw = new PrintWriter(dirPath+"/eof.inf", "euc-kr");){
            fw.write(StringUtils.EMPTY);
            fw.flush();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
