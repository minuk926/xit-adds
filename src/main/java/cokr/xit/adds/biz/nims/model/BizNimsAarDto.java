package cokr.xit.adds.biz.nims.model;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import cokr.xit.adds.core.Constants;
import cokr.xit.adds.core.model.AuditDto;
import cokr.xit.adds.inf.nims.model.NimsApiDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
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
public class BizNimsAarDto {

	/**
	 * 마약류 연계보고 Header request
	 */
	@Schema(name = "AarHeader", description = "마약류 연계보고 Header DTO")
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@SuperBuilder
	public static class AarHeader extends AuditDto {
		@Setter
		@Schema(title = "폐기관리ID", description = "폐기관리ID", example = " ")
		private String dscdmngId;

		@Setter
		@Schema(requiredMode = REQUIRED, title = "보고자식별ID", description = "보고자식별ID", example = " ")
		@NotEmpty(message = "보고자식별ID 필수 입니다")
		private String uid;

		@Setter
		@Schema(requiredMode = REQUIRED, title = "소프트웨어ID", example = " ")
		@NotEmpty(message = "소프트웨어ID 필수 입니다")
		private String swId;

		@Schema(requiredMode = AUTO, title = "수불비고", example = " ")
		private String rndRmk;

		@Schema(requiredMode = REQUIRED, title = "취급일자", example = " ")
		@Pattern(regexp = Constants.DATE_REGX, message = "취급일자 필수 입니다(8자리-yyyyMMdd)")
		private String hdrDe;

		@Schema(requiredMode = REQUIRED, title = "마약류취급자식별번호", example = " ")
		@NotEmpty(message = "마약류 취급자 식별번호는 필수 입니다")
		private String bsshCd;

		@Schema(requiredMode = REQUIRED, title = "보고구분코드", example = "AAR")
		@NotEmpty(message = "보고 구분 코드는 필수 입니다(폐기-AAR)")
		@Builder.Default
		private String rptSeCd = "AAR";

		@Schema(requiredMode = REQUIRED, title = "사용자보고식별번호", example = " ")
		@NotEmpty(message = "사용자 보고 식별번호는 필수 입니다")
		private String usrRptIdNo;

		@Schema(requiredMode = AUTO, title = "참조사용자보고식별번호(변경|취소시 필수)", example = " ")
		//@NotEmpty(message = "사용자 보고 식별번호는 필수 입니다")
		private String refUsrRptIdNo;

		@Schema(requiredMode = REQUIRED, title = "보고유형코드", example = " ", allowableValues = {"0", "1", "2"})
		@Pattern(regexp = "[012]", message = "보고 유형 코드는 필수 입니다(0-신규, 1-취소, 2-변경)")
		private String rptTyCd;

		@Schema(requiredMode = AUTO, title = "비고(변경 및 취소시 필수-사유 기재)", example = " ")
		private String rmk;

		@Schema(requiredMode = REQUIRED, title = "보고자명", example = " ")
		@NotEmpty(message = "보고자 이름은 필수 입니다")
		private String rptrNm;

		@Schema(requiredMode = REQUIRED, title = "보고자업체명", example = " ")
		@NotEmpty(message = "보고자 업체명은 필수 입니다")
		private String rptrEntrpsNm;

		@Schema(requiredMode = REQUIRED, title = "담당자명", example = " ")
		@NotEmpty(message = "담당자 이름은 필수 입니다")
		private String chrgNm;

		@Schema(requiredMode = REQUIRED, title = "담당자 전화번호", example = " ")
		@Pattern(regexp = Constants.TEL_REGX, message = "담당자 전화번호는 필수 입니다")
		private String chrgTelNo;

		@Schema(requiredMode = REQUIRED, title = "담당자 휴대폰 번호", example = " ")
		@Pattern(regexp = Constants.PHONE_REGX, message = "담당자 휴대폰 번호는 필수 입니다")
		private String chrgMpNo;

