package cokr.xit.adds.biz.nims.model;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;

import cokr.xit.adds.core.Constants;
import cokr.xit.adds.core.model.AuditDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
public class BizNimsRequest {

	/**
	 * 마약류 폐기 관리 마스터 request
	 */
	@Schema(name = "DsuseMgt", description = "마약류 폐기 관리 마스터 DTO")
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@SuperBuilder
	@EqualsAndHashCode(callSuper = false)
	public static class DsuseMgt2 extends AuditDto {
		@Schema(requiredMode = AUTO, title = "폐기 관리 ID", example = " ")
		private String dscdmngId;

		@Schema(requiredMode = REQUIRED, title = "사용자 ID", example = " ")
		@NotEmpty(message = "사용자 ID는 필수 입니다")
		private String userId;

		/**
		 * <pre>
		 * 원 사용자 보고 식별 번호
		 * 폐기 보고 생성시의 사용자 보고 식별 번호
		 * 생성 > 변경 > 변경 > 취소 등의 보고시 매번 새로운 보고식별번호가 생성
		 * => 추적을 위해 최초의 생성시 보고식별번호를 기록
		 * YYYYMMDD
		 * </pre>
		 */
		@Schema(requiredMode = REQUIRED, title = "원사용자보고식별번호", example = " ")
		private String orgUsrRptIdNo;

		/**
		 * <pre>
		 * 사용자 보고 식별 번호
		 * 생성후 변경이나 취소시 새로운 보고식별번호로 update
		 * </pre>
		 */
		@Schema(requiredMode = REQUIRED, title = "사용자보고식별번호", example = " ")
		private String usrRptIdNo;

		/**
		 * 마약류취급자식별번호
		 */
		@JsonProperty(value = "BSSH_CD", required = true)
		private String bsshCd;

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
		@Schema(requiredMode = REQUIRED, title = "진행 상태 코드", example = " ", allowableValues = {"01", "02", "11", "21", "22", "31", "41", "99"})
		@Pattern(regexp = "01|02|11|22|31|41|99", message = "진행 상태 코드는 필수 입니다")
		private String prgrsSttsCd = Constants.PRGRS_STTS_CD.RECEIPT.getCode();
	}

	/**
	 * 마약류 폐기 관리 마스터 request
	 */
	@Schema(name = "DsuseMgt", description = "마약류 폐기 관리 마스터 DTO")
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@SuperBuilder
	@EqualsAndHashCode(callSuper = false)
	public static class DsuseMgt extends AuditDto {
		/**
		 * 폐기 관리 ID
		 */
		@Schema(requiredMode = AUTO, title = "폐기 관리 ID", example = " ")
		private String dscdmngId;

		/**
		 * 사용자 ID
		 */
		@Schema(requiredMode = REQUIRED, title = "사용자 ID", example = " ")
		@NotEmpty(message = "사용자 ID는 필수 입니다")
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
		 * 마약류취급자식별번호
		 */
		private String bsshCd;

		/**
		 * 수불상세보고수
		 */
		private Integer rndDtlRptCnt;

		/**
		 * 취급일자
		 */
		private String hdrDe;

		/**
		 * 보고일자
		 */
		private String rptDe;

		/**
		 * <pre>
		 * 폐기 구분 코드
		 * 1-보건소폐기, 2-공무원임회, 4-도난/분실/재해 발생 사고마약류
		 * </pre>
		 */
		private String dsuseSeCd;

		/**
		 * <pre>
		 * 폐기 사유 코드
		 * 01-파손, 02-변질,부패, 03-유효기간 또는 사용기한 경과
		 * 04-유효 기간 임박, 05-사용 중단, 07-폐업, 08-환자 반납
		 * 09-기타, 12-제조 공정중 폐기물
		 * </pre>
		 */
		private String dsusePrvCd;

		/**
		 * <pre>
		 * 폐기 방법 코드
		 * 1-소각, 2-중화, 3-가수 분해, 4-산화, 5-환원
		 * 6-희석, 7-매물, 8-기타(파쇄,혼합), 9-사고
		 * </pre>
		 */
		private String dsuseMthCd;

		/**
		 * 폐기 장소
		 */
		private String dsuseLoc;

		/**
		 * 폐기일자
		 */
		private String dsuseDe;

		/**
		 * <pre>
		 * 사용자 보고 식별 번호
		 * 생성후 변경이나 취소시 새로운 보고식별번호로 update
		 * </pre>
		 */
		@Schema(requiredMode = REQUIRED, title = "사용자보고식별번호", example = " ")
		private String usrRptIdNo;

