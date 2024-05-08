package cokr.xit.adds.biz.nims.service.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cokr.xit.adds.biz.nims.dao.BizNimsMapper;
import cokr.xit.adds.biz.nims.model.BizNimsRequest;
import cokr.xit.adds.biz.nims.model.BizNimsResponse;
import cokr.xit.adds.biz.nims.service.BizNimsService;
import cokr.xit.adds.core.Constants;
import cokr.xit.adds.core.spring.exception.ApiCustomException;
import cokr.xit.adds.core.util.ApiUtil;
import cokr.xit.adds.inf.nims.model.NimsApiDto;
import cokr.xit.adds.inf.nims.model.NimsApiDto.BsshInfoSt;
import cokr.xit.adds.inf.nims.model.NimsApiRequest;
import cokr.xit.adds.inf.nims.model.NimsApiRequest.BsshInfoReq;
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
	/**
	 * <pre>
	 *     DB에서 먼저 조회(dbSkipYn = 'Y' 인 경우는 DB 조회 skip)
	 *     -> 조회 결과가 없는 경우 API 조회
	 *     업체정보 조회후 DB 저장
	 *     조회 건수를 제한하기 위해 조회 조건 강제 - 업체명(3자 이상) 필수
	 * @param dto NimsApiRequest.BsshInfoRequest
	 * @return List<NimsApiDto.BsshInfoSt>
	 * </pre>
	 */
	@Override
	public List<BsshInfoSt> saveBsshInfoSt(BsshInfoReq dto) {
		if(!isEmpty(dto.getBn()) && dto.getBn().length() < 3) {
			throw ApiCustomException.create("업체[사업자]명은 3자 이상 으로 조회해 주세요");
		}

		// DB 조회
		List<BsshInfoSt> list = new ArrayList<>();
		if("N".equals(dto.getDbSkipYn())) {
			list = bizNimsMapper.selectBsshInfos(dto);
			if (!isEmpty(list)) 	return list;
		}

		while(true) {
			// 마약류취급자식별번호로 마약류취급자정보 조회
			NimsApiResult.Response<BsshInfoSt> rslt = infNimsService.getBsshInfoSt(dto);
			List<BsshInfoSt> curList = rslt.getResult();

			if(isEmpty(curList)) break;

			for (BsshInfoSt d : curList) {
				d.setRgtr(Constants.NIMS_API_USER_ID);
				bizNimsMapper.mergeBsshInfoSt(d);
			}
			list.addAll(curList);

			if(rslt.isEndYn()) break;
			dto.setPg(String.valueOf(Integer.parseInt(dto.getPg()) + 1));

		}
		return list;
	}

	/**
	 * <pre>
	 *     DB에서 먼저 조회(dbSkipYn = 'Y' 인 경우는 DB 조회 skip)
	 *     -> 조회 결과가 없는 경우 API 조회
	 *     상품정보 조회후 DB 저장
	 *     제조번호 조회 여부에 따라 제조번호, 일련번호, 유효기간 정보 목록 추가
	 *     조회 건수를 제한하기 위해 조회 조건 강제 - 상품번호 또는 상품명(2자 이상) 필수
	 * @param dto NimsApiRequest.ProductInfoRequest
	 * @param isMnfSeqInfo 제조번호 조회 여부
	 * @return
	 * </pre>
	 */
	@Override
	public List<NimsApiDto.ProductInfoKd> saveProductInfoKd(NimsApiRequest.ProductInfoReq dto, boolean isMnfSeqInfo) {
		if(isEmpty(dto.getP()) && isEmpty(dto.getPn())){
			throw ApiCustomException.create("상품번호 또는 상품명은 필수 입니다");
		}

		if(!isEmpty(dto.getPn()) && dto.getPn().length() < 2){
			throw ApiCustomException.create("상품명은 2자 이상 으로 조회해 주세요");
		}

		List<NimsApiDto.ProductInfoKd> list = new ArrayList<>();
		// DB 조회
		if("N".equals(dto.getDbSkipYn())) {
			list = bizNimsMapper.selectProductInfos(dto);
			if (!isEmpty(list)){
				if(isMnfSeqInfo)	productInfoaddMnfSeqs(list);
				return list;
			}
		}
		while(true) {
			// 제품코드로 제품정보 조회
			NimsApiResult.Response<NimsApiDto.ProductInfoKd> rslt = infNimsService.getProductInfoKd(dto);
			List<NimsApiDto.ProductInfoKd> curList = rslt.getResult();

			if(isEmpty(curList))	break;

			for (NimsApiDto.ProductInfoKd d : curList) {
				d.setRgtr(Constants.NIMS_API_USER_ID);
				bizNimsMapper.mergeProductInfoKd(d);
			}

			// 제조 번호, 일련번호, 유효기간  정보 목록 추가
			if(isMnfSeqInfo)	productInfoaddMnfSeqs(curList);
			list.addAll(curList);

			if(rslt.isEndYn()) break;
			dto.setPg(String.valueOf(Integer.parseInt(dto.getPg()) + 1));
		}
		return list;
	}

	@Override
	public List<NimsApiDto.MnfSeqInfo> getMnfSeqInfo(NimsApiRequest.MnfSeqInfoReq dto) {
		NimsApiResult.Response<NimsApiDto.MnfSeqInfo> response = infNimsService.getMnfSeqInfo(dto);

		List<NimsApiDto.MnfSeqInfo> results = response.getResultOrThrow();
		// FIXME: 내림 차순 정렬
		results.sort((a, b) -> {
			if (isEmpty(a.getPrdValidDe()) && isEmpty(b.getPrdValidDe()))
				return 0;
			if (isEmpty(a.getPrdValidDe()))
				return 1;
			if (isEmpty(b.getPrdValidDe()))
				return -1;
			return b.getPrdValidDe().compareTo(a.getPrdValidDe());
		});

		return results;
	}

	/**
	 * <pre>
	 * 폐기연계보고 데이타를 API에서 조회후 DB 저장
	 * 변경 이력 관리 등을 위해 orgUsrRptIdNo(원사용자보고식별번호)를 추가 하여 저장
	 *
	 * 데이타 필터링 적용을 위해 아래 순서로 진행
	 * 0. 조회(저장)한 데이타 대상 에서 제외 (usrRptIdNo가 DB에 저장된 경우)
	 * 1. 신규(rptTyCd : 0) 저장 - tb_dsuse_rpt_info, tb_dsuse_rpt_info_dtl 생성
	 *    - orgUsrRptIdNo(원사용자보고식별번호) = 사용자보고식별번호(usrRptIdNo)
	 * 2. 신규 외의 경우(rptTyCd : 1 - 취소, 2 - 변경)
	 *   2-1. refUsrRptIdNo 필수 체크
	 *   2-2. 폐기보고정보, 폐기관리 변경
	 *      1) tb_dsuse_rpt_info, tb_dsuse_rpt_info_dtl 사용여부 'N' update
	 *      2) tb_dsuse_mgt 변경
	 *         조건 : 사용자보고식별번호 = 참조사용자보고식별번호
	 *          => usr_rpt_id_no -> refUsrRptIdNo update
	 *          => 취소인 경우 use_yn = 'N' update
	 *   2-3. tb_dsuse_rpt_info, tb_dsuse_rpt_info_dtl 생성
	 *        => 취소인 경우는 tb_dsuse_rpt_info의 사용 여부 'N'으로 생성
	 * @param reqDto NimsApiRequest.DsuseRptInfoRequest
	 * @return List<NimsApiDto.DsuseRptInfo>
	 * </pre>
	 */
	@Override
	public List<NimsApiDto.DsuseRptInfo> saveDsuseRptInfo(NimsApiRequest.DsuseRptInfoReq reqDto) {
		List<NimsApiDto.DsuseRptInfo> rsltList = new ArrayList<>();

		while(true) {
			NimsApiResult.Response<NimsApiDto.DsuseRptInfo> rslt = infNimsService.getDsuseRptInfo(reqDto);
			List<NimsApiDto.DsuseRptInfo> curList = rslt.getResultOrThrow();

			if(isEmpty(curList)) break;

			rsltList.addAll(curList);

			if(rslt.isEndYn()) break;
			reqDto.setPg(String.valueOf(Integer.parseInt(reqDto.getPg()) + 1));
		}

		// TODO: 보고관리-보고정보 매핑 처리
		// 미완료(종료)된 폐기 관리 목록 조회
		List<BizNimsResponse.DsuseMgtRes> dsuseMgts = getDsuseMgts(BizNimsRequest.DsuseMgtInq.builder()
			.prgrsSttsCd(Constants.PRGRS_STTS_CD.END.getCode())
			.build());

		//////////////////////////////////////////////////////////////////////////////
		// FIXME : 테스트를 위한 코드
		//////////////////////////////////////////////////////////////////////////////
		// for (NimsApiDto.DsuseRptInfo dto : rsltList) {
		// 	// 폐기 관리 데이타 매핑
		// 	for(BizNimsResponse.DsuseMgtResponse mgtDto: dsuseMgts){
		// 		// 폐기 신청서 접수 상태 데이타만 처리
		// 		if(Constants.PRGRS_STTS_CD.RECEIPT.getCode().equals(mgtDto.getPrgrsSttsCd())){
		// 			dto.mappingDsuseRptInfo(mgtDto);
		// 		};
		// 	}
		// }
		// if(true)	return null;
		//////////////////////////////////////////////////////////////////////////////


		// 0. 조회(저장)한 데이타 대상 에서 제외 (usrRptIdNo가 DB에 저장된 경우)
		List<NimsApiDto.DsuseRptInfo> list = new ArrayList<>();
		for (NimsApiDto.DsuseRptInfo dto : rsltList) {
			Map<String, String> map = new HashMap<>();
			map.put("usrRptIdNo", dto.getUsrRptIdNo());
			if(isEmpty(bizNimsMapper.selectDsuseRptInfoByUsrRptIdNo(map))){
				list.add(dto);
			};
		}

		// 1. 신규(rptTyCd : 0) 저장 - tb_dsuse_rpt_info, tb_dsuse_rpt_info_dtl 생성
		for (NimsApiDto.DsuseRptInfo dto : list) {
			dto.setOrgUsrRptIdNo(dto.getUsrRptIdNo());
			dto.setRgtr(Constants.NIMS_API_USER_ID);

			// 신규가 아닌 경우 skip
			if(!"0".equals(dto.getRptTyCd())) continue;

			// 폐기 보고 정보 데이타 생성
			createDsuseRpt(dto, true);

			// FIXME : 폐기 관리 데이타 매핑
			List<BizNimsResponse.DsuseMgtRes> newList = dto.mappingNewDsuseRptInfo(dsuseMgts);
			if(newList.size() > 1){
				throw ApiCustomException.create("폐기 관리 데이타 매핑 오류[폐기 관리 데이타 중복]");
			}
			if(bizNimsMapper.updateMappingDsuseMgt(newList.get(0)) == 1){
				throw ApiCustomException.create("폐기 관리 데이타 매핑 오류[폐기 관리 데이타 매핑 실패]");
			};
		}



		// 2. 신규 외의 경우(rptTyCd : 1 - 취소, 2 - 변경)
		String errMsg = null;
		for (NimsApiDto.DsuseRptInfo dto : list) {
			dto.setRgtr(Constants.NIMS_API_USER_ID);

			// 신규인 경우 skip
			if("0".equals(dto.getRptTyCd())) continue;

			// 2-1. refUsrRptIdNo 필수 체크
			if(isEmpty(dto.getRefUsrRptIdNo())){
				throw ApiCustomException.create("데이타 오류[취소 및 변경인 경우 참조사용자식별번호(REF_USR_RPT_ID_NO) 필수]");
			}

			// 2-2. 폐기보고정보, 폐기관리 변경
			//   1) tb_dsuse_rpt_info, tb_dsuse_rpt_info_dtl 사용여부 'N' update
			//   2) tb_dsuse_mgt 변경
			//      조건 : 사용자보고식별번호 = 참조사용자보고식별번호
			//         => usr_rpt_id_no -> refUsrRptIdNo update
			//         => 취소인 경우 use_yn = 'N' update
			updateDsuseRpt(dto);

			// 2-3. tb_dsuse_rpt_info, tb_dsuse_rpt_info_dtl 생성 (취소인 경우는 tb_dsuse_rpt_info의 사용 여부 'N'으로 생성)
			createDsuseRpt(dto, false);

			// FIXME : 폐기 관리 데이타 매핑
			List<BizNimsResponse.DsuseMgtRes> newList = dto.mappingDsuseRptInfo(dsuseMgts);
			if(newList.size() > 1){
				throw ApiCustomException.create("폐기 관리 데이타 매핑 오류[폐기 관리 데이타 중복]");
			}
			if(bizNimsMapper.updateMappingDsuseMgt(newList.get(0)) == 1){
				throw ApiCustomException.create("폐기 관리 데이타 매핑 오류[폐기 관리 데이타 매핑 실패]");
			};
		}




		// return dtos;
		return list;
	}

	//------------------------------------------------------------------------------------------------------
	// NIMS BIZ
	//------------------------------------------------------------------------------------------------------

	/**
	 * <pre>
	 *     사고마약류폐기관리 생성
	 * @param dto BizNimsRequest.DsuseMgt
	 * @return BizNimsRequest.DsuseMgt 생성된 폐기관리 정보
	 * </pre>
	 */
	public BizNimsRequest.DsuseMgt saveDsuseMgt(BizNimsRequest.DsuseMgt dto) {
		ApiUtil.validate(dto, null, validator);
		if(dto.getRndDtlRptCnt() != dto.getDsuseMgtDtls().size()) throw ApiCustomException.create("폐기물 보고수 오류[폐기물 갯수 확인]");
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

	/**
	 * <pre>
	 *     폐기 관리 목록 조회
	 * @param dto BizNimsRequest.DsuseMgtInq
	 * @return List<BizNimsResponse.DsuseMgtResponse> 조회된 폐기 관리 목록
	 * </pre>
	 */
	@Override
	public List<BizNimsResponse.DsuseMgtRes> getDsuseMgts(BizNimsRequest.DsuseMgtInq dto) {
		List<BizNimsResponse.DsuseMgtRes> resList = bizNimsMapper.selectDsuseMgts(dto);

		for (BizNimsResponse.DsuseMgtRes r : resList) {
			r.setRptTyCdNm(Constants.RPT_TY_CD.getName(r.getRptTyCd()));
			r.setDsuseSeCdNm(Constants.DSUSE_SE_CD.getName(r.getDsuseSeCd()));
			r.setDsusePrvCdNm(Constants.DSUSE_PRV_CD.getName(r.getDsusePrvCd()));
			r.setDsuseMthCdNm(Constants.DSUSE_MTH_CD.getName(r.getDsuseMthCd()));

			Map<String, String> map = new HashMap<>();
			map.put("dscdmngId", r.getDscdmngId());
			List<BizNimsResponse.DsuseMgtDtlRes> dsuseRptInfoDtls = bizNimsMapper.selectDsuseMgtDtls(map);
			setDsuseMgtDtlAddProductInfo(dsuseRptInfoDtls);
			r.getDsuseMgtDtls().addAll(dsuseRptInfoDtls);
		}

		return resList;
	}



	/**
	 * <pre>
	 *    폐기관리정보 저장
	 * @param dtos List<BizNimsRequest.DsuseMgt>
	 * @return List<BizNimsResponse.DsuseRptInfoResponse>
	 * </pre>
	 */
	// @Deprecated
	// @Override
	// public List<BizNimsResponse.DsuseRptInfoResponse> saveDsuseMgts(List<BizNimsRequest.DsuseMgt> dtos) {
	// 	for (BizNimsRequest.DsuseMgt dto : dtos) {
	// 		ApiUtil.validate(dto, null, validator);
	// 	}
	//
	// 	final List<String> dscdmngIds = new ArrayList<>();
	// 	for (BizNimsRequest.DsuseMgt dto : dtos) {
	// 		dto.setRgtr(Constants.NIMS_API_USER_ID);
	//
	// 		bizNimsMapper.insertDsuseMgt(dto);
	// 		dscdmngIds.add(dto.getDscdmngId());
	// 	}
	// 	List<BizNimsResponse.DsuseRptInfoResponse> resList = bizNimsMapper.selectSavedDsuseMgts(dscdmngIds);
	//
	// 	// 마약류취급업체의 허가번호(prmisnNo), 대표자명(rprsntvNm) set
	// 	setAddBsshInfo(resList);
	//
	// 	return resList;
	// }


	//------------------------------------------------------------------------------------------------------
	// private method
	//------------------------------------------------------------------------------------------------------
	/**
	 * <pre>
	 * 제품 추가 정보 set
	 * 마약항정구분(nrcdSeNm), 중점일반구분(prtmSenm), 제조수입자명(bsshNm)
	 * 제품최소유통단위(stdPackngStleNm), 제품낱개단위명(pceCoUnitNm) set
	 * @param dtlList <NimsApiDto.DsuseRptInfoDtl>
	 * </pre>
	 */
	private void setDsuseMgtDtlAddProductInfo(List<BizNimsResponse.DsuseMgtDtlRes> dtlList) {

		for (BizNimsResponse.DsuseMgtDtlRes r : dtlList) {//if()
			// 마약항정구분(nrcdSeNm), 중점일반구분(prtmSenm)
			if (isEmpty(r.getNrcdSeNm()) || isEmpty(r.getPrtmSeNm())) {
				//NimsApiResult.Response<NimsApiDto.ProductInfoKd> result = infNimsService.getProductInfoKd(

				List<NimsApiDto.ProductInfoKd> list = saveProductInfoKd(
					NimsApiRequest.ProductInfoReq.builder()
						.fg("1")
						.pg("1")
						.p(r.getPrductCd())
						.build(),
					false
				);
				if (isEmpty(list)) {
					// FIXME : 데이타 정상 흐름 확인후 comment 제거
					continue;
					//throw ApiCustomException.create(String.format("데이타 오류(제품코드[%s]에 해당하는 데이타가 없습니다)", r.getPrductCd()));
				}
				r.setNrcdSeNm(list.get(0).getNrcdSeNm());
				r.setPrtmSeNm(list.get(0).getPrtmSeNm());
				r.setStdPackngStleNm(list.get(0).getStdPackngStleNm());
				r.setPceCoUnitNm(list.get(0).getPceCoUnitNm());
			}

			// 제조수입자명(bsshNm)
			if (isEmpty(r.getBsshNm()) && !isEmpty(r.getBsshCd())) {
				List<BsshInfoSt> list = saveBsshInfoSt(
					BsshInfoReq.builder()
						.fg("1")
						.pg("1")
						.bc(r.getBsshCd())
						.build()
				);
				if (isEmpty(list)) {
					// FIXME : 데이타 정상 흐름 확인후 comment 제거
					continue;
					//throw ApiCustomException.create(String.format("데이타 오류(마약류취급자식별번호[%s]에 해당하는 데이타가 없습니다)", r.getBsshCd()));
				}
				r.setBsshNm(list.get(0).getBsshNm());
			}
		}
	}


	/**
	 * <pre>
	 * tb_dsuse_rpt_info, tb_dsuse_rpt_info_dtl 사용여부 'N' update
	 * tb_dsuse_mgt
	 * 조건 : 사용자보고식별번호 = 참조사용자보고식별번호
	 *      => usr_rpt_id_no -> refUsrRptIdNo update
	 *      => 취소인 경우 use_yn = 'N' update
	 * @param dto NimsApiDto.DsuseRptInfo
	 * </pre>
	 */
	private void updateDsuseRpt(NimsApiDto.DsuseRptInfo dto) {
		String errMsg;
		if ("1".equals(dto.getRptTyCd())) errMsg = "취소";
		else errMsg = "변경";

		if (bizNimsMapper.updateCancelDsuseRptInfo(dto) == 1) {
			// TODO : 폐기 관리 테이블에 사용자보고식별번호 반영
			// tb_dsuse_mgt
			// 조건 : 사용자보고식별번호 = 참조사용자보고식별번호
			//      => usr_rpt_id_no -> refUsrRptIdNo update
			//      => 취소인 경우 use_yn = 'N' update
			// if(bizNimsMapper.updateCancelDsuseMgt(dto) == 0){
			// 	throw ApiCustomException.create("폐기보고정보 변경 적용 실패\n[폐기관리테이블에 사용자보고식별번호 = 참조사용자보고식별번호에 해당하는 데이타 미존재]");
			// }

			int cnt = bizNimsMapper.updateCancelDsuseRptInfoDtl(dto);
			if(cnt == 0) throw ApiCustomException.create(String.format("폐기 정보 상세 %s 실패", errMsg));

			// 변경인 경우 상세 데이타 건수와 일치 하지 않는 경우 오류 처리
			if ("2".equals(dto.getRptTyCd()) && dto.getRndDtlRptCnt() != cnt) {
				throw ApiCustomException.create(String.format("폐기 정보 상세 %s 실패", errMsg));
			}

		} else {
			throw ApiCustomException.create(String.format("폐기 정보 %s 실패", errMsg));
		}
	}

	/**
	 * <pre>
	 * tb_dsuse_rpt_info, tb_dsuse_rpt_info_dtl 생성
	 * => 취소인 경우는 tb_dsuse_rpt_info의 사용 여부 'N'으로 생성
	 *
	 * 원사용자보고식별번호 조회
	 * 1. 신규(rptTyCd : 0) - 사용자보고식별번호
	 * 2. 취소 또는 변경(rptTyCd : 1, 2) - 참조사용자보고식별번호 필수
	 * => 사용자보고식별번호 = 참조사용자식별번호 조건 으로 조회
	 * => 조회결과 참조사용자식별번호가 존재하지 않을때 까지 반복 조회(신규(rptTyCd : 0)인 경우 까지)
	 * => 사용자보고식별번호를 원사용자보고식별번호로 설정
	 * @param dto NimsApiDto.DsuseRptInfo
	 * @param isNew boolean
	 * </pre>
	 */
	private void createDsuseRpt(NimsApiDto.DsuseRptInfo dto, boolean isNew) {
		dto.setRptTyCdNm(Constants.RPT_TY_CD.getName(dto.getRptTyCd()));
		dto.setDsuseSeCdNm(Constants.DSUSE_SE_CD.getName(dto.getDsuseSeCd()));
		dto.setDsusePrvCdNm(Constants.DSUSE_PRV_CD.getName(dto.getDsusePrvCd()));
		dto.setDsuseMthCdNm(Constants.DSUSE_MTH_CD.getName(dto.getDsuseMthCd()));

		// TODO : 폐기 관리 테이블에 사용자보고식별번호 반영
		// 원 사용자 식별 번호 set - 변경/취소 인 경우
		// 신규인 경우는 사용자보고식별번호로 설정
		if(!isNew){

			String refUsrRptIdNo = dto.getRefUsrRptIdNo();

			// 참조사용자보고식별번호로 원사용자보고식별번호조회
			while(true) {
				Map<String, String> map = bizNimsMapper.recusiveRefUsrRptIdNo(refUsrRptIdNo);
				if(map == null) throw ApiCustomException.create("데이타 오류[참조사용자로 사용자보고식별번호 조회 실패 - 데이타 누락]");

				if(!isEmpty(map.get("refUsrRptIdNo"))){
					refUsrRptIdNo = map.get("refUsrRptIdNo");
					continue;
				}

				if("0".equals(map.get("rptTyCd"))){
					dto.setOrgUsrRptIdNo(map.get("usrRptIdNo"));
					break;
				} else {
					throw ApiCustomException.create("데이타 오류[참조사용자로 사용자보고식별번호 조회 실패 - 신규보고 데이타 누락]");
				}
			}
		}


		// TODO : 폐기 관리 테이블에 사용자보고식별번호 반영
		if (bizNimsMapper.insertDsuseRptInfo(dto) == 1) {

			//취소인 경우 상세 데이타 등록 skip
			if("1".equals(dto.getRptTyCd()))	return;

			int dtlCnt = 0;
			for (NimsApiDto.DsuseRptInfoDtl d : dto.getDsuseRptInfoDtls()) {
				d.setRgtr(Constants.NIMS_API_USER_ID);
				dtlCnt = dtlCnt + bizNimsMapper.insertDsuseRptInfoDtl(d);
			}
			if (dto.getRndDtlRptCnt() != dtlCnt)
				throw ApiCustomException.create("폐기 정보 상세 등록 실패");
		} else {
			throw ApiCustomException.create("폐기 정보 등록 실패");
		}
	}

	/**
	 * <pre>
	 * 마약류 취급자 업체 추가 정보 set
	 * 허가번호(prmisnNo), 대표자명(rprsntvNm) set
	 * @param resList List<BizNimsResponse.DsuseMgtResponse>
	 * </pre>
	 */
	private void setAddBsshInfo(List<BizNimsResponse.DsuseRptInfoRes> resList) {
        for (BizNimsResponse.DsuseRptInfoRes r : resList) {
            r.setRptTyCdNm(Constants.RPT_TY_CD.getName(r.getRptTyCd()));
            r.setDsuseSeCdNm(Constants.DSUSE_SE_CD.getName(r.getDsuseSeCd()));
            r.setDsusePrvCdNm(Constants.DSUSE_PRV_CD.getName(r.getDsusePrvCd()));
            r.setDsuseMthCdNm(Constants.DSUSE_MTH_CD.getName(r.getDsuseMthCd()));

            if (isEmpty(r.getPrmisnNo())) {
                List<BsshInfoSt> list = saveBsshInfoSt(
                    BsshInfoReq.builder()
                        .fg("1")
                        .pg("1")
                        .bc(r.getBsshCd())
                        .build()
                );
                if (isEmpty(list)) {
                    // FIXME : 데이타 정상 흐름 확인후 comment 제거
                    continue;
                    //throw ApiCustomException.create(String.format("데이타 오류(마약류취급자식별번호[%s]에 해당하는 데이타가 없습니다)", r.getBsshCd()));
                }
                r.setPrmisnNo(list.get(0).getPrmisnNo());
                r.setRprsntvNm(list.get(0).getRprsntvNm());
            }
        }
    }

	/**
	 * <pre>
	 * 제품 추가 정보 set
	 * 마약항정구분(nrcdSeNm), 중점일반구분(prtmSenm), 제조수입자명(bsshNm)
	 * 제품최소유통단위(stdPackngStleNm), 제품낱개단위명(pceCoUnitNm) set
	 * @param dtlList <NimsApiDto.DsuseRptInfoDtl>
	 * </pre>
	 */
	private void setAddProductInfo(List<NimsApiDto.DsuseRptInfoDtl> dtlList) {

        for (NimsApiDto.DsuseRptInfoDtl r : dtlList) {//if()
            // 마약항정구분(nrcdSeNm), 중점일반구분(prtmSenm)
			if (isEmpty(r.getNrcdSeNm()) || isEmpty(r.getPrtmSeNm())) {
                //NimsApiResult.Response<NimsApiDto.ProductInfoKd> result = infNimsService.getProductInfoKd(

                List<NimsApiDto.ProductInfoKd> list = saveProductInfoKd(
                    NimsApiRequest.ProductInfoReq.builder()
                        .fg("1")
                        .pg("1")
                        .p(r.getPrductCd())
                        .build(),
					false
                );
                if (isEmpty(list)) {
                    // FIXME : 데이타 정상 흐름 확인후 comment 제거
                    continue;
                    //throw ApiCustomException.create(String.format("데이타 오류(제품코드[%s]에 해당하는 데이타가 없습니다)", r.getPrductCd()));
                }
                r.setNrcdSeNm(list.get(0).getNrcdSeNm());
                r.setPrtmSeNm(list.get(0).getPrtmSeNm());
				r.setStdPackngStleNm(list.get(0).getStdPackngStleNm());
				r.setPceCoUnitNm(list.get(0).getPceCoUnitNm());
            }

			// 제조수입자명(bsshNm)
			if (isEmpty(r.getBsshNm()) && !isEmpty(r.getBsshCd())) {
				List<BsshInfoSt> list = saveBsshInfoSt(
					BsshInfoReq.builder()
						.fg("1")
						.pg("1")
						.bc(r.getBsshCd())
						.build()
				);
				if (isEmpty(list)) {
					// FIXME : 데이타 정상 흐름 확인후 comment 제거
					continue;
					//throw ApiCustomException.create(String.format("데이타 오류(마약류취급자식별번호[%s]에 해당하는 데이타가 없습니다)", r.getBsshCd()));
				}
				r.setBsshNm(list.get(0).getBsshNm());
			}
        }
    }

	/**
	 * <pre>
	 * 상품정보에 제조번호 목록 추가
	 * @param list List<NimsApiDto.ProductInfoKd>
	 * </pre>
	 */
	private void productInfoaddMnfSeqs(List<NimsApiDto.ProductInfoKd> list) {
		String productCd = "";

		try {

            for (NimsApiDto.ProductInfoKd d : list) {
                productCd = d.getPrductCd();

                List<NimsApiDto.MnfSeqInfo> mnfList = getMnfSeqInfo(
                    NimsApiRequest.MnfSeqInfoReq.builder()
                        .fg("1")
                        .pg("1")
                        .p(d.getPrductCd())
                        .build()
                );
                d.getMnfSeqInfos().addAll(mnfList);
            }

        }catch (Exception e){
			if( e instanceof ApiCustomException){
				throw ApiCustomException.create(String.format("[%s]의 제조번호 정보를 찾을수 없습니다.", productCd));
			}
			throw ApiCustomException.create(e.getMessage());
		}
	}










	// @Override
	// public List<NimsApiDto.JurisdictionGovInfo> getJurisdictionGovInfo(NimsApiRequest.JurisdictionGovInfoRequest dto) {
	// 	NimsApiResult.Response<NimsApiDto.JurisdictionGovInfo> result = infNimsService.getJurisdictionGovInfo(dto);
	// 	return result.getResultOrThrow();
	// }
	//
	// @Override
	// public List<NimsApiDto.StorageInfo> saveStorageInfo(NimsApiRequest.StorageInfoRequest dto) {
	// 	NimsApiResult.Response<NimsApiDto.StorageInfo> result = infNimsService.getStorageInfo(dto);
	// 	List<NimsApiDto.StorageInfo> list = result.getResultOrThrow();
	//
	// 	for (NimsApiDto.StorageInfo d : list) {
	// 		d.setRgtr(Constants.NIMS_API_USER_ID);
	// 		bizNimsMapper.mergeStorgeInfo(d);
	// 	}
	// 	return list;
	// }


/*
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
*/
	// @Override
	// public NimsAarResult createReportDsuse() {
	// 	String xml = toXml();
	// 	log.info(xml);
	// 	return NimsAarResult.builder().build();
	// }
	//
	// @Override
	// public NimsAarResult updateReportDsuse() {
	// 	String xml = toXml();
	// 	log.info(xml);
	// 	return NimsAarResult.builder().build();
	// }
	//
	// @Override
	// public NimsAarResult cancelReportDsuse() {
	// 	String xml = toXml();
	// 	log.info(xml);
	// 	return NimsAarResult.builder().build();
	// }



	// private String toXml() {
	// 	Aar dto = getAar();
	//
	// 	XmlMapper mapper = new XmlMapper();
	// 	mapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
	// 	mapper.enable(SerializationFeature.INDENT_OUTPUT);
	//
	// 	String xmlString = null;
	// 	try {
	// 		xmlString = mapper.writeValueAsString(dto);
	// 	} catch (JsonProcessingException e) {
	// 		throw ApiCustomException.create(e.getMessage());
	// 	}
	// 	xmlString = xmlString.replaceFirst("nims\">",
	// 		"nims\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
	// 	return xmlString.replaceFirst(" xmlns=\"\"", StringUtils.EMPTY);
	// }
	//
	// private Aar getAar() {
	// 	Aar.ReportSet reportSet = Aar.ReportSet.builder()
	// 		.header(List.of(getHeader()))
	// 		.build();
	//
	// 	return Aar.builder()
	// 		.reportSet(reportSet)
	// 		.build();
	// }
	//
	// private Aar.Header getHeader() {
	// 	return Aar.Header.builder()
	// 		.hdrDe("20240326")
	// 		.bsshCd("123456789")
	// 		.lines(getLines())
	// 		.atchFileCo("2")
	// 		.atchFiles(getAtchFiles())
	// 		.build();
	// }
	//
	// private Aar.Lines getLines() {
	//
	// 	return Aar.Lines.builder()
	// 		.line(getLineList())
	// 		.build();
	// }
	//
	// private Aar.AtchFiles getAtchFiles() {
	// 	return Aar.AtchFiles.builder()
	// 		.atchFileNm(List.of("file-1.txt","file-2.txt"))
	// 		.build();
	// }
	//
	// private List<Aar.Line> getLineList(){
	// 	Aar.Line line = Aar.Line.builder()
	// 		.usrRptIdNo("123456789")
	// 		.usrRptLnIdNo("123456789")
	// 		.storgeNo("123456789")
	// 		.mvmnTyCd("123456789")
	// 		.prductCd("123456789")
	// 		.build();
	// 	Aar.Line line2 = Aar.Line.builder()
	// 		.usrRptIdNo("123456789-1")
	// 		.usrRptLnIdNo("123456789-1")
	// 		.storgeNo("123456789-1")
	// 		.mvmnTyCd("123456789-1")
	// 		.prductCd("123456789-1")
	// 		.build();
	//
	// 	return List.of(line, line2);
	// }
	//
	//
	// private void setStorgeNo(BizNimsAarDto.AarHeader aarHeader, List<BizNimsAarDto.AarDetail> aarDetails) {
	//
	// 	if(isEmpty(aarDetails.get(0).getStorgeNo())){
	// 		try {
	// 			List<NimsApiDto.StorageInfo> storageInfos = saveStorageInfo(
	// 				NimsApiRequest.StorageInfoRequest.builder()
	// 					.fg("1")
	// 					.pg("1")
	// 					.bc(aarHeader.getBsshCd())
	// 					.build()
	// 			);
	// 			aarDetails.forEach(d -> d.setStorgeNo(storageInfos.get(0).getStorgeNo()));
	//
	// 		}catch (Exception e){
	// 			if( e instanceof ApiCustomException){
	// 				aarDetails.forEach(d -> d.setStorgeNo("S0001"));
	// 				return;
	// 			}
	// 			throw ApiCustomException.create(e.getMessage());
	// 		}
	// 	}
	// }
	//
	// private void setDsuseInsttCd(BizNimsAarDto.AarHeader aarHeader) {
	//
	// 	try {
	// 		List<NimsApiDto.JurisdictionGovInfo> list = getJurisdictionGovInfo(
	// 			NimsApiRequest.JurisdictionGovInfoRequest.builder()
	// 				.fg("1")
	// 				.pg("1")
	// 				.onm(onm)
	// 				.build()
	// 		);
	// 		aarHeader.setDsuseInsttCd(list.get(0).getOfCd());
	//
	// 	}catch (Exception e){
	// 		if( e instanceof ApiCustomException){
	// 			throw ApiCustomException.create(String.format("[%s]의 관할 행정 기관 코드를 찾을수 없습니다.", onm));
	// 		}
	// 		throw ApiCustomException.create(e.getMessage());
	// 	}
	// }

	// private void setMnfSeqs(List<BizNimsAarDto.AarDetail> aarDetails) {
	// 	AtomicReference<String> productCd = new AtomicReference<>("");
	//
	// 	try {
	//
	// 		aarDetails.forEach(d -> {
	// 			productCd.set(d.getPrductCd());
	//
	// 			List<NimsApiDto.MnfSeqInfo> list = getMnfSeqInfo(
	// 				NimsApiRequest.MnfSeqInfoRequest.builder()
	// 					.fg("1")
	// 					.pg("1")
	// 					.p(d.getPrductCd())
	// 					.build()
	// 			);
	//
	// 			// FIXME: 내림 차순 정렬
	// 			list.sort((a, b) -> {
	// 				if(isEmpty(a.getPrdValidDe()) && isEmpty(b.getPrdValidDe())) return 0;
	// 				if(isEmpty(a.getPrdValidDe())) return 1;
	// 				if(isEmpty(b.getPrdValidDe())) return -1;
	// 				return b.getPrdValidDe().compareTo(a.getPrdValidDe());
	// 			});
	//
	// 			// FIXME: default list 1st value??
	// 			d.setMnfNo(list.get(0).getMnfNo());
	// 			d.setMnfSeq(list.get(0).getMnfSeq());
	// 			d.setPrdValidDe(list.get(0).getPrdValidDe());
	//
	// 			d.getMnfSeqInfos().addAll(list);
	// 		});
	//
	// 	}catch (Exception e){
	// 		if( e instanceof ApiCustomException){
	// 			throw ApiCustomException.create(String.format("[%s]의 제조번호 정보를 찾을수 없습니다.", productCd.get()));
	// 		}
	// 		throw ApiCustomException.create(e.getMessage());
	// 	}
	// }
}