		@Schema(requiredMode = REQUIRED, title = "수불 상세 보고수", example = " ")
		@NotEmpty(message = "수불 상세 보고수는 필수 입니다")
		private String rndDtlRptCnt;

		@Schema(requiredMode = REQUIRED, title = "폐기 구분 코드", example = " ", allowableValues = {"1", "2", "4"})
		@Pattern(regexp = "[124]", message = "폐기 구분 코드는 필수 입니다(1-보건소폐기, 2-공무원임회, 4-도난/분실/재해 발생 사고마약류)")
		private String dsuseSeCd;

		/**
		 * <pre>
		 * 폐기 사유 코드
		 *
		 * 01-파손, 02-변질,부패, 03-유효기간 또는 사용기한 경과
		 * 04-유효 기간 임박, 05-사용 중단, 07-폐업, 08-환자 반납
		 * 09-기타, 12-제조 공정중 폐기물
		 * </pre>
		 */
		@Schema(requiredMode = REQUIRED, title = "폐기 사유 코드", example = " ", allowableValues = {"01", "02", "03", "04", "05", "07", "08", "09", "12"})
		@Pattern(regexp = "0[1-57-9]|12", message = "폐기 사유 코드는 필수 입니다(01~05, 07~09, 12)")
		private String dsusePrvCd;

		@Schema(requiredMode = REQUIRED, title = "폐기 방법 코드", example = " ", allowableValues = {"1", "2", "3", "4", "5", "6", "7", "8", "9"})
		@Pattern(regexp = "[1-9]", message = "폐기 방법 코드는 필수 입니다(1~9)")
		private String dsuseMthCd;

		@Schema(requiredMode = REQUIRED, title = "폐기 장소", example = " ")
		@NotEmpty(message = "폐기 장소는 필수 입니다")
		private String dsuseLoc;

		@Schema(requiredMode = REQUIRED, title = "폐기 일자", example = " ")
		@Pattern(regexp = Constants.CUR_DATE_REGX, message = "폐기 일자는 필수 입니다(8자리-yyyyMMdd)")
		private String dsuseDe;

		@Setter
		@Schema(requiredMode = REQUIRED, title = "폐기 관할 행정 기관 코드", example = " ")
		@NotEmpty(message = "폐기 관할 행정 기관 코드는 필수 입니다")
		private String dsuseInsttCd;

		@Schema(requiredMode = REQUIRED, title = "첨부 파일 건수", example = " ")
		@Pattern(regexp = "\\d+", message = "첨부 파일 건수 필수 입니다")
		private String atchFileCo;

		@Schema(requiredMode = REQUIRED, title = "등록자ID", example = " ")
		@NotEmpty(message = "등록자 ID는 필수 입니다")
		private String registerId;

		@Schema(requiredMode = REQUIRED, title = "파일 생성 일시", example = " ")
		@Pattern(regexp = Constants.CUR_DTM_REGX, message = "파일 생성 일시는 필수 입니다(14자리-yyyyMMddHHmmss)")
		private String fileCreatDt;

		@Schema(requiredMode = AUTO, title = "첨부파일", example = " ")
		@Builder.Default
		List<String> atchFiles = new ArrayList<>();

		@Schema(requiredMode = REQUIRED)
		@Builder.Default
		@Valid
		List<AarDetail> aarDetails = new ArrayList<>();
	}

	/**
	 * 마약류 연계보고 Detail request
	 */
	@Schema(name = "AarDetail", description = "마약류 연계보고 상세 DTO")
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	//@JsonIgnoreProperties(value = { "mnfSeqInfos" }, allowGetters = true)
	@JsonIgnoreProperties(value = { "prductCd" }, allowGetters = true)
	public static class AarDetail {
		@Schema(requiredMode = REQUIRED, title = "사용자 보고 식별 번호", example = " ")
		@NotEmpty(message = "사용자 보고 식별 번호는 필수 입니다")
		private String usrRptIdNo;

