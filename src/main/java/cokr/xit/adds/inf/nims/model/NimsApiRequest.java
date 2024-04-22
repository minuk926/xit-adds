package cokr.xit.adds.inf.nims.model;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.StringUtils;

import cokr.xit.adds.core.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * description :
 *
 * packageName : cokr.xit.adds.inf.nims.model
 * fileName    : NimsApiRequest
 * author      : limju
 * date        : 2024-03-21
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-03-21    limju       최초 생성
 *
 * </pre>
 */

public class NimsApiRequest {
    /**
     * 마약류 취급자 정보 조회 request
     */
    @Schema(name = "BsshInfoRequest", description = "마약류 취급자 정보 조회 request")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BsshInfoRequest {

        /**
         * 인증키
         */
        @Schema(requiredMode = REQUIRED, title = "인증키", description = "인증키", example = "998003699950fa950b798c9edea1b38f3cfbcf3b77e03e419")
        @NotEmpty(message = "인증키는 필수 입니다")
        String k;

        /**
         * 조회범위
         * 1-전체, 2-내거래처
         */
        @Schema(requiredMode = REQUIRED, title = "조회 범위(1-전체, 2-내거래처)", description = "조회 범위(1-전체, 2-내거래처)", example = "1", allowableValues = {"1", "2"})
        @Pattern(regexp = "[12]", message = "조회 범위는 1 또는 2 입니다(1-전체, 2-내거래처)")
        String fg;

        /**
         * 조회 페이지
         */
        @Schema(requiredMode = REQUIRED, title = "조회 페이지", description = "조회 페이지", example = "1")
        @Pattern(regexp = "[0-9]{1,}", message = "조회 페이지는 필수 입니다")
        String pg;

        /**
         * 사업자 등록 번호
         */
        @Schema(title = "사업자등록번호", description = "사업자등록번호", example = " ")
        @Builder.Default
        String bi = StringUtils.EMPTY;

        /**
         * 요양기관번호
         */
        @Schema(title = "요양기관번호", description = "요양기관번호", example = " ")
        @Builder.Default
        String hp = StringUtils.EMPTY;

        /**
         * 업체명(like 검색)
         */
        @Schema(title = "업체명", description = "업체명", example = " ")
        @Builder.Default
        String bn = StringUtils.EMPTY;

        /**
         * 취급자식별번호
         * 보고자의 마약류취급자 식별번호
         */
        @Schema(title = "취급자식별번호", description = "취급자식별번호", example = " ")
        @Builder.Default
        String bc = StringUtils.EMPTY;

        /**
         * 기준일자 이후
         * yyyyMMdd
         */
        @Schema(title = "기준일자(yyyyMMdd-이후일자)", description = "조회기준일자(yyyyMMdd-이후일자)", example = " ")
        @Pattern(regexp = "^$|" + Constants.DATE_REGX, message = "기준일자는 8자리 입니다(yyyyMMdd)")
        @Builder.Default
        String ymd = StringUtils.EMPTY;

        /**
         * 조회범위2
         * 1:NK(취급승인)포함 - default
         * 2:NK(취급승인)제외
         */
        @Schema(requiredMode = REQUIRED, title = "조회범위2(1:NK(취급승인)포함 - default, 2:NK(취급승인)제외)", description = "조회범위2(1:NK(취급승인)포함 - default, 2:NK(취급승인)제외)", example = "1", allowableValues = {"1", "2"})
        @Builder.Default
        String fg2 = "1";
    }

