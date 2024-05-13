package cokr.xit.adds.biz.nims.service;

import java.util.List;

import cokr.xit.adds.biz.nims.model.BizNimsRequest;
import cokr.xit.adds.biz.nims.model.BizNimsResponse;
import cokr.xit.adds.inf.nims.model.NimsApiDto;
import cokr.xit.adds.inf.nims.model.NimsApiRequest;

/**
 * <pre>
 * description : 
 *
 * packageName : cokr.xit.adds.inf.nims.service
 * fileName    : InfNimsService
 * author      : limju
 * date        : 2024-04-04
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-04-04    limju       최초 생성
 *
 * </pre>
 */
public interface BizNimsService {
	//------------------------------------------------------------------------------------------------------
	// NIMS API CALL
	//------------------------------------------------------------------------------------------------------
	List<NimsApiDto.BsshInfoSt> saveBsshInfoSt(NimsApiRequest.BsshInfoReq dto);
	List<NimsApiDto.ProductInfoKd> saveProductInfoKd(NimsApiRequest.ProductInfoReq dto, boolean isMnfSeqInfo);
	List<NimsApiDto.MnfSeqInfo> getMnfSeqInfo(NimsApiRequest.MnfSeqInfoReq dto);
	// List<NimsApiDto.JurisdictionGovInfo> getJurisdictionGovInfo(NimsApiRequest.JurisdictionGovInfoRequest dto);
	// List<NimsApiDto.StorageInfo> saveStorageInfo(NimsApiRequest.StorageInfoRequest dto);

	/**
	 * <pre>
	 * 폐기연계보고 데이타를 API에서 조회후 DB 저장
	 * 변경 이력 관리 등을 위해 orgUsrRptIdNo(원사용자보고식별번호)를 추가 하여 저장
	 *
	 * 데이타 필터링 적용을 위해 아래 순서로 진행
	 * 1. 신규(rptTyCd : 0) 저장 - tb_dsuse_rpt_info, tb_dsuse_rpt_info_dtl 생성
	 *    - orgUsrRptIdNo(원사용자보고식별번호) = 사용자보고식별번호(usrRptIdNo)
	 * 2. 신규 외의 경우(rptTyCd : 1 - 취소, 2 - 변경)
	 *   2-1. refUsrRptIdNo 필수 체크
	 *   2-2. tb_dsuse_rpt_info, tb_dsuse_rpt_info_dtl 사용여부 'N' update
	 *   3-3. tb_dsuse_rpt_info, tb_dsuse_rpt_info_dtl 생성
	 *        => 취소인 경우는 tb_dsuse_rpt_info의 사용 여부 'N'으로 생성
	 * @param dto NimsApiRequest.DsuseRptInfoRequest
	 * @return List<NimsApiDto.DsuseRptInfo>
	 * </pre>
	 */
	List<NimsApiDto.DsuseRptInfo> saveDsuseRptInfo(NimsApiRequest.DsuseRptInfoReq dto);

	//------------------------------------------------------------------------------------------------------
	// NIMS BIZ
	//------------------------------------------------------------------------------------------------------
	// NimsAarResult createReportDsuse();
	// NimsAarResult updateReportDsuse();
	// NimsAarResult cancelReportDsuse();

	BizNimsRequest.DsuseMgt saveDsuseMgt(BizNimsRequest.DsuseMgt dto);
	List<BizNimsResponse.DsuseMgtRes> getDsuseMgts(BizNimsRequest.DsuseMgtInq dto);

	NimsApiDto.ProductInfoKd getPrdMnfSeqInfoOfBarcode(final String barcodeStr);

    // @Deprecated
	// List<BizNimsResponse.DsuseRptInfoResponse> saveDsuseMgts(List<BizNimsRequest.DsuseMgt> dtos);


	// BizNimsAarDto.AarHeader getTgtDsuseRptData(BizNimsRequest.DsuseMgt dto);
	// BizNimsAarDto.AarHeader createTgtDsuseRptData(BizNimsAarDto.AarHeader dto);


}
