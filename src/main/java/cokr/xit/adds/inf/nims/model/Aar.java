// package cokr.xit.adds.inf.nims.model;
//
// import java.util.List;
//
// import com.fasterxml.jackson.annotation.JsonInclude;
// import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
// import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
// import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
//
// import jakarta.xml.bind.annotation.XmlAccessType;
// import jakarta.xml.bind.annotation.XmlAccessorType;
// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.NoArgsConstructor;
//
// /**
//  * <pre>
//  * description : NIMS xsd 구조 정의
//  *               마약류 관리 시스템 연계시 사용되는 xsd 구조 정의
//  *               -> 폐기(AAR)
//  *
//  * packageName : cokr.xit.adds.inf.nims.model
//  * fileName    : Aar
//  * author      : limju
//  * date        : 2024-03-22
//  * ======================================================================
//  * 변경일         변경자        변경 내용
//  * ----------------------------------------------------------------------
//  * 2024-03-22    limju       최초 생성
//  *
//  * </pre>
//  */
// @JacksonXmlRootElement(localName = "aar_regist", namespace = "https://www.nims.or.kr/schema/nims")
// @NoArgsConstructor
// @AllArgsConstructor
// @Builder
// public class Aar {
//     @JacksonXmlProperty(localName = "REPORT_SET")
//     private ReportSet reportSet;
//
//     @JsonInclude(JsonInclude.Include.NON_NULL)
//     @XmlAccessorType(XmlAccessType.FIELD)
//     @NoArgsConstructor
//     @AllArgsConstructor
//     @Builder
//     public static class ReportSet {
//         /**
//          * 필수 - 보고자식별ID
//          */
//         @JacksonXmlProperty(localName = "UID")
//         private String uid;
//
//         /**
//          * 필수 - 소프트웨어ID
//          */
//         @JacksonXmlProperty(localName = "SW_ID")
//         private String swId;
//
//         /**
//          * 수불비고 - 보고사유 기재
//          */
//         @JacksonXmlProperty(localName = "RND_RMK")
//         private String rndRmk;
//
//         /**
//          * Header
//          */
//         @JacksonXmlElementWrapper(useWrapping = false)
//         @JacksonXmlProperty(localName = "HEADER")
//         private List<Header> header;
//
//     }
//
//     @JsonInclude(JsonInclude.Include.NON_NULL)
//     @NoArgsConstructor
//     @AllArgsConstructor
//     @Builder
//     public static class Header {
//         /**
//          * 필수 - 취급일자
//          * 폐기신청민원 처리일자(공문시행일자)
//          */
//         @JacksonXmlProperty(localName = "HDR_DE")
//         private String hdrDe;
//
//         /**
//          * 필수 - 마약류취급자식별번호
//          */
//         @JacksonXmlProperty(localName = "BSSH_CD")
//         private String bsshCd;
//
//         /**
//          * 필수 - 보고구분코드
//          * AAR - 폐기
//          */
//         @JacksonXmlProperty(localName = "RPT_SE_CD")
//         private String rptSeCd;
//
//         /**
//          * 필수 - 사용자보고식별번호
//          * 보고자 시스템에서 관리하는 고유식별번호
//          * 변경|취소시 에도 다른 식별 번호 생성
//          */
//         @JacksonXmlProperty(localName = "USR_RPT_ID_NO")
//         private String usrRptIdNo;
//
//         /**
//          * 참조사용자보고식별번호(변경|취소시 필수)
//          * 원본 사용자보고식별번호 기입
//          */
//         @JacksonXmlProperty(localName = "REF_USR_RPT_ID_NO")
//         private String refUsrRptIdNo;
//
//         /**
//          * 필수 - 보고유형코드
//          * 0-신규, 1-취소, 2-변경
//          */
//         @JacksonXmlProperty(localName = "RPT_TY_CD")
//         private String rptTyCd;
//
//         /**
//          * 비고(변경 및 취소시 필수-사유 기재)
//          */
//         @JacksonXmlProperty(localName = "RMK")
//         private String rmk;
//
//         /**
//          * 필수 - 보고자명
//          */
//         @JacksonXmlProperty(localName = "RPTR_NM")
//         private String rptrNm;
//
//         /**
//          * 필수 - 보고자업체명
//          */
//         @JacksonXmlProperty(localName = "RPTR_ENTRPS_NM")
//         private String rptrEntrpsNm;
//
//         /**
//          * 필수 - 담당자명
//          */
//         @JacksonXmlProperty(localName = "CHRG_NM")
//         private String chrgNm;
//
//         /**
//          * 필수 - 담당자 전화번호
//          */
//         @JacksonXmlProperty(localName = "CHRG_TEL_NO")
//         private String chrgTelNo;
//
//         /**
//          * 필수 - 담당자 휴대폰 번호
//          * 암호화 필요
//          */
//         @JacksonXmlProperty(localName = "CHRG_MP_NO")
//         private String chrgMpNo;
//
//         /**
//          * 필수 - 수불 상세 보고수
//          * 당해 보고건의 라인수
//          */
//         @JacksonXmlProperty(localName = "RND_DTL_RPT_CNT")
//         private String rndDtlRptCnt;
//
//         /**
//          * 필수 - 폐기구분코드
//          * 1-보건소폐기, 2-공무원임회, 4-도난/분실/재해 발생 사고마약류
//          */
//         @JacksonXmlProperty(localName = "DSUSE_SE_CD")
//         private String dsuseSeCd;
//
//         /**
//          * <pre>
//          * 필수 - 폐기 사유 코드
//          *
//          * 01-파손, 02-변질,부패, 03-유효기간 또는 사용기한 경과
//          * 04-유효 기간 임박, 05-사용 중단, 07-폐업, 08-환자 반납
//          * 09-기타, 12-제조 공정중 폐기물
//          * </pre>
//          */
//         @JacksonXmlProperty(localName = "DSUSE_PRV_CD")
//         private String dsusePrvCd;
//
//         /**
//          * <pre>
//          * 필수 - 폐기방법코드
//          *
//          * 1-소각, 2-중화, 3-가수 분해, 4-산화, 5-환원
//          * 6-희석, 7-매물, 8-기타(파쇄,혼합), 9-사고
//          * </pre>
//          */
//         @JacksonXmlProperty(localName = "DSUSE_MTH_CD")
//         private String dsuseMthCd;
//
//         /**
//          * 필수 - 폐기장소
//          */
//         @JacksonXmlProperty(localName = "DSUSE_LOC")
//         private String dsuseLoc;
//
//         /**
//          * 필수 - 폐기일자
//          * yyyyMMdd
//          */
//         @JacksonXmlProperty(localName = "DSUSE_DE")
//         private String dsuseDe;
//
//         /**
//          * 필수 - 폐기 관할 행정 기관 코드
//          */
//         @JacksonXmlProperty(localName = "DSUSE_INSTT_CD")
//         private String dsuseInsttCd;
//
//         /**
//          * 필수 - 첨부 파일 건수
//          */
//         @JacksonXmlProperty(localName = "ATCH_FILE_CO")
//         private String atchFileCo;
//
//         /**
//          * Line
//          */
//         @JacksonXmlProperty(localName = "REGISTER_ID")
//         private String registerId;
//
//         @JacksonXmlProperty(localName = "FILE_CREAT_DT")
//         private String fileCreatDt;
//
//         @JacksonXmlProperty(localName = "LINES")
//         private Lines lines;
//
//         @JacksonXmlProperty(localName = "ATCH_FILES")
//         private AtchFiles atchFiles;
//     }
//
//     @JsonInclude(JsonInclude.Include.NON_NULL)
//     @NoArgsConstructor
//     @AllArgsConstructor
//     @Builder
//     public static class Lines {
//         @JacksonXmlElementWrapper(useWrapping = false)
//         @JacksonXmlProperty(localName = "LINE")
//         private List<Line> line;
//     }
//
//     @JsonInclude(JsonInclude.Include.NON_NULL)
//     @NoArgsConstructor
//     @AllArgsConstructor
//     @Builder
//     public static class Line {
//         @JacksonXmlProperty(localName = "USR_RPT_ID_NO")
//         private String usrRptIdNo;
//
//         @JacksonXmlProperty(localName = "USR_RPT_LN_ID_NO")
//         private String usrRptLnIdNo;
//
//         @JacksonXmlProperty(localName = "STORGE_NO")
//         private String storgeNo;
//
//         @JacksonXmlProperty(localName = "MVMN_TY_CD")
//         private String mvmnTyCd;
//
//         @JacksonXmlProperty(localName = "PRDUCT_CD")
//         private String prductCd;
//
//         @JacksonXmlProperty(localName = "MNF_NO")
//         private String mnfNo;
//         @JacksonXmlProperty(localName = "MNF_SEQ")
//         private String mnfSeq;
//
//         @JacksonXmlProperty(localName = "MIN_DISTB_QY")
//         private String minDistbQy;
//
//         @JacksonXmlProperty(localName = "PRD_MIN_DISTB_UNIT")
//         private String prdMinDistbUnit;
//
//         @JacksonXmlProperty(localName = "PCE_QY")
//         private String pceQy;
//
//         @JacksonXmlProperty(localName = "PRD_PCE_UNIT")
//         private String prdPceUnit;
//
//         @JacksonXmlProperty(localName = "PRDUCT_NM")
//         private String prductNm;
//
//         @JacksonXmlProperty(localName = "PRD_SGTIN")
//         private String prdSgtin;
//
//         @JacksonXmlProperty(localName = "PRD_MIN_DISTB_QY")
//         private String prdMinDistbQy;
//
//         @JacksonXmlProperty(localName = "PRD_TOT_PCE_QY")
//         private String prdTotPceQy;
//
//         @JacksonXmlProperty(localName = "PRD_VALID_DE")
//         private String prdValidDe;
//
//         @JacksonXmlProperty(localName = "FILE_CREAT_DT")
//         private String fileCreatDt;
//     }
//
//     @JsonInclude(JsonInclude.Include.NON_NULL)
//     @NoArgsConstructor
//     @AllArgsConstructor
//     @Builder
//     public static class AtchFiles{
//         @JacksonXmlElementWrapper(useWrapping = false)
//         @JacksonXmlProperty(localName = "ATCH_FILE_NM")
//         private List<String> atchFileNm;
//     }
// }