    /**
     * 상품 정보 조회 request
     */
    @Schema(name = "ProductInfoRequest", description = "상품 정보 조회 request")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProductInfoRequest {
        /**
         * 인증키
         */
        @Schema(requiredMode = REQUIRED, title = "인증키", description = "인증키", example = "998003699950fa950b798c9edea1b38f3cfbcf3b77e03e419")
        @NotEmpty(message = "인증키는 필수 입니다")
        String k;

        /**
         * 조회범위
         * 1-전체, 2-내거래품목, 3-청구코드매핑
         */
        @Schema(requiredMode = REQUIRED, title = "조회범위(1-전체, 2-내거래처 품목, 3-청구코드 매핑)", description = "조회범위(1-전체, 2-내거래처 품목, 3-청구코드 매핑)", example = "1", allowableValues = {"1", "2", "3"})
        @Pattern(regexp = "[1-3]", message = "조회범위는 필수 입니다(1-전체, 2-내거래처 품목, 3-청구코드 매핑)")
        String fg;

        /**
         * 조회 페이지
         */
        @Schema(requiredMode = REQUIRED, title = "조회 페이지", description = "조회 페이지", example = "1")
        @Pattern(regexp = "[0-9]{1,}", message = "조회 페이지는 필수 입니다")
        String pg;

        /**
         * 기준일자 이후
         * yyyyMMdd
         */
        @Schema(title = "기준일자(yyyyMMdd-이후일자)", description = "기준일자(yyyyMMdd-이후일자)", example = " ")
        @Pattern(regexp = "^$|"+Constants.DATE_REGX, message = "기준 일자는 8자리 입니다(yyyyMMdd)")
        @Builder.Default
        String ymd = StringUtils.EMPTY;

        /**
         * 중점/일반 구분
         * 1:중점
         * 2:일반
         */
        @Schema(title = "중점|일반 구분(all, 1:중점, 2:일반)", description = "중점|일반 구분(all, 1:중점, 2:일반)", example = " ", allowableValues = {"", "1", "2"})
        @Pattern(regexp = "^$|[12]", message = "중점|일반 구분은 1 또는 2 입니다(1:중점, 2:일반)")
        @Builder.Default
        String fg2 = StringUtils.EMPTY;

        /**
         * 제품코드
         * 제품코드(like 검색)
         * 조회범위(pg)가 3인 경우 청구 코드
         */
        @Schema(title = "제품코드", description = "제품코드", example = " ")
        @Pattern(regexp = "^$|[0-9a-zA-Z]{13}", message = "제품코드는 13자리 입니다")
        @Builder.Default
        String p = StringUtils.EMPTY;

        /**
         * 제품명(like 검색)
         */
        @Schema(title = "제품명", description = "제품명", example = " ")
        @Builder.Default
        String pn = StringUtils.EMPTY;
    }

