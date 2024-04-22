package cokr.xit.adds.inf.nims.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import cokr.xit.adds.core.model.AuditDto;
import cokr.xit.adds.core.spring.exception.ApiCustomException;
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
 * packageName : cokr.xit.adds.inf.nims.model
 * fileName    : NimsApiDto
 * author      : limju
 * date        : 2024-03-21
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-03-21    limju       최초 생성
 *
 * </pre>
 */
public class NimsApiDto {

    /**
     * 마약류 취급자 정보 조회 response
     */
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @SuperBuilder
    public static class BsshInfoSt extends AuditDto {
        /**
         * 마약류취급자식별번호
         */
        @JsonProperty(value = "BSSH_CD", required = true)
        private String bsshCd;

        /**
         * 업체명
         */
        @JsonProperty(value = "BSSH_NM", required = true)
        private String bsshNm;

        /**
         * 업종명
         */
        @JsonProperty(value = "INDUTY_NM", required = true)
        private String indutyNm;

        /**
         * 의료업자구분
         */
        @JsonProperty(value = "HDNT_CD", required = true)
        private String hdntCd;

        /**
         * 의료업자구분명
         */
        @JsonProperty(value = "HDNT_NM", required = true)
        private String hdntNm;

        /**
         * 사업자등록번호
         */
        @JsonProperty(value = "BIZRNO", required = true)
        private String bizrno;

        /**
         * 대표자명
         */
        @JsonProperty(value = "RPRSNTV_NM", required = true)
        private String rprsntvNm;

        /**
         * 담당자명
         */
        @JsonProperty(value = "CHRG_NM", required = true)
        private String chrgNm;

        /**
         * 요양기관번호
         */
        @JsonProperty(value = "HPTL_NO", required = true)
        private String hptlNo;

        /**
         * 회원가입여부 가입|미가입|탈퇴
         */
        @JsonProperty(value = "JOIN_YN", required = true)
        private String joinYn;

        /**
         * 상태
         */
        @JsonProperty(value = "BSSH_STTUS_NM", required = true)
        private String bsshSttusNm;

        /**
         * 허가번호
         */
        @JsonProperty(value = "PRMISN_NO", required = true)
        private String prmisnNo;
    }

    /**
     * 상품 정보 조회 response
     */
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @SuperBuilder
    public static class ProductInfoKd extends AuditDto {
        /**
         * 품목코드
         */
        @JsonProperty(value = "PRDUCT_CD", required = true)
        private String prductCd;

        /**
         * 제품대표코드
         */
        @JsonProperty(value = "PRDLST_MST_CD")
        private String prdlstMstCd;

        /**
         * 제품명
         */
        @JsonProperty(value = "PRDUCT_NM", required = true)
        private String prductNm;

        /**
         * 마약/항정 구분명
         */
        @JsonProperty(value = "NRCD_SE_NM", required = true)
        private String nrcdSeNm;

        /**
         * 중점/일반 구분
         */
        @JsonProperty(value = "PRTM_SE_NM", required = true)
        private String prtmSeNm;

        /**
         * 제품최소유통단위수량
         * 제품규격정보(고정값 = 1)
         */
        @JsonProperty(value = "PRD_MIN_DISTB_QY", required = true)
        private Integer prdMinDistbQy;

        /**
         * 제품최소유통단위
         */
        @JsonProperty(value = "STD_PACKNG_STLE_NM", required = true)
        private String stdPackngStleNm;

        /**
         * 제품총낱개단위수량
         */
        @JsonProperty(value = "PRD_TOT_PCE_QY", required = true)
        private Integer prdTotPceQy;

        /**
         * 제품낱개단위
         */
        @JsonProperty(value = "PCE_CO_UNIT_NM", required = true)
        private String pceCoUnitNm;

        /**
         * 마약류취급자식별번호
         */
        @JsonProperty(value = "BSSH_CD", required = true)
        private String bsshCd;

        /**
         * 등록일
         */
        @JsonProperty(value = "RGS_DT")
        private String rgsDt;

        /**
         * 변경일
         */
        @JsonProperty(value = "UPD_DT")
        private String updDt;
    }

    /**
     * 폐기보고 response
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @SuperBuilder
    public static class DsuseRpt {

        /**
         * 마약류취급자식별번호
         */
        @JsonProperty(value = "BSSH_CD", required = true)
        private String bsshCd;

