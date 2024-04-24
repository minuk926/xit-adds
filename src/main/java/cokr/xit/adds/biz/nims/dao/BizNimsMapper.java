package cokr.xit.adds.biz.nims.dao;

import java.util.Map;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import cokr.xit.adds.biz.nims.model.BizNimsRequest;
import cokr.xit.adds.inf.nims.model.NimsApiDto;
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
	int mergeBsshInfoSt(NimsApiDto.BsshInfoSt dto);
	int mergeProductInfoKd(NimsApiDto.ProductInfoKd dto);
	int mergeStorgeInfo(NimsApiDto.StorageInfo dto);

	//------------------------------------------------------------------------------------------------------
	// NIMS BIZ
	//------------------------------------------------------------------------------------------------------
	int insertDsuseRptInfo(NimsApiDto.DsuseRptInfo dto);
	int insertDsuseRptInfoDtl(NimsApiDto.DsuseRptInfoDtl dto);
	int updateCancelDsuseRptInfo(NimsApiDto.DsuseRptInfo dto);
	int updateCancelDsuseRptInfoDtl(NimsApiDto.DsuseRptInfo dto);
	Map<String, String> recusiveRefUsrRptIdNo(String refUsrRptIdNo);

	int insertDsuseMgt(BizNimsRequest.DsuseMgt dto);
	int insertDsuseMgtDtl(BizNimsRequest.DsuseMgtDtl dto);
	int updateCancelDsuseMgt(BizNimsRequest.DsuseMgt dto);
	int updateCancelDsuseMgtDtl(BizNimsRequest.DsuseMgt dto);

	// BizNimsAarDto.AarHeader selectTgtAarHeader(BizNimsRequest.DsuseMgt dto);
	// List<BizNimsAarDto.AarDetail> selectTgtAarDetails(BizNimsRequest.DsuseMgt dto);
}