		/**
		 * <pre>
		 * 원 사용자 보고 식별 번호
		 * 폐기 보고 생성시의 사용자 보고 식별 번호
		 * 생성 > 변경 > 변경 > 취소 등의 보고시 매번 새로운 보고식별번호가 생성
		 * => 추적을 위해 최초의 생성시 보고식별번호를 기록
		 * YYYYMMDD
		 * </pre>
		 */
		@Schema(requiredMode = REQUIRED, title = "원사용자보고식별번호", example = " ")
		private String orgUsrRptIdNo;

		/**
		 * <pre>
		 * 보고 유형 코드
		 * 0-신규, 1-취소, 2-변경
		 * </pre>
		 */
		private String rptTyCd;

		/**
		 * <pre>
		 * 처리상태
		 * </pre>
		 */
		private String stts;

		@Schema(requiredMode = REQUIRED)
		@Builder.Default
		@Valid
		List<DsuseMgtDtl> dsuseMgtDtls = new ArrayList<>();
	}

	/**
	 * 마약류 폐기 관리 상세 request
	 */
	@Schema(name = "DsuseMgtDtl", description = "마약류 폐기 관리 상세 DTO")
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@SuperBuilder
	@EqualsAndHashCode(callSuper = false)
	public static class DsuseMgtDtl extends AuditDto {
		// 폐기관리_id
		private String dscdmngId;
		// 폐기관리_순번
		private String dscdmngSn;
		// 제품_코드
		private String prductCd;
		// 제품_명
		private String prductNm;
		// 최소_유통단위_수량
		private Integer minDistbQy;
		// 낱개단위_수량
		private Integer pceQy;
		// 제조_번호
		private String mnfNo;
		// 제품_유효기한_일자
		private String prdValidDe;
		// 제조_일련번호
		private String mnfSeq;
		// 폐기_수량
		private Integer dsuseQy;
		// 사용자_보고_식별_번호
		private String usrRptIdNo;
		// 사용자_보고_라인_식별_번호
		private String usrRptLnIdNo;

		/**
		 * <pre>
		 *     폐기관리상세와 폐기보고정보 상세의 폐기 정보(상품) 유효성 체크
		 *     -> 일치하는 경우 "Y"
		 * </pre>
		 */
		private String validYn;
	}

	/**
	 * 마약류 폐기 관리 조회 request
	 */
	@Schema(name = "DsuseMgtInq", description = "마약류 폐기 관리 조회 DTO")
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@SuperBuilder
	@EqualsAndHashCode(callSuper = false)
	public static class DsuseMgtInq {
		@Schema(requiredMode = AUTO, title = "폐기 관리 ID 목록", example = "[\"2024050001\", \"2024050002\"]")
		private List<String> dscdmngIds;

		@Schema(requiredMode = REQUIRED, title = "사용자 ID", example = " ")
		@NotEmpty(message = "사용자 ID는 필수 입니다")
		private String userId;

		/**
		 * <pre>
		 * 폐기관리진행상태
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
		@Schema(requiredMode = REQUIRED, title = "진행 상태 코드", example = " ", allowableValues = {"01", "02", "11", "21", "22", "31", "41", "99"})
		private String prgrsSttsCd = Constants.PRGRS_STTS_CD.RECEIPT.getCode();
	}
	/**
	 * 마약류 폐기 관리 상세 request
	 */
	// @Schema(name = "DsuseMgtDtl", description = "마약류 폐기 관리 상세 DTO")
	// @Data
	// @NoArgsConstructor
	// @AllArgsConstructor
	// @SuperBuilder
	// @EqualsAndHashCode(callSuper = false)
	// public static class DsuseMgtDtl extends NimsApiDto.DsuseRptInfoDtl {
	// 	@Schema(requiredMode = AUTO, title = "폐기 관리 ID", example = " ")
	// 	private String dscdmngId;
	//
	// 	@JsonIgnore
	// 	@Builder.Default
	// 	private String useYn = "Y";
	//
	// 	@JsonIgnore
	// 	private String regDt;
	//
	// 	@JsonIgnore
	// 	@Setter
	// 	private String rgtr;
	//
	// 	@JsonIgnore
	// 	private String mdfcnDt;
	//
	// 	@JsonIgnore
	// 	@Setter
	// 	private String mdfr;
	// }
}