        /**
         * 사용자 보고 식별 번호
         */
        @JsonProperty(value = "USR_RPT_ID_NO", required = true)
        private String usrRptIdNo;

        /**
         * 보고 유형 코드(0-신규,1-취소,2-변경)
         */
        @JsonProperty(value = "RPT_TY_CD", required = true)
        private String rptTyCd;

        /**
         * 수불 상세 보고 수
         */
        @JsonProperty(value = "RND_DTL_RPT_CNT", required = true)
        private Integer rndDtlRptCnt;

        /**
         * 취급 일자
         */
        @JsonProperty(value = "HDR_DE", required = true)
        private String hdrDe;

        /**
         * 보고 일자
         */
        @JsonProperty(value = "RPT_DE", required = true)
        private String rptDe;

        /**
         * 폐기 구분 코드
         * 1-보건소폐기, 2-공무원임회, 4-도난/분실/재해 발생 사고마약류
         */
        @JsonProperty(value = "DSUSE_SE_CD", required = true)
        private String dsuseSeCd;

        /**
         * 폐기 사유 코드
         * 01~05, 07~09, 12
         */
        @JsonProperty(value = "DSUSE_PRV_CD", required = true)
        private String dsusePrvCd;

        /**
         * 폐기 방법 코드
         * 1 ~ 9
         */
        @JsonProperty(value = "DSUSE_MTH_CD", required = true)
        private String dsuseMthCd;

        /**
         * 폐기 장소
         */
        @JsonProperty(value = "DSUSE_LOC", required = true)
        private String dsuseLoc;

        /**
         * 폐기 일자
         */
        @JsonProperty(value = "DSUSE_DE", required = true)
        private String dsuseDe;

        /**
         * 처리 상태 코드(0-정상,1-취소,2-변경)
         */
        @JsonProperty(value = "STATUS", required = true)
        private String status;

        /**
         * FIXME: 속성명 확정 필요
         * 보고 진행 상태 코드(0-정상,1-취소,2-변경)
         */
        @JsonProperty(value = "RPT_PRG_STTS_CD", required = true)
        private String rptPgrSttsCd;

        /**
         * 폐기보고상세 목록
         */
        @Builder.Default
        @Valid
        private List<DsuseRptDtl> dsuseRptDtls = new ArrayList<>();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @SuperBuilder
    public static class DsuseRptDtl {
        /**
         * 사용자 보고 라인 식별 번호
         */
        @JsonProperty(value = "USR_RPT_LN_ID_NO", required = true)
        private String usrRptLnIdNo;

        /**
         * 제품 코드
         */
        @JsonProperty(value = "PRDCT_CD", required = true)
        private String prdctCd;

        /**
         * 제품 명
         */
        @JsonProperty(value = "PRDCT_NM", required = true)
        private String prductNm;

        /**
         * 최소 유통 단위 수량
         */
        @JsonProperty(value = "MIN_DISTB_QY", required = true)
        private Integer minDistbQy;

        /**
         * 낱개 단위 수량
         */
        @JsonProperty(value = "PCE_QY", required = true)
        private Integer pceQy;

        /**
         * 제조 번호
         */
        @JsonProperty(value = "MNF_NO", required = true)
        private String mnfNo;

        /**
         * 제품 유효기한 일자
         */
        @JsonProperty(value = "PRD_VALID_DE", required = true)
        private String prdValidDe;

        /**
         * 제조 일련번호
         */
        @JsonProperty(value = "MNF_SEQ")
        private String mnfSeq;

        /**
         * 저장소 번호
         */
        @JsonProperty(value = "STORGE_NO", required = true)
        private String storgeNo;

        /**
         * 저장소명
         */
        @JsonProperty(value = "STORGE_NM", required = true)
        private String storgeNm;

        /**
         * 이동 유형 코드
         * 1102: 재고차감, 1170: 재고미차감
         */
        @JsonProperty(value = "MVMN_TY_CD", required = true)
        private String mvmnTyCd;

        /**
         * 폐기 수량
         */
        @JsonProperty(value = "DSUSE_QY", required = true)
        private Integer dsuseQy;
    }

    /**
     * 제품 제조 일련 번호 정보 조회 response
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @SuperBuilder
    public static class MnfSeqInfo extends AuditDto {
        /**
         * 제품코드
         */
        @JsonProperty(value = "PRDUCT_CD", required = true)
        private String prductCd;

