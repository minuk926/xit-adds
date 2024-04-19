package cokr.xit.adds.biz.nims.service;

import java.util.List;

import cokr.xit.adds.biz.nims.model.BizNimsAarDto;
import cokr.xit.adds.biz.nims.model.BizNimsRequest;
import cokr.xit.adds.inf.nims.model.NimsAarResult;
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
	List<NimsApiDto.BsshInfoSt> saveBsshInfoSt(NimsApiRequest.BsshInfoRequest dto);
	List<NimsApiDto.ProductInfoKd> saveProductInfoKd(NimsApiRequest.ProductInfoRequest dto);
	List<NimsApiDto.MnfSeqInfo> getMnfSeqInfo(NimsApiRequest.MnfSeqInfoRequest dto);
	List<NimsApiDto.JurisdictionGovInfo> getJurisdictionGovInfo(NimsApiRequest.JurisdictionGovInfoRequest dto);
	List<NimsApiDto.StorageInfo> saveStorageInfo(NimsApiRequest.StorageInfoRequest dto);

	//------------------------------------------------------------------------------------------------------
	// NIMS BIZ
	//------------------------------------------------------------------------------------------------------
	NimsAarResult createReportDsuse();
	NimsAarResult updateReportDsuse();
	NimsAarResult cancelReportDsuse();

	BizNimsRequest.DsuseMgt saveDsuseMgt(BizNimsRequest.DsuseMgt dto);

	BizNimsAarDto.AarHeader getTgtDsuseRptData(BizNimsRequest.DsuseMgt dto);
	BizNimsAarDto.AarHeader createTgtDsuseRptData(BizNimsAarDto.AarHeader dto);
}
