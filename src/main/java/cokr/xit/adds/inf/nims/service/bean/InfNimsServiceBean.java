package cokr.xit.adds.inf.nims.service.bean;

import java.util.List;

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
	private static JSON json = new JSON();

	@Value("${app.inf.nims.url}")
	private String nimsUrl;

	@Value("${app.inf.nims.api-key}")
	private String nimsApiKey;

	@Value("${app.inf.nims.api.bsshinfoStV1}")
	private String bsshInfoStV1;

	@Value("${app.inf.nims.api.productinfoKd}")
	private String productinfoKd;

	@Value("${app.inf.nims.api.reportinfo}")
	private String reportinfo;

	@Value("${app.inf.nims.api.seqinfo}")
	private String seqinfo;

	@Value("${app.inf.nims.api.officeinfo}")
	private String officeinfo;

	@Value("${app.inf.nims.api.placeinfoV1}")
	private String placeinfoV1;

	@Override
	@TraceLogging
	public NimsApiResult.Response<NimsApiDto.BsshInfoSt> getBsshInfoSt(NimsApiRequest.BsshInfoRequest dto) {
		dto.setK(nimsApiKey);
		ApiUtil.validate(dto, null, validator);
		if(isEmpty(dto.getBi()) && isEmpty(dto.getHp()) && isEmpty(dto.getBn()) && isEmpty(dto.getBc())) {
			throw ApiCustomException.create("필수 파라메터 에러(bi-사업자등록번호, hp-요양기관번호, bn-업체명, bc-취급자식별번호 중 하나는 필수)");
		}
		if(!isEmpty(dto.getYmd())) ApiUtil.checkYmdError(dto.getYmd(), null);

		String rslt = ApiUtil.callNimsApi(nimsUrl + bsshInfoStV1, dto);
		NimsApiResult<NimsApiDto.BsshInfoSt> result = json.parse(rslt, new TypeReference<NimsApiResult<NimsApiDto.BsshInfoSt>>() {});
		return result.getResponse();
	}

	@Override
	@TraceLogging
	public NimsApiResult.Response<NimsApiDto.ProductInfoKd> getProductInfoKd(NimsApiRequest.ProductInfoRequest dto) {
		dto.setK(nimsApiKey);
		ApiUtil.validate(dto, null, validator);
		if(!isEmpty(dto.getYmd())) ApiUtil.checkYmdError(dto.getYmd(), null);

		String rslt = ApiUtil.callNimsApi(nimsUrl + productinfoKd, dto);
		NimsApiResult<NimsApiDto.ProductInfoKd> result = json.parse(rslt, new TypeReference<NimsApiResult<NimsApiDto.ProductInfoKd>>() {});
		return result.getResponse();
	}

	@Override
	@TraceLogging
	public NimsApiResult.Response<NimsApiDto.MnfSeqInfo> getMnfSeqInfo(NimsApiRequest.MnfSeqInfoRequest dto) {
		dto.setK(nimsApiKey);
		ApiUtil.validate(dto, null, validator);
		if(!isEmpty(dto.getYmd())) ApiUtil.checkYmdError(dto.getYmd(), null);

		String rslt = ApiUtil.callNimsApi(nimsUrl + seqinfo, dto);
		NimsApiResult<NimsApiDto.MnfSeqInfo> result = json.parse(rslt, new TypeReference<NimsApiResult<NimsApiDto.MnfSeqInfo>>() {});
		return result.getResponse();
	}

	@Override
	@TraceLogging
	public NimsApiResult.Response<NimsApiDto.JurisdictionGovInfo> getJurisdictionGovInfo(
		NimsApiRequest.JurisdictionGovInfoRequest dto) {
		dto.setK(nimsApiKey);
		ApiUtil.validate(dto, null, validator);
		if(isEmpty(dto.getOcd()) && isEmpty(dto.getOnm()) && isEmpty(dto.getAdr())) {
			throw ApiCustomException.create("필수 파라메터 에러(ocd-기관 코드, onm-기관명, adr-주소 중 하나는 필수)");
		}

		String rslt = ApiUtil.callNimsApi(nimsUrl + officeinfo, dto);
		NimsApiResult<NimsApiDto.JurisdictionGovInfo> result = json.parse(rslt, new TypeReference<NimsApiResult<NimsApiDto.JurisdictionGovInfo>>() {});
		return result.getResponse();
	}

	@Override
	@TraceLogging
	public NimsApiResult.Response<NimsApiDto.StorageInfo> getStorageInfo(NimsApiRequest.StorageInfoRequest dto) {
		dto.setK(nimsApiKey);
		ApiUtil.validate(dto, null, validator);
		if(!isEmpty(dto.getYmd())) ApiUtil.checkYmdError(dto.getYmd(), null);
		if(!isEmpty(dto.getFg()) && dto.getFg().equals("1")) {
			if(isEmpty(dto.getBc())){
				throw ApiCustomException.create("조회 범위를 특정업체(fg=1)로 조회할 경우 취급자식별번호(bc)는 필수입니다");
			}
		}

		String rslt = ApiUtil.callNimsApi(nimsUrl + placeinfoV1, dto);
		NimsApiResult<NimsApiDto.StorageInfo> result = json.parse(rslt, new TypeReference<NimsApiResult<NimsApiDto.StorageInfo>>() {});
		return result.getResponse();
	}

	@Override
	public NimsApiResult.Response<NimsApiDto.DsuseRpt> getDsuseRptInfo(NimsApiRequest.DsuseRptInfoRequest dto) {
		dto.setK(nimsApiKey);
		ApiUtil.validate(dto, null, validator);
		ApiUtil.checkYmdError(dto.getSdt(), "sdt");
		ApiUtil.checkYmdError(dto.getEdt(), "edt");

		//String rslt = ApiUtil.callNimsApi(nimsUrl + reportinfo, dto);
		//NimsApiResult<NimsApiDto.DsuseRpt> result = json.parse(rslt, new TypeReference<NimsApiResult<NimsApiDto.DsuseRpt>>() {});
		//return result.getResponse();

		return getDsuseRptResponse();
	}

	private NimsApiResult.Response<NimsApiDto.DsuseRpt> getDsuseRptResponse() {
		NimsApiDto.DsuseRptDtl dtl1 = NimsApiDto.DsuseRptDtl.builder()
			.usrRptIdNo("usrRptIdNo1111")
			.usrRptLnIdNo("dsuseRptDtlIdNo11")
			.prductCd("상품코드")
			.prductNm("제품명")
			.minDistbQy(1)
			.pceQy(0)
			.mnfNo("제조번호")
			.prdValidDe("20240401")
			.mnfSeq("-")
			.storgeNo("저장소번호")
			.storgeNm("저장소명")
			.mvmnTyCd("1102")
			.dsuseQy(30)
			.build();
		NimsApiDto.DsuseRptDtl dtl2 = NimsApiDto.DsuseRptDtl.builder()
			.usrRptIdNo("usrRptIdNo1111")
			.usrRptLnIdNo("dsuseRptDtlIdNo22")
			.prductCd("상품코드1")
			.prductNm("제품명1")
			.minDistbQy(1)
			.pceQy(0)
			.mnfNo("제조번호1")
			.prdValidDe("20240401")
			.mnfSeq("-")
			.storgeNo("저장소번호1")
			.storgeNm("저장소명1")
			.mvmnTyCd("1102")
			.dsuseQy(10)
			.build();

		NimsApiDto.DsuseRpt dsuseRpt = NimsApiDto.DsuseRpt.builder()
			.bsshCd("bsshCd")
			.usrRptIdNo("usrRptIdNo1111")
			.rptTyCd("0")
			.rndDtlRptCnt(2)
			.hdrDe("20240401")
			.rptDe("20240401")
			.dsuseSeCd("1")
			.dsusePrvCd("01")
			.dsuseMthCd("1")
			.dsuseLoc("보건소")
			.status("0")
			.rptPrgSttsCd("0")
			.dsuseRptDtls(List.of(dtl1, dtl2))
			.build();

		NimsApiDto.DsuseRpt dsuseRpt2 = NimsApiDto.DsuseRpt.builder()
			.bsshCd("bsshCd")
			.usrRptIdNo("usrRptIdNo2222")
			.rptTyCd("0")
			.rndDtlRptCnt(2)
			.hdrDe("20240401")
			.rptDe("20240401")
			.dsuseSeCd("1")
			.dsusePrvCd("01")
			.dsuseMthCd("1")
			.dsuseLoc("보건소")
			.status("0")
			.rptPrgSttsCd("0")
			.dsuseRptDtls(List.of(dtl1, dtl2))
			.build();

		return NimsApiResult.Response.<NimsApiDto.DsuseRpt>builder()
			.header(
				NimsApiResult.Header.builder()
					.resultCd(0)
					.resultMsg("성공")
					.build()
			)
			.body(
				NimsApiResult.Body.<NimsApiDto.DsuseRpt>builder()
					.list(List.of(dsuseRpt, dsuseRpt2))
					.isEndYn("Y")
					.nRecord(2)
					.totalCount(2)
					.build()
			)
			.build();
	}

}