        /**
         * 제품명
         */
        @JsonProperty(value = "PRDUCT_NM", required = true)
        private String prductNm;

        /**
         * 제조번호
         */
        @JsonProperty(value = "MNF_NO", required = true)
        private String mnfNo;

        /**
         * 일련번호
         */
        @JsonProperty(value = "MNF_SEQ", required = true)
        private String mnfSeq;

        /**
         * 유효기간
         */
        @JsonProperty(value = "PRD_VALID_DE", required = true)
        private String prdValidDe;
    }

    /**
     * 관할 허가 관청 정보 조회 response
     */
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @SuperBuilder
    public static class JurisdictionGovInfo extends AuditDto {
        /**
         * 기관코드
         */
        @JsonProperty(value = "OF_CD", required = true)
        private String ofCd;

        /**
         * 기관명
         */
        @JsonProperty(value = "OF_NM", required = true)
        private String ofNm;

        /**
         * 상위 기관명
         */
        @JsonProperty(value = "UP_OF_NM", required = true)
        private String upOfNm;

        /**
         * 최상위 기관명
         */
        @JsonProperty(value = "TOP_OF_NM", required = true)
        private String topOfNm;

        /**
         * 기본 주소
         */
        @JsonProperty(value = "BASS_ADRES", required = true)
        private String bassAdres;

        /**
         * 상세 주소
         */
        @JsonProperty(value = "BASS_DTL_ADRES", required = true)
        private String bassDtlAdres;
    }

    /**
     * 저장소 정보 조회 response
     */
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @SuperBuilder
    public static class StorageInfo extends AuditDto {
        /**
         * 마약류취급자식별번호
         */
        @JsonProperty(value = "BSSH_CD", required = true)
        private String bsshCd;

        /**
         * 업체명
         */
        @JsonProperty(value = "BSSH_NM", required = true)
        private String bsshNm;

        /**
         * 저장소 번호
         */
        @JsonProperty(value = "STORGE_NO", required = true)
        private String storgeNo;

        /**
         * 저장소명
         */
        @JsonProperty(value = "STORGE_NM", required = true)
        private String storgeNm;

        /**
         * 저장소 유형
         */
        @JsonProperty(value = "STORGE_SE_NM", required = true)
        private String storgeSeNm;

        /**
         * 기본 주소
         */
        @JsonProperty(value = "BASS_ADRES", required = true)
        private String bassAdres;

        /**
         * 상세 주소
         */
        @JsonProperty(value = "BASS_DTL_ADRES", required = true)
        private String bassDtlAdres;

        /**
         * 사용 유무
         */
        @JsonProperty(value = "USE_AT", required = true)
        private String useAt;
    }

    /**
     * <pre>
     * 제품 제조 일련 번호 정보 조회 response Serializer
     * 제품 제조 일련 번호 정보 조회시 (json "PRODUCT_CD" -> "prductCd") 직렬화를 위한 Serializer
     * @uses @JsonSerialize(using = MnfSeqInfoSerializer.class)
     * </pre>
     */
    public static class MnfSeqInfoSerializer extends JsonSerializer<List<MnfSeqInfo>> {
        @Override
        public void serialize(List<NimsApiDto.MnfSeqInfo> mnfSeqInfos, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) {

            try {
                jsonGenerator.writeStartArray();
                for(MnfSeqInfo mnfSeqInfo : mnfSeqInfos) {
                    jsonGenerator.writeStartObject();
                    jsonGenerator.writeStringField("prductCd", mnfSeqInfo.getPrductCd());
                    jsonGenerator.writeStringField("prductNm", mnfSeqInfo.getPrductNm());
                    jsonGenerator.writeStringField("mnfNo", mnfSeqInfo.getMnfNo());
                    jsonGenerator.writeStringField("mnfSeq", mnfSeqInfo.getMnfSeq());
                    jsonGenerator.writeStringField("prdValidDe", mnfSeqInfo.getPrdValidDe());
                    jsonGenerator.writeEndObject();
                }
                jsonGenerator.writeEndArray();
            } catch (IOException e) {
                throw ApiCustomException.create("NIMS 제조 일련 번호 조회 API 호출 결과 직렬화 중 오류가 발생 하였습니다.");
            }
        }
    }
}

