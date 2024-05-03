package cokr.xit.adds.inf.nims.service;

import cokr.xit.adds.inf.nims.model.NimsApiDto;
import cokr.xit.adds.inf.nims.model.NimsApiRequest;
import cokr.xit.adds.inf.nims.model.NimsApiResult;

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
public interface InfNimsService {
	NimsApiResult.Response<NimsApiDto.BsshInfoSt> getBsshInfoSt(NimsApiRequest.BsshInfoRequest dto);

	NimsApiResult.Response<NimsApiDto.ProductInfoKd> getProductInfoKd(NimsApiRequest.ProductInfoRequest dto);

	NimsApiResult.Response<NimsApiDto.MnfSeqInfo> getMnfSeqInfo(NimsApiRequest.MnfSeqInfoRequest dto);
	//
	// NimsApiResult.Response<NimsApiDto.JurisdictionGovInfo> getJurisdictionGovInfo(NimsApiRequest.JurisdictionGovInfoRequest dto);
	//
	// NimsApiResult.Response<NimsApiDto.StorageInfo> getStorageInfo(NimsApiRequest.StorageInfoRequest dto);

	NimsApiResult.Response<NimsApiDto.DsuseRptInfo> getDsuseRptInfo(NimsApiRequest.DsuseRptInfoRequest dto);
}
