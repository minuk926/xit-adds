package cokr.xit.adds.biz.nims.dao;

import java.util.List;
import java.util.Map;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import cokr.xit.adds.biz.nims.model.BizNimsRequest;
import cokr.xit.adds.biz.nims.model.BizNimsResponse;
import cokr.xit.adds.inf.nims.model.NimsApiDto;
import cokr.xit.adds.inf.nims.model.NimsApiRequest;
import cokr.xit.foundation.component.AbstractMapper;

/**
 * <pre>
 * description : 
 *
 * packageName : cokr.xit.adds.inf.nims.dao
 * fileName    : InfNimsMapper
 * author      : limju
 * date        : 2024-04-04
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-04-04    limju       최초 생성
 *
 * </pre>
 */
@Mapper
public interface BizNimsMapper extends AbstractMapper {
	//------------------------------------------------------------------------------------------------------
	// NIMS API CALL
	//------------------------------------------------------------------------------------------------------
	void mergeBsshInfoSt(NimsApiDto.BsshInfoSt dto);
	List<NimsApiDto.BsshInfoSt> selectBsshInfos(NimsApiRequest.BsshInfoRequest dto);
	void mergeProductInfoKd(NimsApiDto.ProductInfoKd dto);
	List<NimsApiDto.ProductInfoKd> selectProductInfos(NimsApiRequest.ProductInfoRequest dto);
	// int mergeStorgeInfo(NimsApiDto.StorageInfo dto);

	//------------------------------------------------------------------------------------------------------
	// NIMS BIZ
	//------------------------------------------------------------------------------------------------------
	int insertDsuseMgt(BizNimsRequest.DsuseMgt dto);
	int insertDsuseMgtDtl(BizNimsRequest.DsuseMgtDtl dto);
	List<BizNimsResponse.DsuseMgtResponse> selectDsuseMgts(BizNimsRequest.DsuseMgtInq dto);
	List<BizNimsResponse.DsuseMgtDtlResponse> selectDsuseMgtDtls(Map<String, String> map);

	int insertDsuseRptInfo(NimsApiDto.DsuseRptInfo dto);
	int insertDsuseRptInfoDtl(NimsApiDto.DsuseRptInfoDtl dto);
	int updateCancelDsuseRptInfo(NimsApiDto.DsuseRptInfo dto);
	int updateCancelDsuseRptInfoDtl(NimsApiDto.DsuseRptInfo dto);
	int updateCancelDsuseMgt(NimsApiDto.DsuseRptInfo dto);
	Map<String, String> recusiveRefUsrRptIdNo(String refUsrRptIdNo);


	NimsApiDto.DsuseRptInfo selectDsuseRptInfoByUsrRptIdNo(Map<String, String> map);
	List<NimsApiDto.DsuseRptInfoDtl> selectDsuseRptInfoDtls(Map<String, String> map);


	List<BizNimsResponse.DsuseRptInfoResponse> selectSavedDsuseMgts(List<String> dsuseMgtIds);
	// int insertDsuseMgtDtl(BizNimsRequest.DsuseMgtDtl dto);
	// int updateCancelDsuseMgtDtl(BizNimsRequest.DsuseMgt dto);

	// BizNimsAarDto.AarHeader selectTgtAarHeader(BizNimsRequest.DsuseMgt dto);
	// List<BizNimsAarDto.AarDetail> selectTgtAarDetails(BizNimsRequest.DsuseMgt dto);
}
