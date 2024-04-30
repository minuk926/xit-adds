package cokr.xit.adds.inf.nims.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonAlias;

import cokr.xit.adds.core.model.AuditDto;
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
        @JsonAlias("BSSH_CD")
        private String bsshCd;

        /**
         * 업체명
         */
        @JsonAlias(value = "BSSH_NM")
        private String bsshNm;

        /**
         * 업종명
         */
        @JsonAlias("INDUTY_NM")
        private String indutyNm;

        /**
         * 의료업자구분
         */
        @JsonAlias("HDNT_CD")
        private String hdntCd;

        /**
         * 의료업자구분명
         */
        @JsonAlias("HDNT_NM")
        private String hdntNm;

        /**
         * 사업자등록번호
         */
        @JsonAlias("BIZRNO")
        private String bizrno;

        /**
         * 대표자명
         */
        @JsonAlias("RPRSNTV_NM")
        private String rprsntvNm;

        /**
         * 담당자명
         */
        @JsonAlias("CHRG_NM")
        private String chrgNm;

        /**
         * 요양기관번호
         */
        @JsonAlias("HPTL_NO")
        private String hptlNo;

        /**
         * 회원가입여부 가입|미가입|탈퇴
         */
        @JsonAlias("JOIN_YN")
        private String joinYn;

        /**
         * 상태
         */
        @JsonAlias("BSSH_STTUS_NM")
        private String bsshSttusNm;

        /**
         * 허가번호
         */
        @JsonAlias("PRMISN_NO")
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
        @JsonAlias("PRDUCT_CD")
        private String prductCd;

        /**
         * 제품대표코드
         */
        @JsonAlias("PRDLST_MST_CD")
        private String prdlstMstCd;

        /**
         * 제품명
         */
        @JsonAlias("PRDUCT_NM")
        private String prductNm;

        /**
         * 마약/항정 구분명
         */
        @JsonAlias("NRCD_SE_NM")
        private String nrcdSeNm;

        /**
         * 중점/일반 구분
         */
        @JsonAlias("PRTM_SE_NM")
        private String prtmSeNm;

        /**
         * 제품최소유통단위수량
         * 제품규격정보(고정값 = 1)
         */
        @JsonAlias("PRD_MIN_DISTB_QY")
        private Integer prdMinDistbQy;

        /**
         * 제품최소유통단위
         */
        @JsonAlias("STD_PACKNG_STLE_NM")
        private String stdPackngStleNm;

        /**
         * 제품총낱개단위수량
         */
        @JsonAlias("PRD_TOT_PCE_QY")
        private Integer prdTotPceQy;

        /**
         * 제품낱개단위
         */
        @JsonAlias("PCE_CO_UNIT_NM")
        private String pceCoUnitNm;

        /**
         * 마약류취급자식별번호
         */
        @JsonAlias("BSSH_CD")
        private String bsshCd;

        /**
         * 등록일
         */
        @JsonAlias("RGS_DT")
        private String rgsDt;

        /**
         * 변경일
         */
        @JsonAlias("UPD_DT")
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
    public static class DsuseRptInfo extends AuditDto {

        /**
         * 사용자 보고 식별 번호
         */
        @JsonAlias("USR_RPT_ID_NO")
        private String usrRptIdNo;

        /**
         * 참조 사용자 보고 식별 번호
         * 취소|변경시 필수 - 사용자 보고 식별 번호
         */
        @JsonAlias("REF_USR_RPT_ID_NO")
        private String refUsrRptIdNo;

        /**
         * 마약류취급자식별번호
         */
        @JsonAlias("BSSH_CD")
        private String bsshCd;

        /**
         * 마약류취급자명업체명
         */
        @JsonAlias("BSSH_NM")
        private String bsshNm;

        /**
         * 업종명
         */
        @JsonAlias("INDUTY_NM")
        private String indutyNm;

        /**
         * 보고 유형 코드(0-신규,1-취소,2-변경)
         */
        @JsonAlias("RPT_TY_CD")
        private String rptTyCd;

        private String rptTyCdNm;

        /**
         * 수불 상세 보고 수
         */
        @JsonAlias("RND_DTL_RPT_CNT")
        private Integer rndDtlRptCnt;

        /**
         * 취급 일자
         */
        @JsonAlias("HDR_DE")
        private String hdrDe;

        /**
         * 보고 일자
         */
        @JsonAlias("RPT_DE")
        private String rptDe;

        /**
         * 폐기 구분 코드
         * 1-보건소폐기, 2-공무원임회, 4-도난/분실/재해 발생 사고마약류
         */
        @JsonAlias("DSUSE_SE_CD")
        private String dsuseSeCd;

        private String dsuseSeCdNm;

        /**
         * 폐기 사유 코드
         * 01~05, 07~09, 12
         */
        @JsonAlias("DSUSE_PRV_CD")
        private String dsusePrvCd;

        private String dsusePrvCdNm;

        /**
         * 폐기 방법 코드
         * 1 ~ 9
         */
        @JsonAlias("DSUSE_MTH_CD")
        private String dsuseMthCd;

        private String dsuseMthCdNm;

        /**
         * 폐기 장소
         */
        @JsonAlias("DSUSE_LOC")
        private String dsuseLoc;

        /**
         * 폐기 일자
         */
        @JsonAlias("DSUSE_DE")
        private String dsuseDe;

        /**
         * 처리 상태 코드(0-정상,1-취소,2-변경)
         */
        @JsonAlias("STATUS")
        private String status;

        /**
         * FIXME: 속성명 확정 필요
         * 보고 진행 상태 코드(0-정상,1-취소,2-변경)
         */
        @JsonAlias("RPT_PRG_STTS_CD")
        private String rptPrgSttsCd;

        /**
         * 원사용자보고식별번호
         */
        private String orgUsrRptIdNo;

        /**
         * 폐기보고상세 목록
         */
        @Builder.Default
        @Valid
        private List<DsuseRptInfoDtl> dsuseRptInfoDtls = new ArrayList<>();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @SuperBuilder
    public static class DsuseRptInfoDtl extends AuditDto {
        /**
         * 사용자 보고 식별 번호
         */
        @JsonAlias("USR_RPT_ID_NO")
        private String usrRptIdNo;

        /**
         * 사용자 보고 라인 식별 번호
         */
        @JsonAlias("USR_RPT_LN_ID_NO")
        private String usrRptLnIdNo;

        /**
         * 제품 코드
         */
        @JsonAlias("PRDUCT_CD")
        private String prductCd;

        /**
         * 제품 명
         */
        @JsonAlias("PRDCT_NM")
        private String prductNm;

        /**
         * 최소 유통 단위 수량
         */
        @JsonAlias("MIN_DISTB_QY")
        private Integer minDistbQy;

        /**
         * 낱개 단위 수량
         */
        @JsonAlias("PCE_QY")
        private Integer pceQy;

        /**
         * 제조 번호
         */
        @JsonAlias("MNF_NO")
        private String mnfNo;

        /**
         * 제품 유효기한 일자
         */
        @JsonAlias("PRD_VALID_DE")
        private String prdValidDe;

        /**
         * 제조 일련번호
         */
        @JsonAlias("MNF_SEQ")
        private String mnfSeq;

        /**
         * 이동 유형 코드
         * 1102: 재고차감, 1170: 재고미차감
         */
        @JsonAlias("MVMN_TY_CD")
        private String mvmnTyCd;

        /**
         * 폐기 수량
         */
        @JsonAlias("DSUSE_QY")
        private Integer dsuseQy;

        /**
         * 마약/항정 구분
         */
        private String nrcdSeNm;

        /**
         * 중점/일반 구분
         */
        private String prtmSeNm;

        /**
         * 제조 수입자
         */
        private String bsshCd;

        /**
         * 제조 수입자명
         */
        private String bsshNm;
    }

    // /**
    //  * 제품 제조 일련 번호 정보 조회 response
    //  */
    // @Getter
    // @Setter
    // @NoArgsConstructor
    // @AllArgsConstructor
    // @SuperBuilder
    // public static class MnfSeqInfo extends AuditDto {
    //     /**
    //      * 제품코드
    //      */
    //     @JsonAlias("PRDUCT_CD")
    //     private String prductCd;
    //
    //     /**
    //      * 제품명
    //      */
    //     @JsonAlias("PRDUCT_NM")
    //     private String prductNm;
    //
    //     /**
    //      * 제조번호
    //      */
    //     @JsonAlias("MNF_NO")
    //     private String mnfNo;
    //
    //     /**
    //      * 일련번호
    //      */
    //     @JsonAlias("MNF_SEQ")
    //     private String mnfSeq;
    //
    //     /**
    //      * 유효기간
    //      */
    //     @JsonAlias("PRD_VALID_DE")
    //     private String prdValidDe;
    // }
    //
    // /**
    //  * 관할 허가 관청 정보 조회 response
    //  */
    // @Getter
    // @NoArgsConstructor
    // @AllArgsConstructor
    // @SuperBuilder
    // public static class JurisdictionGovInfo extends AuditDto {
    //     /**
    //      * 기관코드
    //      */
    //     @JsonAlias("OF_CD")
    //     private String ofCd;
    //
    //     /**
    //      * 기관명
    //      */
    //     @JsonAlias("OF_NM")
    //     private String ofNm;
    //
    //     /**
    //      * 상위 기관명
    //      */
    //     @JsonAlias("UP_OF_NM")
    //     private String upOfNm;
    //
    //     /**
    //      * 최상위 기관명
    //      */
    //     @JsonAlias("TOP_OF_NM")
    //     private String topOfNm;
    //
    //     /**
    //      * 기본 주소
    //      */
    //     @JsonAlias("BASS_ADRES")
    //     private String bassAdres;
    //
    //     /**
    //      * 상세 주소
    //      */
    //     @JsonAlias("BASS_DTL_ADRES")
    //     private String bassDtlAdres;
    // }
    //
    // /**
    //  * 저장소 정보 조회 response
    //  */
    // @Getter
    // @NoArgsConstructor
    // @AllArgsConstructor
    // @SuperBuilder
    // public static class StorageInfo extends AuditDto {
    //     /**
    //      * 마약류취급자식별번호
    //      */
    //     @JsonAlias("BSSH_CD")
    //     private String bsshCd;
    //
    //     /**
    //      * 업체명
    //      */
    //     @JsonAlias("BSSH_NM")
    //     private String bsshNm;
    //
    //     /**
    //      * 저장소 번호
    //      */
    //     @JsonAlias("STORGE_NO")
    //     private String storgeNo;
    //
    //     /**
    //      * 저장소명
    //      */
    //     @JsonAlias("STORGE_NM")
    //     private String storgeNm;
    //
    //     /**
    //      * 저장소 유형
    //      */
    //     @JsonAlias("STORGE_SE_NM")
    //     private String storgeSeNm;
    //
    //     /**
    //      * 기본 주소
    //      */
    //     @JsonAlias("BASS_ADRES")
    //     private String bassAdres;
    //
    //     /**
    //      * 상세 주소
    //      */
    //     @JsonAlias("BASS_DTL_ADRES")
    //     private String bassDtlAdres;
    //
    //     /**
    //      * 사용 유무
    //      */
    //     @JsonAlias("USE_AT")
    //     private String useAt;
    // }
    //
    // /**
    //  * <pre>
    //  * 제품 제조 일련 번호 정보 조회 response Serializer
    //  * 제품 제조 일련 번호 정보 조회시 (json "PRODUCT_CD" -> "prductCd") 직렬화를 위한 Serializer
    //  * @uses @JsonSerialize(using = MnfSeqInfoSerializer.class)
    //  * </pre>
    //  */
    // public static class MnfSeqInfoSerializer extends JsonSerializer<List<MnfSeqInfo>> {
    //     @Override
    //     public void serialize(List<NimsApiDto.MnfSeqInfo> mnfSeqInfos, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) {
    //
    //         try {
    //             jsonGenerator.writeStartArray();
    //             for(MnfSeqInfo mnfSeqInfo : mnfSeqInfos) {
    //                 jsonGenerator.writeStartObject();
    //                 jsonGenerator.writeStringField("prductCd", mnfSeqInfo.getPrductCd());
    //                 jsonGenerator.writeStringField("prductNm", mnfSeqInfo.getPrductNm());
    //                 jsonGenerator.writeStringField("mnfNo", mnfSeqInfo.getMnfNo());
    //                 jsonGenerator.writeStringField("mnfSeq", mnfSeqInfo.getMnfSeq());
    //                 jsonGenerator.writeStringField("prdValidDe", mnfSeqInfo.getPrdValidDe());
    //                 jsonGenerator.writeEndObject();
    //             }
    //             jsonGenerator.writeEndArray();
    //         } catch (IOException e) {
    //             throw ApiCustomException.create("NIMS 제조 일련 번호 조회 API 호출 결과 직렬화 중 오류가 발생 하였습니다.");
    //         }
    //     }
    // }
}