    @Schema(name = "DsuseRptInfoRequest", description = "폐기 보고 정보 조회 request")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DsuseRptInfoRequest {
        /**
         * 인증키
         */
        @Schema(requiredMode = REQUIRED, title = "인증키", description = "인증키", example = "998003699950fa950b798c9edea1b38f3cfbcf3b77e03e419")
        @NotEmpty(message = "인증키는 필수 입니다")
        String k;

        /**
         * 조회범위
         * 1-취소제외(신규, 변경), 2-취소포함(신규,변경,취소)
         */
        @Schema(requiredMode = REQUIRED, title = "조회범위(1-취소제외(신규,변경), 2-취소포함)", description = "조회범위(1-취소제외(신규,변경), 2-취소포함)", example = "1", allowableValues = {"1", "2"})
        @Pattern(regexp = "[12]", message = "조회범위는 필수 입니다(1-취소제외(신규,변경), 2-취소포함)")
        @Builder.Default
        String fg = "1";

        /**
         * 조회 페이지
         */
        @Schema(requiredMode = REQUIRED, title = "조회 페이지", description = "조회 페이지", example = "1")
        @Pattern(regexp = "[0-9]{1,}", message = "조회 페이지는 필수 입니다")
        String pg;

        /**
         * 상태
         * 1:정상
         * 2:전체(변경, 취소 포함)
         */
        @Schema(title = "상태(1:정상, 2:전체(정상,변경,취소))", description = "상태(1:정상, 2:전체(정상,변경,취소))", example = "1", allowableValues = {"1", "2"})
        @Pattern(regexp = "[12]", message = "상태는 필수 입니다(상태(1:정상, 2:전체(정상,변경,취소))")
        @Builder.Default
        String fg2 = "1";

        /**
         * 보고구분코드
         * AAR - 폐기보고
         */
        @Schema(title = "보고 구분 코드(AAR-폐기보고)", description = "보고 구분 코드(AAR-폐기보고)", example = "AAR")
        @Pattern(regexp = "[A-Z]{3}", message = "보고 구분 코드는 필수 입니다(AAR-폐기보고)")
        @Builder.Default
        String se = "AAR";

        /**
         * 조회 기준 일자
         * 1:보고 일자
         * 2:취급 일자
         */
        @Schema(title = "조회 기준 일자(1:보고 일자, 2:취급 일자)", description = "조회 기준 일자(1:보고 일자, 2:취급 일자)", example = "1", allowableValues = {"1", "2"})
        @Pattern(regexp = "[12]", message = "조회 기준 일자는 필수 입니다(1:보고 일자, 2:취급 일자)")
        @Builder.Default
        String fg3 = "1";

        /**
         * 조회 시작일(yyyyMMdd)
         * 최대 1개월
         */
        @Schema(title = "조회 시작일(yyyyMMdd)", description = "조회 시작일(yyyyMMdd)", example = "20240101")
        @Pattern(regexp = Constants.DATE_REGX, message = "조회 시작일은 필수 입니다(yyyyMMdd)")
        String sdt;

        /**
         * 조회 종료일(yyyyMMdd)
         * 최대 1개월
         */
        @Schema(title = "조회 종료일(yyyyMMdd)", description = "조회 종료일(yyyyMMdd)", example = "20240131")
        @Pattern(regexp = Constants.DATE_REGX, message = "조회 종료일은 필수 입니다(yyyyMMdd)")
        String edt;

        /**
         * 마약류 취급자 식별 번호
         */
        @Schema(title = "마약류 취급자 식별 번호", description = "마약류 취급자 식별 번호", example = " ")
        String bc;

        /**
         * 업체명
         */
        @Schema(title = "마약류 취급자 업체명", description = "마약류 취급자 업체명", example = " ")
        String bn;

        /**
         * 사용자 보고 식별 번호
         */
        @Schema(title = "사용자 보고 식별 번호", description = "사용자 보고 식별 번호", example = " ")
        String ur;

        /**
         * FIXME : 속성명 및 상태값 확정 필요
         * 폐기 보고 진행 상태
         * 0: 전체, 1: 미처리, 2: 처리중, 3: 완료
         */
        @Schema(title = "폐기 보고 진행 상태", description = "폐기 보고 진행 상태", example = " ", allowableValues = {"0", "1", "2", "3"})
        String ps;

    }

        /**
     * 제품 일련 번호 정보 조회 request
     */
    @Schema(name = "MnfSeqInfoRequest", description = "제품 일련 번호 정보 조회 request")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MnfSeqInfoRequest {
        /**
         * 인증키
         */
        @Schema(requiredMode = REQUIRED, title = "인증키", description = "인증키", example = "998003699950fa950b798c9edea1b38f3cfbcf3b77e03e419")
        @NotEmpty(message = "인증키는 필수 입니다")
        String k;

        /**
         * 조회범위 : 실제는 동일
         * 1-제조번호, 2-일련번호, 3-바코드/RFID
         */
        @Schema(requiredMode = REQUIRED, title = "제조번호 또는 일련번호 구분", description = "제조번호 또는 일련번호 구분(1-제조번호, 2-일련번호, 3-바코드/RFID)", example = "1", allowableValues = {"1", "2", "3"})
        @Pattern(regexp = "[1-3]", message = "제조번호 또는 일련번호 구분은 필수 입니다(1-제조번호, 2-일련번호, 3-바코드/RFID)")
        String fg;

        /**
         * 조회 페이지
         */
        @Schema(requiredMode = REQUIRED, title = "조회 페이지", description = "조회 페이지", example = "1")
        @Pattern(regexp = "[0-9]{1,}", message = "조회 페이지는 필수 입니다")
        String pg;

        /**
         * 제품코드
         * 제품코드(like 검색)
         * 조회범위(pg)가 3인 경우 청구 코드
         */
        @Schema(requiredMode = REQUIRED, title = "제품코드", description = "제품코드", example = "8806718050823")
        @Pattern(regexp = "[0-9]{13}", message = "제품코드는 필수 입니다(13자리)")
        String p;


        /**
         * 기준일자 이후
         * yyyyMMdd
         */
        @Schema(title = "기준일자(yyyyMMdd-이후일자)", description = "기준일자(yyyyMMdd-이후일자)", example = " ")
        @Pattern(regexp = "^$|"+Constants.DATE_REGX, message = "기준 일자는 8자리 입니다(yyyyMMdd)")
        @Builder.Default
        String ymd = StringUtils.EMPTY;

        /**
         * 제품코드 : like 검색 - 오류 -> 사용하지 말것
         */
        @Schema(title = "번호일부 검색", description = "번호 일부 검색(like)", example = " ")
        @Pattern(regexp = "^$|[0-9]{5,}", message = "제품코드를 5자이상 입력해주세요(제품코드는 13자리)")
        @Builder.Default
        String t = StringUtils.EMPTY;
    }

