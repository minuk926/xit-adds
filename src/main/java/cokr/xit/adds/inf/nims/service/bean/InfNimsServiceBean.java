package cokr.xit.adds.inf.nims.service.bean;

import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;

import cokr.xit.adds.core.spring.annotation.TraceLogging;
import cokr.xit.adds.core.spring.exception.ApiCustomException;
import cokr.xit.adds.core.util.ApiUtil;
import cokr.xit.adds.inf.nims.model.NimsApiDto;
import cokr.xit.adds.inf.nims.model.NimsApiRequest;
import cokr.xit.adds.inf.nims.model.NimsApiResult;
import cokr.xit.adds.inf.nims.service.InfNimsService;
import cokr.xit.foundation.component.AbstractServiceBean;
import cokr.xit.foundation.data.JSON;
import cokr.xit.foundation.web.WebClient;
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
public class InfNimsServiceBean extends AbstractServiceBean implements InfNimsService {
	private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	private static WebClient webClient = new WebClient();
	private static JSON json = new JSON();

	@Value("${app.inf.nims.url}")
	private String nimsUrl;

	@Value("${app.inf.nims.api-key}")
	private String nimsApiKey;

	@Value("${app.inf.nims.api.bsshinfoStV1}")
	private String bsshInfoStV1;

	@Value("${app.inf.nims.api.productinfoKd}")
	private String productinfoKd;

	@Value("${app.inf.nims.api.seqinfo}")
	private String seqinfo;

	@Value("${app.inf.nims.api.officeinfo}")
	private String officeinfo;

	@Value("${app.inf.nims.api.placeinfoV1}")
	private String placeinfoV1;

	@Override
	@TraceLogging
	public NimsApiResult<NimsApiDto.BsshInfoSt> getBsshInfoSt(NimsApiRequest.BsshInfoRequest dto) {
		dto.setK(nimsApiKey);
		ApiUtil.validate(dto, null, validator);
		if(isEmpty(dto.getBi()) && isEmpty(dto.getHp()) && isEmpty(dto.getBn()) && isEmpty(dto.getBc())) {
			throw ApiCustomException.create("필수 파라메터 에러(bi-사업자등록번호, hp-요양기관번호, bn-업체명, bc-취급자식별번호 중 하나는 필수)");
		}
		if(!isEmpty(dto.getYmd())) ApiUtil.checkYmdError(dto.getYmd(), null);

		String result = ApiUtil.callNimsApi(nimsUrl + bsshInfoStV1, dto);
		return json.parse(result, new TypeReference<NimsApiResult<NimsApiDto.BsshInfoSt>>() {});
	}

	@Override
	@TraceLogging
	public NimsApiResult<NimsApiDto.ProductInfoKd> getProductInfoKd(NimsApiRequest.ProductInfoRequest dto) {
		dto.setK(nimsApiKey);
		ApiUtil.validate(dto, null, validator);
		if(!isEmpty(dto.getYmd())) ApiUtil.checkYmdError(dto.getYmd(), null);

		String result = ApiUtil.callNimsApi(nimsUrl + productinfoKd, dto);
		return json.parse(result, new TypeReference<NimsApiResult<NimsApiDto.ProductInfoKd>>() {});
	}

	@Override
	@TraceLogging
	public NimsApiResult<NimsApiDto.MnfSeqInfo> getMnfSeqInfo(NimsApiRequest.MnfSeqInfoRequest dto) {
		dto.setK(nimsApiKey);
		ApiUtil.validate(dto, null, validator);
		if(!isEmpty(dto.getYmd())) ApiUtil.checkYmdError(dto.getYmd(), null);

		String result = ApiUtil.callNimsApi(nimsUrl + seqinfo, dto);
		return json.parse(result, new TypeReference<NimsApiResult<NimsApiDto.MnfSeqInfo>>() {});
	}

	@Override
	@TraceLogging
	public NimsApiResult<NimsApiDto.JurisdictionGovInfo> getJurisdictionGovInfo(
		NimsApiRequest.JurisdictionGovInfoRequest dto) {
		dto.setK(nimsApiKey);
		ApiUtil.validate(dto, null, validator);
		if(isEmpty(dto.getOcd()) && isEmpty(dto.getOnm()) && isEmpty(dto.getAdr())) {
			throw ApiCustomException.create("필수 파라메터 에러(ocd-기관 코드, onm-기관명, adr-주소 중 하나는 필수)");
		}

		String result = ApiUtil.callNimsApi(nimsUrl + officeinfo, dto);
		return json.parse(result, new TypeReference<NimsApiResult<NimsApiDto.JurisdictionGovInfo>>() {});
	}

	@Override
	@TraceLogging
	public NimsApiResult<NimsApiDto.StorageInfo> getStorageInfo(NimsApiRequest.StorageInfoRequest dto) {
		dto.setK(nimsApiKey);
		ApiUtil.validate(dto, null, validator);
		if(!isEmpty(dto.getYmd())) ApiUtil.checkYmdError(dto.getYmd(), null);
		if(!isEmpty(dto.getFg()) && dto.getFg().equals("1")) {
			if(isEmpty(dto.getBc())){
				throw ApiCustomException.create("조회 범위를 특정업체(fg=1)로 조회할 경우 취급자식별번호(bc)는 필수입니다");
			}
		}

		String result = ApiUtil.callNimsApi(nimsUrl + placeinfoV1, dto);
		return json.parse(result, new TypeReference<NimsApiResult<NimsApiDto.StorageInfo>>() {});
	}

}
