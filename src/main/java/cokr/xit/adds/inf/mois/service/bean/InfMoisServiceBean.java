package cokr.xit.adds.inf.mois.service.bean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import cokr.xit.adds.biz.nims.service.BizNimsService;
import cokr.xit.adds.core.spring.exception.ApiCustomException;
import cokr.xit.adds.core.util.DateUtils;
import cokr.xit.adds.inf.mois.model.ExchangeCommon;
import cokr.xit.adds.inf.mois.model.ExchangeDto;
import cokr.xit.adds.inf.mois.model.MoisExchangeRequest;
import cokr.xit.adds.inf.mois.model.PackDto;
import cokr.xit.adds.inf.mois.service.InfMoisService;
import cokr.xit.adds.inf.nims.model.NimsApiDto;
import cokr.xit.adds.inf.nims.model.NimsApiRequest;
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

    /**
     * 송신 연계 임시 보관
     */
    @Value("${app.inf.mois.dataPath.sendTemp}")
    private String sendTemp;

    /**
     * 송신 연계 보관
     */
    @Value("${app.inf.mois.dataPath.send}")
    private String sendDir;

    /**
     *
     */
    @Value("${app.inf.mois.dataPath.senderr}")
    private String sendErrDir;

    /**
     * 수신 연계 임시 보관
     */
    @Value("${app.inf.mois.dataPath.receiveTemp}")
    private String receiveTemp;

    /**
     * 수신 연계 보관
     */
    @Value("${app.inf.mois.dataPath.receive}")
    private String receiveDir;

    /**
     * 수신 연계 오류 보관
     */
    @Value("${app.inf.mois.dataPath.receiveerr}")
    private String receiveErrDir;

    private final BizNimsService bizNimsService;

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private static JSON json = new JSON();

    private static final String delimiter = System.getProperty("os.name").contains("Window") ? "\\" : "/";
    private static final String bodyText =
        """
        1. 귀 기관의 무궁한 발전을 기원합니다.
        2. 사고마약류 등의 폐기 신청 민원에 대하여 마약류관리에 관한 법률 시행규칙 제23조
           규정에 따라 처리하고 결과를 통보하니 마약류 취급자는 마약류 통합관리시스템
           (www.nims.go.kr)에 폐기 보고를 즉시 하시기 바랍니다.
        3. 보고 시 일련번호 및 제조번호 등 항목 누락 및 오입력이 발생하지 않도록 전산 재고 취급에
           주의를 기울여 주시기 바랍니다.
           
           ※ 마약류 관리법 시행규칙 별표2. 행정처분기준  
           -------------------------------------------------------------------------
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
           -------------------------------------------------------------------------
             - 폐기 보고 시 관할 관청 기관 코드는 "경기도 용인시 수지보건소/4050149"로 선택
             - 마약류통합관리시스템 보고 기한: 일반 관리 대상(2024. 56. 10.)
        """;
    /**
     * <pre>
     *     전자결재연계
     *     - exchange.dtd -> exchange.xml, pack.dtd ->
     *
     * </pre>
     */
    @Override
    public void sendExchange(MoisExchangeRequest reqDto) {
        NimsApiDto.BsshInfoSt bsshInfoSt = getBsshInfo(reqDto);

        final String pathName = String.format(
            "%s%s%s%s%s",
            receiverUserId,
            senderSystemId,
            receiverSystemId,
            DateUtils.getTodayAndNowTime("YYYYMMDDHHmmss"),
            String.format("%05d", new Random().nextInt(100000)));

        createHeaderFromPackDto(pathName, getPackDto(reqDto));
        createEofFile(pathName);
        createExchangeXml(pathName, reqDto, bsshInfoSt);
        //createExchangeXml(getExchangeDto(reqDto, pathName), bsshInfoSt, pathName, "exchange");
        //log.info("sendExchange");
    }

    @Override
    public void saveResultExchange() {
        File dirPath = new File(dataRootPath + receiveTemp);
        List<File> files = listFilesUsingDirectoryStream(dataRootPath + receiveTemp);
        files.sort((a, b) -> b.getName().compareTo(a.getName()));

        List<Map<String, String>> rcvTgtFiles = new ArrayList<>();
        List<PackDto.MoisPackRes> dtoList = new ArrayList<>();
        for(File f : files) {
            if (!f.isFile())
                continue;

            String srcPath = f.getParent();
            String srcFileName = f.getName();
            log.info("srcPath : {}", srcPath);
            log.info("srcFileNam : {}", srcFileName);
            rcvTgtFiles.add(Map.of(
                "inFolder", srcPath + delimiter + srcFileName,
                "outFolder",
                dataRootPath + receiveDir + srcPath.substring(srcPath.lastIndexOf(delimiter) + 1) + delimiter,
                "delFolder", srcPath
            ));

            File file = new File(srcPath, srcFileName);
            parseExchangeResult(file, dtoList);
        }

        /////////////////////////////////////////////////////////////
        // FIXME: 수신 결과 처리
        /////////////////////////////////////////////////////////////
        //
        //
        for (PackDto.MoisPackRes dto : dtoList) {
            // 수신처리
        }

        // receivetemp -> receive file move
        try {
            for(Map<String, String> m : rcvTgtFiles) {
                fileMove(m.get("inFolder"), m.get("outFolder"));
                deleteDirectory(m.get("delFolder"));
            }
        } catch (Exception e) {
            for(Map<String, String> m : rcvTgtFiles) {
                String[] items = m.get("inFolder").split(delimiter);
                String fileName = items[items.length-1];
                String inFile = m.get("outFolder")+fileName;
                String outFile = m.get("inFolder").replace(fileName, "");

                File tmp = new File(inFile);
                if(!(tmp.exists() && tmp.isFile()))
                    continue;

                fileMove(inFile, outFile);
            }
            throw ApiCustomException.create("(전자결재 연계 수신)파일 이동 및 디렉토리 제거 중 오류 발생::"+e.getMessage());
        }

    }

    private void createExchangeXml(String pathName, MoisExchangeRequest reqDto, NimsApiDto.BsshInfoSt bsshInfoSt) {
        final String path = dataRootPath + sendTemp + pathName;

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            Document doc = docFactory.newDocumentBuilder().newDocument();

            // Root
            Element rootElement = doc.createElement("EXCHANGE");
            doc.appendChild(rootElement);

            // Header
            Element header = doc.createElement("HEADER");
            rootElement.appendChild(header);

            // Header - Common
            Element common = doc.createElement("COMMON");
            header.appendChild(common);

            // Header - Common - sender
            Element sender = doc.createElement("SENDER");
            common.appendChild(sender);
            setTransInfo(doc, sender, senderSystemId, reqDto.getUserId(), "senderEmail");

            // Header - Common - receiver
            Element receiver = doc.createElement("RECEIVER");
            common.appendChild(receiver);
            setTransInfo(doc, receiver, receiverSystemId, receiverUserId, "receiverEmail");

            // Header - Common - title
            Element title = doc.createElement("TITLE");
            common.appendChild(title);
            title.appendChild(doc.createCDATASection(String.format("사고마약류 등의 폐기 결과 알림[%s]", bsshInfoSt.getBsshNm())));

            // Header - Common - created_date
            Element create_dt = doc.createElement("CREATED_DATE");
            common.appendChild(create_dt);
            create_dt.appendChild(doc.createTextNode(DateUtils.getTodayAndNowTime("-", false)));

            // Header - Common - attachnum
            Element attachnum = doc.createElement("ATTACHNUM");	//첨부문서 수에따라 틀림 - 현재는 첨부x : 0
            common.appendChild(attachnum);
            attachnum.appendChild(doc.createTextNode(reqDto.getFiles().size()+""));

            // Header - Common - administrative_num
            Element administrative_num = doc.createElement("ADMINISTRATIVE_NUM");
            common.appendChild(administrative_num);
            administrative_num.appendChild(doc.createTextNode(reqDto.getDscdmngId()));


            // Header - direction
            Element direction = doc.createElement("DIRECTION");
            header.appendChild(direction);

            // Header - direction - to_document_system
            Element to_document_system = doc.createElement("TO_DOCUMENT_SYSTEM");
            direction.appendChild(to_document_system);

            // Header - direction - to_document_system - lines
            setLines(doc, to_document_system);

            // Header - direction - to_document_system - modification_flag
            Element modification_flag = doc.createElement("MODIFICATION_FLAG");
            to_document_system.appendChild(modification_flag);

            Element modifiable = doc.createElement("MODIFIABLE");
            modification_flag.appendChild(modifiable);
            Attr modifiable_attr = doc.createAttribute("modifyflag");
            modifiable_attr.setValue("yes");
            modifiable.setAttributeNode(modifiable_attr);

            // body
            Element body = doc.createElement("BODY");
            rootElement.appendChild(body);
            body.appendChild(doc.createCDATASection(bodyText));

            // attachments
            if(!isEmpty(reqDto.getFiles()) && !reqDto.getFiles().isEmpty()){
                Element attachments = doc.createElement("ATTACHMENTS");
                rootElement.appendChild(attachments);

                //Element administrativeDb = doc.createElement("ADMINISTRATIVE_DB");
                //attachments.appendChild(administrativeDb);

                for(MultipartFile mf : reqDto.getFiles()){
                    Element attachment = doc.createElement("ATTACHMENT");
                    attachments.appendChild(attachment);
                    Attr attr_filename = doc.createAttribute("filename");
                    attr_filename.setValue("attach_"+mf.getOriginalFilename());
                    attachment.setAttributeNode(attr_filename);
                    attachment.appendChild(doc.createCDATASection(mf.getOriginalFilename()));
                    attachments.appendChild(attachment);
                }
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "exchange.dtd");
            transformer.setOutputProperty(OutputKeys.ENCODING, "euc-kr");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            //transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");

            DOMSource source = new DOMSource(doc);
            try(FileOutputStream fio = new FileOutputStream(new File(path + delimiter + "exchange.xml"));) {
                StreamResult result = new StreamResult(fio);
                transformer.transform(source, result);
            }catch (SecurityException | IOException e) {
                throw ApiCustomException.create(e.getMessage());
            }

        } catch (ParserConfigurationException | DOMException | TransformerException ex) {
            throw ApiCustomException.create(ex.getMessage());
        }

    }

    private NimsApiDto.BsshInfoSt getBsshInfo(MoisExchangeRequest reqDto) {
        List<NimsApiDto.BsshInfoSt> list = bizNimsService.saveBsshInfoSt(
            NimsApiRequest.BsshInfoReq.builder()
                .fg("1")
                .pg("1")
                .bc(reqDto.getBsshCd())
                .build()
        );
        if (isEmpty(list) || list.size() > 1){
            throw ApiCustomException.create(String.format("데이타 오류(마약류취급자[%s] 정보가 부정확 합니다)", reqDto.getBsshCd()));
        }
        return list.get(0);
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

        return PackDto.builder()
            .header(header)
            .contents(PackDto.Contents.builder()
                .content(List.of(content))
                .build())
            .filename("filename")
            .build();
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

        try(PrintWriter fw = new PrintWriter(dirPath+ delimiter + "header.inf", "euc-kr");){
            fw.write(sb.toString());
            fw.flush();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private void createEofFile(final String pathName) {
        final String path = dataRootPath + sendTemp + pathName;
        File dirPath = new File(path);

        try(PrintWriter fw = new PrintWriter(dirPath + delimiter + "eof.inf", "euc-kr");){
            fw.write(StringUtils.EMPTY);
            fw.flush();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }


    private void setTransInfo(Document doc, Element elServer, String systemNm, String userId, String email) {
        Element serverid = doc.createElement("SERVERID");
        elServer.appendChild(serverid);
        serverid.appendChild(doc.createTextNode(systemNm));
        Element userid = doc.createElement("USERID");
        elServer.appendChild(userid);
        userid.appendChild(doc.createTextNode(userId));
        Element mail = doc.createElement("EMAIL");
        elServer.appendChild(mail);
        mail.appendChild(doc.createTextNode(email));
    }

    private static void setLines(Document doc, Element to_document_system) {
        // direction - lines
        Element lines = doc.createElement("LINES");
        to_document_system.appendChild(lines);

        createLine(doc, lines, "1", "기안", "상신", "사용자ID", "황세미", "주무관", "보건행정과", "경기도용인시");
        createLine(doc, lines, "2", "검토", "상신", "사용자ID", "서홍", "의약무관리팀장", "보건행정과", "경기도용인시");
        createLine(doc, lines, "final", "전결", "상신", "사용자ID", "김정금", "보건행정과장", "보건행정과", "경기도용인시");
    }

    private static void createLine(Document doc, Element lines, String level, String type, String result, String userId, String name, String position, String dept, String org) {
        // direction - lines - line
        Element line = doc.createElement("LINE");
        lines.appendChild(line);
        Element line_level = doc.createElement("LEVEL");
        line.appendChild(line_level);
        line_level.appendChild(doc.createTextNode(level));

        // direction - lines - line - sanction
        Element sanction = doc.createElement("SANCTION");
        line.appendChild(sanction);
        Attr line_type = doc.createAttribute("type");
        line_type.setValue(type);
        Attr line_result = doc.createAttribute("result");
        line_result.setValue(result);
        sanction.setAttributeNode(line_type);
        sanction.setAttributeNode(line_result);

        // direction - lines - line - sanction - person
        Element person = doc.createElement("PERSON");
        sanction.appendChild(person);

        Element person_userid = doc.createElement("USERID");
        person.appendChild(person_userid);
        person_userid.appendChild(doc.createTextNode(userId));
        Element person_name = doc.createElement("NAME");
        person.appendChild(person_name);
        person_name.appendChild(doc.createTextNode(name));
        Element person_position = doc.createElement("POSITION");
        person.appendChild(person_position);
        person_position.appendChild(doc.createTextNode(position));
        Element person_dept = doc.createElement("DEPT");
        person.appendChild(person_dept);
        person_dept.appendChild(doc.createTextNode(dept));
        Element person_org = doc.createElement("ORG");
        person.appendChild(person_org);
        person_org.appendChild(doc.createTextNode(org));
    }

    private void parseExchangeResult(File file, List<PackDto.MoisPackRes> dtoList) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            builder.setEntityResolver((String publicId, String systemId)
                    -> {
                    if (systemId.contains("pack.dtd")) { //pack.dtd 제외
                        return new InputSource(new StringReader(""));
                    } else {
                        return null;
                    }
                }
            );

            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            Element root = doc.getDocumentElement();
            log.info(root.getTagName());
            NodeList list = root.getElementsByTagName("type");
            log.info("Node List Length:" + list.getLength());

            PackDto.MoisPackRes moisPackRes = new PackDto.MoisPackRes();
            for (int i = 0; i < list.getLength(); i++) {
                Element element = (Element)list.item(i);
                moisPackRes.setDocType(element.getAttribute("doc-type"));
                log.info("doc_type:" + moisPackRes.getDocType());
            }

            NodeList CD_List = doc.getElementsByTagName("header");
            for (int CD_idx = 0; CD_idx < CD_List.getLength(); CD_idx++) {
                Node CD_Node = CD_List.item(CD_idx);

                if (CD_Node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element)CD_Node;

                    moisPackRes.setSender(getTagValue("sender", eElement));
                    moisPackRes.setReceiver(getTagValue("receiver", eElement));
                    moisPackRes.setAdministrativeNum(getTagValue("administrative_num", eElement));
                    moisPackRes.setSenderUserid(getTagValue("sender_userid", eElement));
                    moisPackRes.setReceiverUserid(getTagValue("receiver_userid", eElement));
                    moisPackRes.setDate(getTagValue("date", eElement));
                    moisPackRes.setSenderEmail(getTagValue("sender_email", eElement));
                }
            }
            dtoList.add(moisPackRes);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw ApiCustomException.create(e.getMessage());
        }
    }

    private String getTagValue(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);

        return nValue.getNodeValue();
    }

    private void fileMove(String inFileName, String outFilePath) {
        Path file = Paths.get(inFileName);
        Path movePath = Paths.get(outFilePath);

        if(!Files.exists(movePath) && !movePath.toFile().mkdirs()){
            throw ApiCustomException.create("온나라 전자 결재 수신 보관함 폴더 생성 실패: " + outFilePath);
        };
        // Check if source file exists
        if (!Files.exists(file)) {
            throw ApiCustomException.create("온나라 전자 결재 수신 파일 미존재: " + inFileName);
        }

        try {
            // Use REPLACE_EXISTING option to overwrite existing files
            Files.move(file, movePath.resolve(file.getFileName()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw ApiCustomException.create(e.getMessage());
        }
    }

    private boolean deleteDirectory(String path) {
        File fileDirPath = new File(path);

        File[] files = fileDirPath.listFiles();      // 경로 내의 파일 리스트
        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                deleteDirectory(path);
            } else {
                file.delete();
            }
        }
        return fileDirPath.delete();
    }


    public List<File> listFilesUsingDirectoryStream(String dir) {
        List<File> fileList = new ArrayList<>();
        try {
            Files.walkFileTree(Paths.get(dir), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (file.toString().toLowerCase().endsWith(".xml")) {
                        fileList.add(file.toFile());
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw ApiCustomException.create(e.getMessage());
        }
        return fileList;
    }

    private void createExchangeXml2(final ExchangeDto dto, final String pathName, final String filename) {
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


    private ExchangeDto getExchangeDto(MoisExchangeRequest reqDto, NimsApiDto.BsshInfoSt bsshInfoSt, String pathName) {
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
                    //String filename = new String(Objects.requireNonNull(mf.getOriginalFilename()).getBytes("EUC-KR"), "EUC-KR");
                    String filename = Objects.requireNonNull(mf.getOriginalFilename());
                    return ExchangeDto.Attachment.builder()
                        .filename("attach_"+filename)
                        //.desc("001")
                        .value(filename)
                        .build();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        ).toList();



        ExchangeDto.Common common = ExchangeDto.Common.builder()
            .sender(
                ExchangeDto.Sender.builder()
                    .serverid(senderSystemId)
                    .userid(reqDto.getUserId())
                    .email("Gangcheol94@korea.kr")
                    .build())
            .receiver(
                ExchangeDto.Receiver.builder()
                    .serverid(receiverSystemId)
                    .userid(receiverUserId)
                    //.email("ldlldld@k.r")
                    .build())
            .title(String.format("사고마약류 등의 폐기 결과 알림[%s]", bsshInfoSt.getBsshNm()))
            .createdDate(DateUtils.getTodayAndNowTime("-", false))
            .attachnum(files.size())
            //FIXME: 시스템 키 - 폐기관리ID
            .administrativeNum(reqDto.getDscdmngId())
            .build();


        // 결재선
        ExchangeCommon.Line line1 = ExchangeCommon.Line.builder()
            .level("1")
            .sanction(
                ExchangeCommon.Sanction.builder()
                    .type("기안")
                    .result("상신")
                    .person(
                        ExchangeCommon.Person.builder()
                            .userid("사용자ID")
                            .name("서홍")
                            .position("의약무관리팀장")
                            .dept("의약무관리팀")
                            .org("보건행정과")
                            .build())
                    .build())
            .build();
        ExchangeCommon.Line line2 = ExchangeCommon.Line.builder()
            .level("final")
            .sanction(
                ExchangeCommon.Sanction.builder()
                    .type("전결")
                    .result("상신")
                    .person(
                        ExchangeCommon.Person.builder()
                            .userid("사용자ID")
                            .name("김정금")
                            .position("보건행정과장")
                            .dept("보건행정과")
                            .org("보건행정과")
                            .build())
                    .build())
            .build();

        ExchangeCommon.Lines lines = ExchangeCommon.Lines.builder()
            .line(List.of(line1, line2))
            .build();

        ExchangeDto.ToDocumentSystem toDocumentSystem = new ExchangeDto.ToDocumentSystem();
        if(!isEmpty(lines)){
            toDocumentSystem.setLines(lines);
        }
        toDocumentSystem.setModificationFlag(
            ExchangeDto.ModificationFlag.builder()
                .modifiable(
                    ExchangeDto.Modifiable.builder()
                        .modifyflag("yes")
                        .build())
                .build());

        ExchangeDto.Direction direction = ExchangeDto.Direction.builder()
            .toDocumentSystem(toDocumentSystem)
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




    public void packXmlWriteTest(MoisExchangeRequest reqDto) {
        PackDto dto = getPackDto(reqDto);

        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(true);
        XmlMapper mapper = new XmlMapper(module);

        XMLOutputFactory factory = mapper.getFactory().getXMLOutputFactory();

        String dtd = """
            <!DOCTYPE pack SYSTEM "pack.dtd">
            """;
        // FIXME: 파일명 생성
        try (FileOutputStream w = new FileOutputStream("pack.xml")) {
            XMLStreamWriter sw = factory.createXMLStreamWriter(w, "EUC-KR");
            //XMLStreamWriter sw = factory.createXMLStreamWriter(w);
            sw.writeStartDocument("EUC-KR", "1.0");
            sw.writeDTD("\n"+dtd);
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(sw, dto);

        }catch (IOException | XMLStreamException e) {
            throw ApiCustomException.create(e.getMessage());
        }
    }
}
