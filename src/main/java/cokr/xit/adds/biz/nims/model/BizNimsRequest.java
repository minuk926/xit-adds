package cokr.xit.adds.biz.nims.model;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import cokr.xit.adds.core.model.AuditDto;
import cokr.xit.adds.inf.nims.model.NimsApiDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
		@Schema(requiredMode = REQUIRED, title = "진행 상태 코드", example = " ", allowableValues = {"01", "02", "11", "22", "31", "41", "99"})
		@Pattern(regexp = "01|02|11|22|31|41|99", message = "진행 상태 코드는 필수 입니다")
		private String prgrsSttsCd = "01";
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
	public static class DsuseMgtDtl extends NimsApiDto.DsuseRptInfoDtl {
		@Schema(requiredMode = AUTO, title = "폐기 관리 ID", example = " ")
		private String dscdmngId;

		@JsonIgnore
		@Builder.Default
		private String useYn = "Y";

		@JsonIgnore
		private String regDt;

		@JsonIgnore
		@Setter
		private String rgtr;

		@JsonIgnore
		private String mdfcnDt;

		@JsonIgnore
		@Setter
		private String mdfr;
	}
}
