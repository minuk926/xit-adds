package cokr.xit.adds.biz.nims.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cokr.xit.adds.core.Constants;
import cokr.xit.adds.inf.nims.model.NimsApiDto;
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

	/**
	 * <pre>
	 * Barcode(GS1-128) 조회 response
	 * 최소 30자리 이상
	 * AI상품식별코드(2) + 상품코드(14) + AI최대유통일자(2) + 유통일자(6) + AI로트번호(2) + 제조번호(1) + AI일련번호(2) + 제품일련번호(1)
	 * -> 상품코드(확장코드(1) + 상품코드(13)), 제조번호(1), 제품일련번호(1) 은 필수로 포함한다는 가정
	 * </pre>
	 */
	@Schema(name = "Barcode", description = "Barcode response DTO")
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Barcode {
		/**
		 * <pre>
		 * GS1-128 barcode - 01 : AI상품식별코드
		 * 2자리 : 01
		 * </pre>
		 */
		@Builder.Default
		private String gsId = "01";

		/**
		 * <pre>
		 * GS1-128 상품코드
		 * 14자리 - 확장코드(0) + 상품코드(13)
		 * </pre>
		 */
		private String gsPrductCd;

		/**
		 * <pre>
		 * GS1-128 확장코드
		 * 1자리 - 확장코드(0)
		 * </pre>
		 */
		@Builder.Default
		private String gsPrductExtCd = "0";

		/**
		 * <pre>
		 * AI최대유통일자
		 * 2자리 : 17
		 * </pre>
		 */
		@Builder.Default
		private String gsDistributionDel = "17";

		/**
		 * <pre>
		 * 유통일자 : YYMMDD
		 * 6자리
		 * </pre>
		 */
		private String gsDistributionDate;

		/**
		 * <pre>
		 * AI로트번호
		 * 2자리 - 10
		 * </pre>
		 */
		@Builder.Default
		private String gsAiLotNo = "10";

		/**
		 * <pre>
		 * 제조번호(제품의 생산라인 제조번호)
		 * 20자 이하
		 * AI로트번호(10) 이후 ~ AI일련번호(21) 이전
		 * </pre>
		 */
		private String gsMnfNo;

		/**
		 * <pre>
		 * AI일련번호
		 * 2자리 - 21
		 * </pre>
		 */
		@Builder.Default
		private String gsAiSerialNo = "21";

		/**
		 * <pre>
		 * 제품일련번호
		 * AI일련번호 이후 20자 이하
		 * </pre>
		 */
		private String gsMnfSeqNo;

		/**
		 * <pre>
		 * 바코드 reader 사용시 - Barcode(GS1-128) 정보 파싱
		 * 최소 30자리 이상
		 * AI상품식별코드(2) + 상품코드(14) + AI최대유통일자(2) + 유통일자(6) + AI로트번호(2) + 제조번호(1) + AI일련번호(2) + 제품일련번호(1)
		 * -> 상품코드(확장코드(1) + 상품코드(13)), 제조번호(1), 제품일련번호(1) 은 필수로 포함한다는 가정
		 * -> 상품코드, 제조번호, 유효기간, 제조일련번호 정보 추출
		 *
		 * ex) 01088065780457311717050110A1234210000000006
		 * @param barcodeStr
		 * @return NimsApiDto.MnfSeqInfo
		 * </pre>
		 */
		public NimsApiDto.MnfSeqInfo parseBarcode(final String barcodeStr){
			NimsApiDto.MnfSeqInfo mnfSeqInfo = new NimsApiDto.MnfSeqInfo();

			if(barcodeStr.length() < 30){
				throw new IllegalArgumentException("Barcode는 최소 30자리 이상이어야 합니다.");
			}
			// AI상품식별코드(01)로 시작
			if(barcodeStr.startsWith(this.gsId) == false){
				throw new IllegalArgumentException("Barcode(GS1-128)는 AI상품식별코드(01)로 시작 되어야 합니다.");
			}
			String barcode = barcodeStr.substring(barcodeStr.indexOf(this.gsId)+this.gsId.length());

			// 상품코드(확장코드(0) + 상품코드(13))
			String temp = barcode.substring(0, 14);
			String regx = "^0(\\d{13})";
			Pattern pattern = Pattern.compile(regx);
			Matcher matcher = pattern.matcher(temp);
			if(matcher.find()) {
				mnfSeqInfo.setPrductCd(matcher.group(1));
			}else{
				throw new IllegalArgumentException("Barcode(GS1-128) 상품코드는 확장코드(0) + 상품코드(13)로 구성되어야 합니다.");
			}
			barcode = barcode.substring(14);

			// 유통일자 : AI최대유통일자(17) + 6자리 유통일자
			temp = barcode.substring(0, 8);
			regx = "^17(\\d{6})";
			pattern = Pattern.compile(regx);
			matcher = pattern.matcher(temp);
			if(matcher.find()) {
				// 20xx년 유통일자
				mnfSeqInfo.setPrdValidDe("20" + matcher.group(1));
			}else{
				throw new IllegalArgumentException("Barcode(GS1-128) 유통일자는 AI최대유통일자(17) + 유통일자(6)로 구성되어야 합니다.");
			}
			barcode = barcode.substring(8);

			// 제조번호 : AI로트번호(10)로 시작 ~ 20자 이하 제조번호 ~ AI일련번호(21) 이전
			regx = "^10(.{1,20})21";
			pattern = Pattern.compile(regx);
			matcher = pattern.matcher(barcode);
			if(matcher.find()) {
				mnfSeqInfo.setMnfNo(matcher.group(1));
			}else {
				throw new IllegalArgumentException("Barcode(GS1-128) 제조번호는 AI로트번호(10)로 시작 되어 AI일련번호(21)로 끝나야 합니다.");
			}
			barcode = barcode.substring(mnfSeqInfo.getMnfNo().length()+2);

			// 제품일련번호 : AI일련번호(21)로 시작 ~ 20자 이하 제조일련번호
			regx = "^21(.{1,20}$)";
			pattern = Pattern.compile(regx);
			matcher = pattern.matcher(barcode);
			if(matcher.find()) {
				mnfSeqInfo.setMnfSeq(matcher.group(1));
			}else {
				throw new IllegalArgumentException("Barcode(GS1-128) 제조일련번호는 AI일련번호(21)로 시작 되어야 합니다.");
			}
			return mnfSeqInfo;
		}
	}
}
