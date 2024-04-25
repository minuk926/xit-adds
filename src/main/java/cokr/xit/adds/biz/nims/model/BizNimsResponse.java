package cokr.xit.adds.biz.nims.model;

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
	@Schema(name = "DsuseMgtResponse", description = "폐기관리정보 조회 response DTO")
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@SuperBuilder
	@EqualsAndHashCode(callSuper = true)
	public static class DsuseMgtResponse extends NimsApiDto.DsuseRptInfo {
		private String dscdmngId;

		private String userId;

		/**
		 * <pre>
		 * 진행상태
		 *
		 * 01-폐기보고 접수
		 * 02-폐기보고 확인
		 * 11-민원수령처리(전자결재)
		 * 21-폐기결과통보서 작성
		 * 22-폐기결과보고서 작성
		 * 31-기안 및 발송
		 * 41-폐기보고
		 * 99-종료
		 * </pre>
		 */
		private String prgrsSttsCd = "01";

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

	// @Schema(name = "BizDsuseRptInfo", description = "폐기관리정보 DTO")
	// @Data
	// @NoArgsConstructor
	// @AllArgsConstructor
	// @SuperBuilder
	// @EqualsAndHashCode(callSuper = false)
	// public static class BizDsuseRptInfo extends AuditDto {
	// 	/**
	// 	 * 사용자 보고 식별 번호
	// 	 */
	// 	private String usrRptIdNo;
	//
	// 	/**
	// 	 * 참조 사용자 보고 식별 번호
	// 	 * 취소|변경시 필수 - 사용자 보고 식별 번호
	// 	 */
	// 	private String refUsrRptIdNo;
	//
	// 	/**
	// 	 * 마약류취급자식별번호
	// 	 */
	// 	private String bsshCd;
	//
	// 	/**
	// 	 * 마약류취급자명업체명
	// 	 */
	// 	private String bsshNm;
	//
	// 	/**
	// 	 * 업종명
	// 	 */
	// 	private String indutyNm;
	//
	// 	/**
	// 	 * 보고 유형 코드(0-신규,1-취소,2-변경)
	// 	 */
	// 	private String rptTyCd;
	//
	// 	/**
	// 	 * 수불 상세 보고 수
	// 	 */
	// 	private Integer rndDtlRptCnt;
	//
	// 	/**
	// 	 * 취급 일자
	// 	 */
	// 	private String hdrDe;
	//
	// 	/**
	// 	 * 보고 일자
	// 	 */
	// 	private String rptDe;
	//
	// 	/**
	// 	 * 폐기 구분 코드
	// 	 * 1-보건소폐기, 2-공무원임회, 4-도난/분실/재해 발생 사고마약류
	// 	 */
	// 	private String dsuseSeCd;
	//
	// 	/**
	// 	 * 폐기 사유 코드
	// 	 * 01~05, 07~09, 12
	// 	 */
	// 	private String dsusePrvCd;
	//
	// 	/**
	// 	 * 폐기 방법 코드
	// 	 * 1 ~ 9
	// 	 */
	// 	private String dsuseMthCd;
	//
	// 	/**
	// 	 * 폐기 장소
	// 	 */
	// 	private String dsuseLoc;
	//
	// 	/**
	// 	 * 폐기 일자
	// 	 */
	// 	private String dsuseDe;
	//
	// 	/**
	// 	 * 처리 상태 코드(0-정상,1-취소,2-변경)
	// 	 */
	// 	private String status;
	//
	// 	/**
	// 	 * FIXME: 속성명 확정 필요
	// 	 * 보고 진행 상태 코드(0-정상,1-취소,2-변경)
	// 	 */
	// 	private String rptPrgSttsCd;
	//
	// 	/**
	// 	 * 원사용자보고식별번호
	// 	 */
	// 	private String orgUsrRptIdNo;
	//
	// 	/**
	// 	 * 폐기보고상세 목록
	// 	 */
	// 	private List<BizDsuseRptInfoDtl> dsuseRptInfoDtls = new ArrayList<>();
	// }
	//
	// @Schema(name = "BizDsuseRptInfo", description = "폐기관리정보 DTO")
	// @Data
	// @NoArgsConstructor
	// @AllArgsConstructor
	// @SuperBuilder
	// @EqualsAndHashCode(callSuper = false)
	// public static class BizDsuseRptInfoDtl extends AuditDto {
	// 	/**
	// 	 * 사용자 보고 식별 번호
	// 	 */
	// 	private String usrRptIdNo;
	//
	// 	/**
	// 	 * 사용자 보고 라인 식별 번호
	// 	 */
	// 	private String usrRptLnIdNo;
	//
	// 	/**
	// 	 * 제품 코드
	// 	 */
	// 	private String prductCd;
	//
	// 	/**
	// 	 * 제품 명
	// 	 */
	// 	private String prductNm;
	//
	// 	/**
	// 	 * 최소 유통 단위 수량
	// 	 */
	// 	private Integer minDistbQy;
	//
	// 	/**
	// 	 * 낱개 단위 수량
	// 	 */
	// 	private Integer pceQy;
	//
	// 	/**
	// 	 * 제조 번호
	// 	 */
	// 	private String mnfNo;
	//
	// 	/**
	// 	 * 제품 유효기한 일자
	// 	 */
	// 	private String prdValidDe;
	//
	// 	/**
	// 	 * 제조 일련번호
	// 	 */
	// 	private String mnfSeq;
	//
	// 	/**
	// 	 * 이동 유형 코드
	// 	 * 1102: 재고차감, 1170: 재고미차감
	// 	 */
	// 	private String mvmnTyCd;
	//
	// 	/**
	// 	 * 폐기 수량
	// 	 */
	// 	private Integer dsuseQy;
	// }
}
