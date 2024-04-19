package cokr.xit.adds.biz.nims.service.bean;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;

import cokr.xit.adds.biz.nims.dao.BizNimsMapper;
import cokr.xit.adds.biz.nims.model.BizNimsAarDto;
import cokr.xit.adds.biz.nims.model.BizNimsRequest;
import cokr.xit.adds.biz.nims.service.BizNimsService;
import cokr.xit.adds.core.Constants;
import cokr.xit.adds.core.spring.exception.ApiCustomException;
import cokr.xit.adds.core.util.ApiUtil;
import cokr.xit.adds.inf.nims.model.Aar;
import cokr.xit.adds.inf.nims.model.NimsAarResult;
import cokr.xit.adds.inf.nims.model.NimsApiDto;
import cokr.xit.adds.inf.nims.model.NimsApiDto.BsshInfoSt;
import cokr.xit.adds.inf.nims.model.NimsApiDto.ProductInfoKd;
import cokr.xit.adds.inf.nims.model.NimsApiRequest;
import cokr.xit.adds.inf.nims.model.NimsApiRequest.BsshInfoRequest;
import cokr.xit.adds.inf.nims.model.NimsApiRequest.ProductInfoRequest;
import cokr.xit.adds.inf.nims.model.NimsApiResult;
import cokr.xit.adds.inf.nims.service.InfNimsService;
import cokr.xit.foundation.component.AbstractServiceBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * description : 
 *
 * packageName : cokr.xit.adds.inf.nims.service.bean
 * fileName    : InfNimsServiceBean
 * author      : limju
 * date        : 2024-04-04
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-04-04    limju       최초 생성
 *
 * </pre>
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class BizNimsServiceBean extends AbstractServiceBean implements BizNimsService {
	/**
	 * 기관명
	 */
	@Value("${app.inf.nims.onm:}")
	private String onm;

	@Value("${app.inf.nims.bssh-cd:}")
	private String bsshCd;

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	private final InfNimsService infNimsService;
	private final BizNimsMapper bizNimsMapper;

	//------------------------------------------------------------------------------------------------------
	// NIMS API CALL
	//------------------------------------------------------------------------------------------------------
	@Override
	public List<BsshInfoSt> saveBsshInfoSt(BsshInfoRequest dto) {
		NimsApiResult<BsshInfoSt> result = infNimsService.getBsshInfoSt(dto);
		List<BsshInfoSt> list = result.getResultOrThrow();

		for (BsshInfoSt d : list) {
			d.setRgtr(Constants.NIMS_API_USER_ID);
			bizNimsMapper.mergeBsshInfoSt(d);
		}
		return list;
	}

	@Override
	public List<ProductInfoKd> saveProductInfoKd(ProductInfoRequest dto) {
		NimsApiResult<ProductInfoKd> result = infNimsService.getProductInfoKd(dto);
		List<ProductInfoKd> list = result.getResultOrThrow();

		for (ProductInfoKd d : list) {
			d.setRgtr(Constants.NIMS_API_USER_ID);
			bizNimsMapper.mergeProductInfoKd(d);
		}
		return list;
	}

	@Override
	public List<NimsApiDto.MnfSeqInfo> getMnfSeqInfo(NimsApiRequest.MnfSeqInfoRequest dto) {
		NimsApiResult<NimsApiDto.MnfSeqInfo> result = infNimsService.getMnfSeqInfo(dto);
		return result.getResultOrThrow();
	}

	@Override
	public List<NimsApiDto.JurisdictionGovInfo> getJurisdictionGovInfo(NimsApiRequest.JurisdictionGovInfoRequest dto) {
		NimsApiResult<NimsApiDto.JurisdictionGovInfo> result = infNimsService.getJurisdictionGovInfo(dto);
		return result.getResultOrThrow();
	}

	@Override
	public List<NimsApiDto.StorageInfo> saveStorageInfo(NimsApiRequest.StorageInfoRequest dto) {
		NimsApiResult<NimsApiDto.StorageInfo> result = infNimsService.getStorageInfo(dto);
		List<NimsApiDto.StorageInfo> list = result.getResultOrThrow();

		for (NimsApiDto.StorageInfo d : list) {
			d.setRgtr(Constants.NIMS_API_USER_ID);
			bizNimsMapper.mergeStorgeInfo(d);
		}
		return list;
	}





	//------------------------------------------------------------------------------------------------------
	// NIMS BIZ
	//------------------------------------------------------------------------------------------------------
	@Override
	public BizNimsRequest.DsuseMgt saveDsuseMgt(BizNimsRequest.DsuseMgt dto) {
		ApiUtil.validate(dto, null, validator);
		dto.setRgtr(Constants.NIMS_API_USER_ID);

		if(bizNimsMapper.insertDsuseMgt(dto) == 1){
			int dtlCnt = 0;
			for (BizNimsRequest.DsuseMgtDtl d : dto.getDsuseMgtDtls()) {
				d.setDscdmngId(dto.getDscdmngId());
				d.setDscdmngSn(StringUtils.leftPad(dtlCnt + 1 + "", 3, "0"));
				d.setRgtr(Constants.NIMS_API_USER_ID);
				dtlCnt = dtlCnt + bizNimsMapper.insertDsuseMgtDtl(d);
			}
			if(dto.getDsuseMgtDtls().size() != dtlCnt) throw ApiCustomException.create("폐기 관리 상세 등록 실패");
		} else {
			throw ApiCustomException.create("폐기 관리 마스터 등록 실패");
		}
		return dto;
	}

	@Override
	public BizNimsAarDto.AarHeader getTgtDsuseRptData(BizNimsRequest.DsuseMgt dto) {
		ApiUtil.validate(dto, null, validator);
		dto.setRgtr(Constants.NIMS_API_USER_ID);

		BizNimsAarDto.AarHeader aarHeader = bizNimsMapper.selectTgtAarHeader(dto);
		if(aarHeader == null) throw ApiCustomException.create("NIMS 연계 데이타를 생성할 수 없습니다. 데이타를 확인해 주세요");
		List<BizNimsAarDto.AarDetail> aarDetails = bizNimsMapper.selectTgtAarDetails(dto);
		if(aarDetails.isEmpty()) throw ApiCustomException.create("NIMS 연계 데이타를 생성할 수 없습니다. 데이타를 확인해 주세요");

		// 관할행정기관코드
		setDsuseInsttCd(aarHeader);

		// 저장소
		setStorgeNo(aarHeader, aarDetails);

		// 제조 번호, 일련번호, 유효기간  정보 목록
		setMnfSeqs(aarDetails);

		aarHeader.getAarDetails().addAll(aarDetails);

		return aarHeader;
	}

	@Override
	public BizNimsAarDto.AarHeader createTgtDsuseRptData(BizNimsAarDto.AarHeader dto) {
		ApiUtil.validate(dto, null, validator);
		dto.setUid(bsshCd);
		dto.setSwId(bsshCd);
		dto.setRgtr(Constants.NIMS_API_USER_ID);

		return dto;
	}

	@Override
	public NimsAarResult createReportDsuse() {
		String xml = toXml();
		log.info(xml);
		return NimsAarResult.builder().build();
	}

	@Override
	public NimsAarResult updateReportDsuse() {
		String xml = toXml();
		log.info(xml);
		return NimsAarResult.builder().build();
	}

	@Override
	public NimsAarResult cancelReportDsuse() {
		String xml = toXml();
		log.info(xml);
		return NimsAarResult.builder().build();
	}



	private String toXml() {
		Aar dto = getAar();

		XmlMapper mapper = new XmlMapper();
		mapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
		mapper.enable(SerializationFeature.INDENT_OUTPUT);

		String xmlString = null;
		try {
			xmlString = mapper.writeValueAsString(dto);
		} catch (JsonProcessingException e) {
			throw ApiCustomException.create(e.getMessage());
		}
		xmlString = xmlString.replaceFirst("nims\">",
			"nims\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
		return xmlString.replaceFirst(" xmlns=\"\"", StringUtils.EMPTY);
	}

	private Aar getAar() {
		Aar.ReportSet reportSet = Aar.ReportSet.builder()
			.header(List.of(getHeader()))
			.build();

		return Aar.builder()
			.reportSet(reportSet)
			.build();
	}

	private Aar.Header getHeader() {
		return Aar.Header.builder()
			.hdrDe("20240326")
			.bsshCd("123456789")
			.lines(getLines())
			.atchFileCo("2")
			.atchFiles(getAtchFiles())
			.build();
	}

	private Aar.Lines getLines() {

		return Aar.Lines.builder()
			.line(getLineList())
			.build();
	}

	private Aar.AtchFiles getAtchFiles() {
		return Aar.AtchFiles.builder()
			.atchFileNm(List.of("file-1.txt","file-2.txt"))
			.build();
	}

	private List<Aar.Line> getLineList(){
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

		return List.of(line, line2);
	}


	private void setStorgeNo(BizNimsAarDto.AarHeader aarHeader, List<BizNimsAarDto.AarDetail> aarDetails) {

		if(isEmpty(aarDetails.get(0).getStorgeNo())){
			try {
				List<NimsApiDto.StorageInfo> storageInfos = saveStorageInfo(
					NimsApiRequest.StorageInfoRequest.builder()
						.fg("1")
						.pg("1")
						.bc(aarHeader.getBsshCd())
						.build()
				);
				aarDetails.forEach(d -> d.setStorgeNo(storageInfos.get(0).getStorgeNo()));

			}catch (Exception e){
				if( e instanceof ApiCustomException){
					aarDetails.forEach(d -> d.setStorgeNo("S0001"));
					return;
				}
				throw ApiCustomException.create(e.getMessage());
			}
		}
	}

	private void setDsuseInsttCd(BizNimsAarDto.AarHeader aarHeader) {

		try {
			List<NimsApiDto.JurisdictionGovInfo> list = getJurisdictionGovInfo(
				NimsApiRequest.JurisdictionGovInfoRequest.builder()
					.fg("1")
					.pg("1")
					.onm(onm)
					.build()
			);
			aarHeader.setDsuseInsttCd(list.get(0).getOfCd());

		}catch (Exception e){
			if( e instanceof ApiCustomException){
				throw ApiCustomException.create(String.format("[%s]의 관할 행정 기관 코드를 찾을수 없습니다.", onm));
			}
			throw ApiCustomException.create(e.getMessage());
		}
	}

	private void setMnfSeqs(List<BizNimsAarDto.AarDetail> aarDetails) {
		AtomicReference<String> productCd = new AtomicReference<>("");

		try {

			aarDetails.forEach(d -> {
				productCd.set(d.getPrductCd());

				List<NimsApiDto.MnfSeqInfo> list = getMnfSeqInfo(
					NimsApiRequest.MnfSeqInfoRequest.builder()
						.fg("1")
						.pg("1")
						.p(d.getPrductCd())
						.build()
				);

				// FIXME: 내림 차순 정렬
				list.sort((a, b) -> {
					if(isEmpty(a.getPrdValidDe()) && isEmpty(b.getPrdValidDe())) return 0;
					if(isEmpty(a.getPrdValidDe())) return 1;
					if(isEmpty(b.getPrdValidDe())) return -1;
					return b.getPrdValidDe().compareTo(a.getPrdValidDe());
				});

				// FIXME: default list 1st value??
				d.setMnfNo(list.get(0).getMnfNo());
				d.setMnfSeq(list.get(0).getMnfSeq());
				d.setPrdValidDe(list.get(0).getPrdValidDe());

				d.getMnfSeqInfos().addAll(list);
			});

		}catch (Exception e){
			if( e instanceof ApiCustomException){
				throw ApiCustomException.create(String.format("[%s]의 제조번호 정보를 찾을수 없습니다.", productCd.get()));
			}
			throw ApiCustomException.create(e.getMessage());
		}
	}
}
