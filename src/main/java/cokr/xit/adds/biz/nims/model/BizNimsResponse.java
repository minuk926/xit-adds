package cokr.xit.adds.biz.nims.model;

import cokr.xit.adds.core.Constants;
import cokr.xit.adds.inf.nims.model.NimsApiDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <pre>
 * description : 
 *
 * author      : limju
 * date        : 2024-04-08
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-04-08    limju       최초 생성
 *
 * </pre>
 */
public class BizNimsResponse {

	/**
	 * 폐기관리정보 조회 response
	 */
	@Schema(name = "DsuseMgtRes", description = "폐기관리정보 조회 response DTO")
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@SuperBuilder
	@EqualsAndHashCode(callSuper = true)
	public static class DsuseMgtRes extends BizNimsRequest.DsuseMgt {

		private String dsuseSeCdNm;
		private String dsusePrvCdNm;
		private String dsuseMthCdNm;
		private String rptTyCdNm;

		/**
		 * <pre>
		 * 대표자명
		 * </pre>
		 */
		private String rprsntvNm;

		/**
		 * 허가번호
		 */
		private String prmisnNo;
	}

	/**
	 * 폐기관리정보 조회 response
	 */
	@Schema(name = "DsuseMgtDtlRes", description = "폐기관리상세정보 조회 response DTO")
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@SuperBuilder
	@EqualsAndHashCode(callSuper = true)
	public static class DsuseMgtDtlRes extends BizNimsRequest.DsuseMgtDtl {

		/**
		 * 마약항정구분
		 */
		private String nrcdSeNm;

		/**
		 * 중점일반구분
		 */
		private String prtmSeNm;

		private String bsshCd;

		/**
		 * 제조수입자명
		 */
		private String bsshNm;

		/**
		 * 최소유통단위
		 */
		private String stdPackngStleNm;

		/**
		 * 낱개단위명
		 */
		private String pceCoUnitNm;
	}

	/**
	 * 폐기관리정보 조회 response
	 */
	@Schema(name = "DsuseRptInfoRes", description = "폐기보고관리정보 조회 response DTO")
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@SuperBuilder
	@EqualsAndHashCode(callSuper = true)
	public static class DsuseRptInfoRes extends NimsApiDto.DsuseRptInfo {
		private String dscdmngId;

		private String userId;

		/**
		 * <pre>
		 * 진행상태
		 *
		 * 01-폐기신청서 접수
		 * 02-폐기보고 매핑
		 * 11-민원수령처리(전자결재)
		 * 21-폐기결과통보서 작성
		 * 22-폐기결과보고서 작성
		 * 31-기안 및 발송
		 * 41-폐기보고
		 * 99-종료
		 * </pre>
		 */
		private String prgrsSttsCd = Constants.PRGRS_STTS_CD.RECEIPT.getCode();

		/**
		 * <pre>
		 * 대표자명
		 * </pre>
		 */
		private String rprsntvNm;

		/**
		 * 허가번호
		 */
		private String prmisnNo;

		// /**
		//  * <pre>
		//  * 업체명
		//  * </pre>
		//  */
		// private String nrcdSeNm;
		//
		// /**
		//  * <pre>
		//  * 업체구분명
		//  * </pre>
		//  */
		// private String prtmSeNm;
	}
}
