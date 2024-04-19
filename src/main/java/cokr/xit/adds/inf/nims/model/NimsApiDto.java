package cokr.xit.adds.inf.nims.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import cokr.xit.adds.core.model.AuditDto;
import cokr.xit.adds.core.spring.exception.ApiCustomException;
import lombok.AllArgsConstructor;
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

