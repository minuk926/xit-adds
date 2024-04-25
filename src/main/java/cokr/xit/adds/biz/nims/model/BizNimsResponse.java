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
}
