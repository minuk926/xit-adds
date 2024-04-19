package cokr.xit.adds.inf.iros.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

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
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record DrugPrdtMcpnDtl(
    @JsonProperty("ENTRPS_PRMISN_NO")
    String entrpsPrmisnNo,
    @JsonProperty("ENTRPS")
    String entrps,
    @JsonProperty("PRDUCT")
    String prduct,
    @JsonProperty("MTRAL_SN")
    String mtralSn,
    @JsonProperty("MTRAL_CODE")
    String mtralCode,
    @JsonProperty("MTRAL_NM")
    String mtralNm,
    @JsonProperty("QNT")
    String qnt,
    @JsonProperty("INGD_UNIT_CD")
    String ingdUnitCd,
    @JsonProperty("ITEM_SEQ")
    String itemSeq,
    @JsonProperty("MAIN_INGR_ENG")
    String mainIngrEng,
    @JsonProperty("BIZRNO")
    String bizrno,
    @JsonProperty("CPNT_CTNT_CONT")
    String cpntCtntCont
) {
}