		@Schema(requiredMode = REQUIRED, title = "사용자 보고 라인 식별 번호", example = " ")
		@NotEmpty(message = "사용자 보고 라인 식별 번호는 필수 입니다")
		private String usrRptLnIdNo;

		@Setter
		@Schema(requiredMode = REQUIRED, title = "저장소 번호", example = " ")
		@NotEmpty(message = "저장소 번호는 필수 입니다")
		private String storgeNo;

		@Schema(requiredMode = REQUIRED, title = "이동 유형 코드", example = " ", allowableValues = {"1102", "1170"})
		@Pattern(regexp = "1102|1170", message = "이동 유형 코드는 필수 입니다(1102-폐기출고,1170-폐기재고미차감)")
		private String mvmnTyCd;

		@Schema(requiredMode = REQUIRED, title = "제품 코드", example = " ")
		@NotEmpty(message = "제품 코드는 필수 입니다")
		private String prductCd;

		@Setter
		@Schema(requiredMode = REQUIRED, title = "제조 번호", example = " ")
		@NotEmpty(message = "제조 번호는 필수 입니다")
		private String mnfNo;

		@Setter
		@Schema(requiredMode = AUTO, title = "제품 일련 번호", example = " ")
		private String mnfSeq;

		@Schema(requiredMode = REQUIRED, title = "최소 유통 단위 수량", example = " ")
		@NotEmpty(message = "최소 유통 단위 수량은 필수 입니다")
		private String minDistbQy;

		@Schema(requiredMode = REQUIRED, title = "제품 최소 유통 단위", example = " ")
		@NotEmpty(message = "제품 최소 유통 단위는 필수 입니다")
		private String prdMinDistbUnit;

		@Schema(requiredMode = REQUIRED, title = "낱개 단위 수량", example = " ")
		@NotEmpty(message = "낱개 단위 수량은 필수 입니다")
		private String pceQy;

		@Schema(requiredMode = REQUIRED, title = "제품 낱개 단위", example = " ")
		@NotEmpty(message = "제품 낱개 단위는 필수 입니다")
		private String prdPceUnit;

		@Schema(requiredMode = REQUIRED, title = "제품명", example = " ")
		@NotEmpty(message = "제품명은 필수 입니다")
		private String prductNm;

		@Schema(requiredMode = AUTO, title = "제품 바코드(RFID)", example = " ")
		private String prdSgtin;

		@Schema(requiredMode = REQUIRED, title = "제품 최소 유통 단위 수량", example = " ")
		@NotEmpty(message = "제품 최소 유통 단위 수량은 필수 입니다")
		private String prdMinDistbQy;

		@Schema(requiredMode = REQUIRED, title = "제품 총 낱개 단위 수량", example = " ")
		@NotEmpty(message = "제품 총 낱개 단위 수량 필수 입니다")
		private String prdTotPceQy;

		@Setter
		@Schema(requiredMode = REQUIRED, title = "제품 유효 기간 일자(yyyyMMdd)", example = " ")
		@Pattern(regexp = Constants.DATE_REGX, message = "제품 유효 기간 일자는 필수 입니다(yyyyMMdd)")
		private String prdValidDe;

		@Schema(requiredMode = REQUIRED, title = "파일 생성 일시", example = " ")
		@Pattern(regexp = Constants.CUR_DTM_REGX, message = "파일 생성 일시는 필수 입니다(yyyyMMddHHmmss)")
		private String fileCreatDt;

		@Schema(title = "제조 일련 번호 정보 목록", example = " ")
		@Builder.Default
		@JsonSerialize(using = NimsApiDto.MnfSeqInfoSerializer.class)
		@Setter
		private List<NimsApiDto.MnfSeqInfo> mnfSeqInfos = new ArrayList<>();
	}
}
