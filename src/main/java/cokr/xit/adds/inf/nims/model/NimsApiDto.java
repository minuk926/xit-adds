package cokr.xit.adds.inf.nims.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import cokr.xit.adds.biz.nims.model.BizNimsRequest;
import cokr.xit.adds.biz.nims.model.BizNimsResponse;
import cokr.xit.adds.core.Constants;
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

        @JsonSerialize(using = NimsApiDto.MnfSeqInfoSerializer.class)
        @Setter
        private List<NimsApiDto.MnfSeqInfo> mnfSeqInfos = new ArrayList<>();
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
        @JsonAlias("PRDUCT_CD")
        private String prductCd;

        /**
         * 제품명
         */
        @JsonAlias("PRDUCT_NM")
        private String prductNm;

        /**
         * 제조번호
         */
        @JsonAlias("MNF_NO")
        private String mnfNo;

        /**
         * 일련번호
         */
        @JsonAlias("MNF_SEQ")
        private String mnfSeq;

        /**
         * 유효기간
         */
        @JsonAlias("PRD_VALID_DE")
        private String prdValidDe;
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

        /**
         * <pre>
         * 폐기 관리 정보에 폐기 보고 정보 매핑 처리
         * -> 사용자보고라인식별번호가 매핑되지 않은 경우 - 신규인 경우
         * -> 폐기관리진행상태가 폐기신청서 접수인 상태의 데이타 대상
         *
         * 1. 폐기 관리 정보와 폐기 보고 정보가 동일한 데이타 매핑
         * 2. 폐기관리 데이타 set
         *    -> 폐기보고진생상태 - 폐기보고매핑(02)으로 set
         *    -> 사용자보고식별번호, 원사용자보고식별번호, 보고유형코드, 처리상태 set
         * @param dsuseMgts List<BizNimsResponse.DsuseMgtRes> 진행중인 폐기관리목록
         * @return List<BizNimsResponse.DsuseMgtRes> 매핑 목록
         * </pre>
         */
        @JsonIgnore
        public List<BizNimsResponse.DsuseMgtRes> mappingNewDsuseRptInfo(List<BizNimsResponse.DsuseMgtRes> dsuseMgts){
            List<BizNimsResponse.DsuseMgtRes> newList = new ArrayList<>();

            for(BizNimsResponse.DsuseMgtRes mgtDto: dsuseMgts) {
                // 폐기 신청서 접수 상태 데이타만 처리
                if (Constants.PRGRS_STTS_CD.RECEIPT.getCode().equals(mgtDto.getPrgrsSttsCd())) {
                    String rptInfo = String.join("",
                        this.bsshCd,            // 마약류취급자식별번호
                        this.hdrDe,                         // 취급일자
                        this.rptDe,                         // 보고일자
                        this.dsuseSeCd,                     // 폐기구분코드
                        this.dsusePrvCd,                    // 폐기사유코드
                        this.dsuseMthCd,                    // 폐기방법코드
                        this.dsuseDe,                       // 폐기일자
                        String.valueOf(this.rndDtlRptCnt)   // 수불상세보고수
                    );
                    String mgtInfo = String.join("",
                        mgtDto.getBsshCd(),                         // 마약류취급자식별번호
                        mgtDto.getHdrDe(),                          // 취급일자
                        mgtDto.getRptDe(),                          // 보고일자
                        mgtDto.getDsuseSeCd(),                      // 폐기구분코드
                        mgtDto.getDsusePrvCd(),                     // 폐기사유코드
                        mgtDto.getDsuseMthCd(),                     // 폐기방법코드
                        mgtDto.getDsuseDe(),                        // 폐기일자
                        String.valueOf(mgtDto.getRndDtlRptCnt())    // 수불상세보고수
                    );

                    // FIXME: 폐기관리와 폐기보고의 상품정보 일치 여부 set - 비교 필드 확정 필요
                    if (rptInfo.equals(mgtInfo)) {
                        if (this.dsuseRptInfoDtls.size() == mgtDto.getDsuseMgtDtls().size()) {
                            for (DsuseRptInfoDtl rptDtl : this.dsuseRptInfoDtls) {
                                for (BizNimsRequest.DsuseMgtDtl mgtDtl : mgtDto.getDsuseMgtDtls()) {
                                    if (rptDtl.getPrductCd().equals(mgtDtl.getPrductCd())
                                        // && rptDtl.getMnfNo().equals(mgtDtl.getMnfNo())
                                        // && rptDtl.getPrdValidDe().equals(mgtDtl.getPrdValidDe())
                                        // && rptDtl.getMnfSeq().equals(mgtDtl.getMnfSeq())
                                        && rptDtl.getDsuseQy().equals(mgtDtl.getDsuseQy())) {
                                        mgtDtl.setValidYn("Y");
                                        break;
                                    }
                                }
                            }
                        }
                        mgtDto.setUsrRptIdNo(this.usrRptIdNo);
                        mgtDto.setOrgUsrRptIdNo(this.orgUsrRptIdNo);
                        mgtDto.setRptTyCd(this.rptTyCd);
                        mgtDto.setStts(this.status);
                        mgtDto.setPrgrsSttsCd(Constants.PRGRS_STTS_CD.MAPPING.getCode());
                        mgtDto.setRgtr(this.getRgtr());
                        newList.add(mgtDto);
                    }
                }
            }
            return newList;
        }


        /**
         * <pre>
         * 폐기 관리 정보에 폐기 보고 정보 매핑 처리
         * -> 사용자보고라인식별번호가 매핑되어 있는 경우
         *
         * 1. 원사용자식별번호가 동일한 데이타 매핑
         * 2. 폐기관리 데이타 set
         *    2-1. 취소인경우 - 폐기관리에 매핑된 정보 초기화(폐기보고신청서접수 상태로)
         *    2-2. 변경인 경우
         *    -> 폐기보고진생상태 - 폐기보고매핑(02)으로 set
         *    -> 사용자보고식별번호, 원사용자보고식별번호, 보고유형코드, 처리상태 set
         * @param dsuseMgts List<BizNimsResponse.DsuseMgtRes> 진행중인 폐기관리목록
         * @return List<BizNimsResponse.DsuseMgtRes> 매핑 목록
         * </pre>
         */
        @JsonIgnore
        public List<BizNimsResponse.DsuseMgtRes> mappingDsuseRptInfo(List<BizNimsResponse.DsuseMgtRes> dsuseMgts){
            List<BizNimsResponse.DsuseMgtRes> newList = new ArrayList<>();

            for(BizNimsResponse.DsuseMgtRes mgtDto: dsuseMgts) {

                if (this.orgUsrRptIdNo.equals(mgtDto.getOrgUsrRptIdNo())) {

                    // 취소인 경우
                    // 폐기관리에 매핑된 정보 초기화
                    if (Constants.RPT_TY_CD.CANCEL.getCode().equals(this.rptTyCd)) {
                        mgtDto.setUsrRptIdNo(StringUtils.EMPTY);
                        mgtDto.setOrgUsrRptIdNo(StringUtils.EMPTY);
                        mgtDto.setRptTyCd(StringUtils.EMPTY);
                        mgtDto.setStts(StringUtils.EMPTY);
                        mgtDto.setPrgrsSttsCd(Constants.PRGRS_STTS_CD.RECEIPT.getCode());
                        mgtDto.setRgtr(this.getRgtr());
                        newList.add(mgtDto);
                        continue;
                    }

                    // FIXME: 상품정보 미일치 여부 set - 비교 필드 확정 필요
                    if (this.dsuseRptInfoDtls.size() == mgtDto.getDsuseMgtDtls().size()) {
                        for (DsuseRptInfoDtl rptDtl : this.dsuseRptInfoDtls) {
                            for (BizNimsRequest.DsuseMgtDtl mgtDtl : mgtDto.getDsuseMgtDtls()) {
                                if (rptDtl.getPrductCd().equals(mgtDtl.getPrductCd())
                                    // && rptDtl.getMnfNo().equals(mgtDtl.getMnfNo())
                                    // && rptDtl.getPrdValidDe().equals(mgtDtl.getPrdValidDe())
                                    // && rptDtl.getMnfSeq().equals(mgtDtl.getMnfSeq())
                                    && rptDtl.getDsuseQy().equals(mgtDtl.getDsuseQy())) {
                                    mgtDtl.setValidYn("Y");
                                    break;
                                }
                            }
                        }
                    }
                    mgtDto.setUsrRptIdNo(this.usrRptIdNo);
                    mgtDto.setOrgUsrRptIdNo(this.orgUsrRptIdNo);
                    mgtDto.setRptTyCd(this.rptTyCd);
                    mgtDto.setStts(this.status);
                    mgtDto.setPrgrsSttsCd(Constants.PRGRS_STTS_CD.MAPPING.getCode());
                    mgtDto.setRgtr(this.getRgtr());
                    newList.add(mgtDto);
                }
            }
            return newList;
        }
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
         * 최소유통단위
         */
        private String stdPackngStleNm;

        /**
         * 낱개단위명
         */
        private String pceCoUnitNm;

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

}