    /**
     * 관할 허가 관청 정보 조회 request
     */
    @Schema(name = "JurisdictionGovInfoRequest", description = "관할 허가 관청 정보 조회 request")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class JurisdictionGovInfoRequest {
        /**
         * 인증키
         */
        @Schema(requiredMode = REQUIRED, title = "인증키", description = "인증키", example = "998003699950fa950b798c9edea1b38f3cfbcf3b77e03e419")
        @NotEmpty(message = "인증키는 필수 입니다")
        String k;

        /**
         * 조회범위
         * 1-전체
         */
        @Schema(requiredMode = REQUIRED, title = "조회범위", description = "조회범위(1-전체)", allowableValues = {"1"})
        @Pattern(regexp = "1", message = "조회 범위는 필수 입니다(1-전체)")
        String fg;

        /**
         * 조회 페이지
         */
        @Schema(requiredMode = REQUIRED, title = "조회 페이지", description = "조회 페이지", example = "1")
        @Pattern(regexp = "[0-9]{1,}", message = "조회 페이지는 필수 입니다")
        String pg;

        /**
         * 기관명
         */
        @Schema(title = "기관명", description = "기관명", example = " ")
        @Builder.Default
        String onm = StringUtils.EMPTY;

        /**
         * 기관 코드
         */
        @Schema(title = "기관 코드", description = "기관 코드", example = " ")
        @Builder.Default
        String ocd = StringUtils.EMPTY;

        /**
         * 주소
         */
        @Schema(title = "주소", description = "주소", example = " ")
        @Builder.Default
        String adr = StringUtils.EMPTY;
    }

    /**
     * 저장소 정보 조회 request
     */
    @Schema(name = "StorageInfoRequest", description = "저장소 정보 조회 request")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class StorageInfoRequest {
        /**
         * 인증키
         */
        @Schema(requiredMode = REQUIRED, title = "인증키", description = "인증키", example = "998003699950fa950b798c9edea1b38f3cfbcf3b77e03e419")
        @NotEmpty(message = "인증키는 필수 입니다")
        String k;

        /**
         * 조회범위
         * 1-특정 업체, 2-전체
         */
        @Schema(requiredMode = REQUIRED, title = "조회범위", description = "조회범위(1-특정업체, 2-전체)", allowableValues = {"1", "2"})
        @Pattern(regexp = "[12]", message = "조회 범위는 필수 입니다(1-특정업체, 2-전체)")
        String fg;

        /**
         * 조회 페이지
         */
        @Schema(requiredMode = REQUIRED, title = "조회 페이지", description = "조회 페이지", example = "1")
        @Pattern(regexp = "[0-9]{1,}", message = "조회 페이지는 필수 입니다")
        String pg;

        /**
         * 취급자식별번호
         * 조회범위가 1-특정업체인 경우 필수
         */
        @Schema(title = "취급자식별번호", description = "취급자식별번호", example = " ")
        @Builder.Default
        String bc = StringUtils.EMPTY;

        /**
         * 기준일자 이후
         * yyyyMMdd
         */
        @Schema(title = "기준일자(yyyyMMdd-이후일자)", description = "기준일자(yyyyMMdd-이후일자)", example = " ")
        @Pattern(regexp = "^$|"+Constants.DATE_REGX, message = "기준 일자는 8자리 입니다(yyyyMMdd)")
        @Builder.Default
        String ymd = StringUtils.EMPTY;
    }
}
