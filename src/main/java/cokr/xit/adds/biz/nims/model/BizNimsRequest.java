package cokr.xit.adds.biz.nims.model;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

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
	public static class DsuseMgt extends AuditDto {
		@Schema(requiredMode = AUTO, title = "폐기 관리 ID", example = " ")
		private String dscdmngId;

		@Schema(requiredMode = REQUIRED, title = "마약류 취급자 식별 번호", example = " ")
		@NotEmpty(message = "마약류 취급자 식별 번호는 필수 입니다")
		private String bsshCd;

		@Schema(requiredMode = REQUIRED, title = "사용자 ID", example = " ")
		@NotEmpty(message = "사용자 ID는 필수 입니다")
		private String userId;

		/**
		 * <pre>
		 * 진행상태
		 *
		 * 01-폐기신청서 접수
		 * 02-폐기정보등록
		 * 11-민원수령처리(전자결재)
		 * 21-폐기결과통보서 작성
		 * 22-폐기결과보고서 작성
		 * 31-기안 및 발송
		 * 41-폐기보고
		 * 42-폐기보고확인?
		 * 99-종료
		 * </pre>
		 */
		@Schema(requiredMode = REQUIRED, title = "진행 상태 코드", example = " ", allowableValues = {"01", "02", "11", "22", "31", "41", "42", "99"})
		@Pattern(regexp = "01|02|11|22|31|41|42|99", message = "진행 상태 코드는 필수 입니다")
		private String prgrsSttsCd;

		@Schema(requiredMode = AUTO, title = "보고 접수 번호", example = " ")
		private String rptRceptNo;

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
		@Schema(requiredMode = AUTO, title = "폐기 관리 ID", example = " ")
		private String dscdmngId;

		@Schema(requiredMode = AUTO, title = "폐기 관리 순번", example = " ")
		private String dscdmngSn;

		@Schema(requiredMode = REQUIRED, title = "제품 코드", example = " ")
		@NotEmpty(message = "제품 코드는 필수 입니다")
		private String prdctCd;

		@Schema(requiredMode = REQUIRED, title = "폐기 수량", example = " ")
		@Min(value = 1, message = "폐기 수량은 1 이상 입니다")
		private Integer dsuseQy;

		@Schema(requiredMode = REQUIRED, title = "사고 발생 일", example = " ")
		@Pattern(regexp = Constants.CUR_DATE_REGX, message = "사고 발생 일은 필수 입니다")
		private String acdntOcrnDay;

		@Schema(requiredMode = REQUIRED, title = "사고 발생 보고 일", example = " ")
		@Pattern(regexp = Constants.CUR_DATE_REGX, message = "사고 발생 보고 일은 필수 입니다")
		private String ocrnRptDay;

		/**
		 * <pre>
		 * 사고발생사유(폐기 사유 코드)
		 *
		 * 01-파손, 02-변질,부패, 03-유효기간 또는 사용기한 경과
		 * 04-유효 기간 임박, 05-사용 중단, 07-폐업, 08-환자 반납
		 * 09-기타, 12-제조 공정중 폐기물
		 * </pre>
		 */
		@Schema(requiredMode = AUTO, title = "사고 발생 사유 코드", example = " ")
		@Pattern(regexp = "0[1-57-9]|12", message = "사고 발생 사유 코드는 필수 입니다")
		private String acdntOcrnRsn;
	}
}
