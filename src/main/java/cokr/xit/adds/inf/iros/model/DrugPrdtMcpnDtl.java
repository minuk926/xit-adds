package cokr.xit.adds.inf.iros.model;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 * <pre>
 * description :
 *
 * packageName : cokr.xit.adds.inf.iros.model
 * fileName    : DrugPrdtMcpnDtl
 * author      : limju
 * date        : 2024-03-20
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-03-20    limju       최초 생성
 *
 * </pre>
 */
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record DrugPrdtMcpnDtl(
    /**
     * 업체 허가 번호
     */
    @JsonAlias("ENTRPS_PRMISN_NO")
    String entrpsPrmisnNo,

    /**
     * 제품명
     */
    @JsonAlias("ENTRPS")
    String entrps,

    /**
     * 제품명(한글)
     */
    @JsonAlias("PRDUCT")
    String prduct,

    /**
     * 일련 번호
     */
    @JsonAlias("MTRAL_SN")
    String mtralSn,

    /**
     * 원료 코드
     */
    @JsonAlias("MTRAL_CODE")
    String mtralCode,

    /**
     * 원료명
     */
    @JsonAlias("MTRAL_NM")
    String mtralNm,

    /**
     * 분량
     */
    @JsonAlias("QNT")
    String qnt,

    /**
     * 분량 단위 정보
     */
    @JsonAlias("INGD_UNIT_CD")
    String ingdUnitCd,

    /**
     * 품목 기준 코드
     */
    @JsonAlias("ITEM_SEQ")
    String itemSeq,

    /**
     * 영문 성분명
     */
    @JsonAlias("MAIN_INGR_ENG")
    String mainIngrEng,

    /**
     * 사업자 등록 번호
     */
    @JsonAlias("BIZRNO")
    String bizrno,

    /**
     * 세부 구성 항목
     */
    @JsonAlias("CPNT_CTNT_CONT")
    String cpntCtntCont
) {
}
