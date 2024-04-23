package cokr.xit.adds.biz.nims.web;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cokr.xit.adds.biz.nims.model.BizNimsRequest;
import cokr.xit.adds.biz.nims.service.BizNimsService;
import cokr.xit.adds.core.model.ApiBaseResponse;
import cokr.xit.adds.inf.nims.model.NimsApiDto;
import cokr.xit.adds.inf.nims.model.NimsApiRequest;
import cokr.xit.adds.inf.nims.service.InfNimsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * <pre>
 * description : 
 *
 * packageName : cokr.xit.adds.inf.nims.web
 * fileName    : InfNimsController
 * author      : limju
 * date        : 2024-04-03
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-04-03    limju       최초 생성
 *
 * </pre>
 */
@Tag(name = "BizNimsController", description = "마약류 관리 시스템 업무 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/biz/nims/v1")
public class BizNimsController {
    private final BizNimsService bizNimsService;
    private final InfNimsService infNimsService;

    //------------------------------------------------------------------------------------------------------
    // NIMS API CALL
    //------------------------------------------------------------------------------------------------------
    @Operation(summary = "NIMS API - 마약류 취급자 정보 조회", description = "마약류 취급자 정보 조회<br><br>NIMS API 호출 결과를 DB에 저장후 결과 Return<br><br><strong>bi-사업자등록번호, hp-요양기관번호, bn-업체명, bc-취급자식별번호 중 하나는 필수<strong>")
    @PostMapping(value = "/getBsshInfoSt")
    public ApiBaseResponse<List<NimsApiDto.BsshInfoSt>> getBsshInfoSt(
        @RequestBody @Validated NimsApiRequest.BsshInfoRequest dto
    ) {
        return ApiBaseResponse.of(bizNimsService.saveBsshInfoSt(dto));
    }

    @Operation(summary = "NIMS API - 마약류 상품 정보 조회", description = "마약류 상품정보 조회<br><br>NIMS API 호출 결과를 DB에 저장후 결과 Return")
    @PostMapping(value = "/getProductInfoKd")
    public ApiBaseResponse<List<NimsApiDto.ProductInfoKd>> getMnfSeqInfo(
        @RequestBody @Validated NimsApiRequest.ProductInfoRequest dto
    ) {
        return ApiBaseResponse.of(bizNimsService.saveProductInfoKd(dto));
    }

    @Operation(summary = "NIMS API - 제조 일련 번호 정보 조회", description = "제보 일련 번호 정보 조회<br><br>NIMS API 호출 결과 Return")
    @PostMapping(value = "/getMnfSeqInfo")
    public ApiBaseResponse<List<NimsApiDto.MnfSeqInfo>> getMnfSeqInfo(
        @RequestBody @Validated NimsApiRequest.MnfSeqInfoRequest dto
    ) {
        return ApiBaseResponse.of(bizNimsService.getMnfSeqInfo(dto));
    }

    @Operation(summary = "NIMS API - 관할 허가 관청 정보 조회", description = "관할 허가 관청 정보 조회<br><br>NIMS API 호출 결과 Return")
    @PostMapping(value = "/getJurisdictionGovInfo")
    public ApiBaseResponse<List<NimsApiDto.JurisdictionGovInfo>> getJurisdictionGovInfo(
        @RequestBody @Validated NimsApiRequest.JurisdictionGovInfoRequest dto
    ) {
        return ApiBaseResponse.of(bizNimsService.getJurisdictionGovInfo(dto));
    }

    @Operation(summary = "NIMS API - 저장소 정보 조회", description = "저장소 정보 조회<br><br>NIMS API 호출 결과를 DB에 저장후 Return")
    @PostMapping(value = "/getStorageInfo")
    public ApiBaseResponse<List<NimsApiDto.StorageInfo>> getStorageInfo(
        @RequestBody @Validated NimsApiRequest.StorageInfoRequest dto
    ) {
        return ApiBaseResponse.of(bizNimsService.saveStorageInfo(dto));
    }

    @Operation(summary = "NIMS API - 폐기 보고 정보 조회", description = "폐기 보고 정보 조회<br><br>NIMS API 호출 결과 Return<br>폐기보고관리 화면에서 사용")
    @PostMapping("/getDsuseRptInfo")
    public ApiBaseResponse<List<NimsApiDto.DsuseRpt>> getDsuseRptInfo(
        @RequestBody @Validated NimsApiRequest.DsuseRptInfoRequest dto
    ) {
        return ApiBaseResponse.of(bizNimsService.getDsuseRptInfo(dto));
    }

    //------------------------------------------------------------------------------------------------------
    // NIMS BIZ
    //------------------------------------------------------------------------------------------------------
    // @Operation(summary = "NIMS API - 사고 마약류 폐기 생성 보고", description = "사고 마약류 폐기 생성<br><br>NIMS 연계 시스템에 폐기 생성 send")
    // @PostMapping("/createReportDsuse")
    // public ApiBaseResponse<NimsAarResult> createReportDsuse(
    // 	@RequestBody @Validated NimsApiRequest.ProductInfoRequest dto
    // ) {
    // 	return ApiBaseResponse.of(bizNimsService.createReportDsuse());
    // }
    //
    // @Operation(summary = "NIMS API - 사고 마약류 폐기 변경 보고", description = "사고 마약류 폐기 변경<br><br>NIMS 연계 시스템에 폐기 변경 send")
    // @PostMapping("/updateReportDsuse")
    // public ApiBaseResponse<NimsAarResult> updateReportDsuse(
    // 	@RequestBody @Validated NimsApiRequest.ProductInfoRequest dto
    // ) {
    // 	return ApiBaseResponse.of(bizNimsService.updateReportDsuse());
    // }
    //
    // @Operation(summary = "NIMS API - 사고 마약류 폐기 취소 보고", description = "사고 마약류 폐기 취소<br><br>NIMS 연계 시스템에 폐기 취소 send")
    // @PostMapping("/cancelReportDsuse")
    // public ApiBaseResponse<NimsAarResult> cancelReportDsuse(
    // 	@RequestBody @Validated NimsApiRequest.ProductInfoRequest dto
    // ) {
    // 	return ApiBaseResponse.of(bizNimsService.cancelReportDsuse());
    // }

    @Operation(summary = "사고 마약류 폐기 관리 등록", description = "사고 마약류 폐기 관리 등록<br><br>폐기보고관리 화면에서 보고대상 조회후 등록시 사용")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = {
        @Content(mediaType = "application/json", examples = {
            @ExampleObject(value = """
                [
                    {
                        "userId":"userid1",
                        "USR_RPT_ID_NO":"usrRptIdNo1111",
                        "REF_USR_RPT_ID_NO":null,
                        "BSSH_CD":"bsshCd",
                        "RPT_TY_CD":"0",
                        "RND_DTL_RPT_CNT":2,
                        "HDR_DE":"20240401",
                        "RPT_DE":"20240401",
                        "DSUSE_SE_CD":"1",
                        "DSUSE_PRV_CD":"01",
                        "DSUSE_MTH_CD":"1",
                        "DSUSE_LOC":"보건소",
                        "DSUSE_DE":null,
                        "STATUS":"0",
                        "RPT_PRG_STTS_CD":"0",
                        "dsuseMgtDtls":[
                            {
                                "USR_RPT_ID_NO":"usrRptIdNo1111",
                                "USR_RPT_LN_ID_NO":"dsuseRptDtlIdNo11",
                                "PRDUCT_CD":"상품코드",
                                "PRDCT_NM":"제품명",
                                "MIN_DISTB_QY":1,
                                "PCE_QY":0,
                                "MNF_NO":"제조번호",
                                "PRD_VALID_DE":"20240401",
                                "MNF_SEQ":"-",
                                "STORGE_NO":"저장소번호",
                                "STORGE_NM":"저장소명",
                                "MVMN_TY_CD":"1102",
                                "DSUSE_QY":30
                            },
                            {
                                "USR_RPT_ID_NO":"usrRptIdNo1111",
                                "USR_RPT_LN_ID_NO":"dsuseRptDtlIdNo22",
                                "PRDUCT_CD":"상품코드1",
                                "PRDCT_NM":"제품명1",
                                "MIN_DISTB_QY":1,
                                "PCE_QY":0,
                                "MNF_NO":"제조번호1",
                                "PRD_VALID_DE":"20240401",
                                "MNF_SEQ":"-",
                                "STORGE_NO":"저장소번호1",
                                "STORGE_NM":"저장소명1",
                                "MVMN_TY_CD":"1102",
                                "DSUSE_QY":10
                            }
                        ]
                    },
                    {
                        "userId":"userid1",
                        "USR_RPT_ID_NO":"usrRptIdNo2222",
                        "REF_USR_RPT_ID_NO":null,
                        "BSSH_CD":"bsshCd",
                        "RPT_TY_CD":"0",
                        "RND_DTL_RPT_CNT":2,
                        "HDR_DE":"20240401",
                        "RPT_DE":"20240401",
                        "DSUSE_SE_CD":"1",
                        "DSUSE_PRV_CD":"01",
                        "DSUSE_MTH_CD":"1",
                        "DSUSE_LOC":"보건소",
                        "DSUSE_DE":null,
                        "STATUS":"0",
                        "RPT_PRG_STTS_CD":"0",
                        "dsuseMgtDtls":[
                            {
                                "USR_RPT_ID_NO":"usrRptIdNo2222",
                                "USR_RPT_LN_ID_NO":"dsuseRptDtlIdNo33",
                                "PRDUCT_CD":"상품코드",
                                "PRDCT_NM":"제품명",
                                "MIN_DISTB_QY":1,
                                "PCE_QY":0,
                                "MNF_NO":"제조번호",
                                "PRD_VALID_DE":"20240401",
                                "MNF_SEQ":"-",
                                "STORGE_NO":"저장소번호",
                                "STORGE_NM":"저장소명",
                                "MVMN_TY_CD":"1102",
                                "DSUSE_QY":30
                            },
                            {
                                "USR_RPT_ID_NO":"usrRptIdNo2222",
                                "USR_RPT_LN_ID_NO":"dsuseRptDtlIdNo44",
                                "PRDUCT_CD":"상품코드1",
                                "PRDCT_NM":"제품명1",
                                "MIN_DISTB_QY":1,
                                "PCE_QY":0,
                                "MNF_NO":"제조번호1",
                                "PRD_VALID_DE":"20240401",
                                "MNF_SEQ":"-",
                                "STORGE_NO":"저장소번호1",
                                "STORGE_NM":"저장소명1",
                                "MVMN_TY_CD":"1102",
                                "DSUSE_QY":10
                            }
                        ]
                    },
                    {
                        "userId":"userid1",
                        "USR_RPT_ID_NO":"usrRptIdNo3333",
                        "REF_USR_RPT_ID_NO":"usrRptIdNo1111",
                        "BSSH_CD":"bsshCd",
                        "RPT_TY_CD":"2",
                        "RND_DTL_RPT_CNT":2,
                        "HDR_DE":"20240401",
                        "RPT_DE":"20240401",
                        "DSUSE_SE_CD":"1",
                        "DSUSE_PRV_CD":"01",
                        "DSUSE_MTH_CD":"1",
                        "DSUSE_LOC":"보건소",
                        "DSUSE_DE":null,
                        "STATUS":"0",
                        "RPT_PRG_STTS_CD":"0",
                        "dsuseMgtDtls":[
                            {
                                "USR_RPT_ID_NO":"usrRptIdNo3333",
                                "USR_RPT_LN_ID_NO":"dsuseRptDtlIdNo55",
                                "PRDUCT_CD":"상품코드",
                                "PRDCT_NM":"제품명",
                                "MIN_DISTB_QY":1,
                                "PCE_QY":0,
                                "MNF_NO":"제조번호",
                                "PRD_VALID_DE":"20240401",
                                "MNF_SEQ":"-",
                                "STORGE_NO":"저장소번호",
                                "STORGE_NM":"저장소명",
                                "MVMN_TY_CD":"1102",
                                "DSUSE_QY":30
                            },
                            {
                                "USR_RPT_ID_NO":"usrRptIdNo3333",
                                "USR_RPT_LN_ID_NO":"dsuseRptDtlIdNo66",
                                "PRDUCT_CD":"상품코드1",
                                "PRDCT_NM":"제품명1",
                                "MIN_DISTB_QY":1,
                                "PCE_QY":0,
                                "MNF_NO":"제조번호1",
                                "PRD_VALID_DE":"20240401",
                                "MNF_SEQ":"-",
                                "STORGE_NO":"저장소번호1",
                                "STORGE_NM":"저장소명1",
                                "MVMN_TY_CD":"1102",
                                "DSUSE_QY":10
                            }
                        ]
                    },
                    {
                        "userId":"userid1",
                        "USR_RPT_ID_NO":"usrRptIdNo4444",
                        "REF_USR_RPT_ID_NO":"usrRptIdNo2222",
                        "BSSH_CD":"bsshCd",
                        "RPT_TY_CD":"1",
                        "RND_DTL_RPT_CNT":0,
                        "HDR_DE":"20240401",
                        "RPT_DE":"20240401",
                        "DSUSE_SE_CD":"1",
                        "DSUSE_PRV_CD":"01",
                        "DSUSE_MTH_CD":"1",
                        "DSUSE_LOC":"보건소",
                        "DSUSE_DE":null,
                        "STATUS":"0",
                        "RPT_PRG_STTS_CD":"0",
                        "dsuseMgtDtls":[]
                    }
                ]
                """)
        })
    })
    @PostMapping("/saveDsuseMgt")
    public ApiBaseResponse<List<BizNimsRequest.DsuseMgt>> saveDsuseMgt(
        @RequestBody List<BizNimsRequest.DsuseMgt> dtos
    ) {
        return ApiBaseResponse.of(bizNimsService.saveDsuseMgt(dtos));
    }

    /*
    @Operation(summary = "폐기 보고 관리 대상 연계 데이타 생성 조회", description = "폐기 보고 관리 대상 연계 데이타 생성 조회")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = {
        @Content(mediaType = "application/json", examples = {
            @ExampleObject(value = """
                    {
                      "dscdmngId": "2024040002",
                      "bsshCd": "H00008333",
                      "userId": "userid1",
                      "prgrsSttsCd": "01",
                      "dsuseMgtDtls": []
                    }
                """)
        })
    })
    @PostMapping("/getTgtDsuseRptData")
    public ApiBaseResponse<BizNimsAarDto.AarHeader> getTgtDsuseRptData(
        @RequestBody BizNimsRequest.DsuseMgt dto
    ) {
        return ApiBaseResponse.of(bizNimsService.getTgtDsuseRptData(dto));
    }

    @Operation(summary = "폐기 보고 관리 대상 연계 데이타 생성", description = "폐기 보고 관리 대상 연계 데이타 생성")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = {
        @Content(mediaType = "application/json", examples = {
            @ExampleObject(value = """
                            {
                	"dscdmngId": "2024040002",
                	"uid": "",
                	"swId": null,
                	"rndRmk": "",
                	"hdrDe": null,
                	"bsshCd": "H00008333",
                	"rptSeCd": "AAR",
                	"usrRptIdNo": "2024040002_00001",
                	"refUsrRptIdNo": "",
                	"rptTyCd": "0",
                	"rmk": "",
                	"rptrNm": "",
                	"rptrEntrpsNm": "수지미래산부인과의원",
                	"chrgNm": "이은혜",
                	"chrgTelNo": "",
                	"chrgMpNo": "",
                	"rndDtlRptCnt": "",
                	"dsuseSeCd": "1",
                	"dsusePrvCd": "",
                	"dsuseMthCd": "3",
                	"dsuseLoc": "",
                	"dsuseDe": "",
                	"dsuseInsttCd": "4050149",
                	"atchFileCo": "0",
                	"registerId": "",
                	"fileCreatDt": "",
                	"atchFiles": [],
                	"aarDetails": [
                	  {
                		"usrRptIdNo": null,
                		"usrRptLnIdNo": "",
                		"storgeNo": "S0001",
                		"mvmnTyCd": "1102",
                		"prductCd": "8806718050823",
                		"mnfNo": "123456",
                		"mnfSeq": "A0201K5391D57T",
                		"minDistbQy": "",
                		"prdMinDistbUnit": "",
                		"pceQy": "",
                		"prdPceUnit": "",
                		"prductNm": "아쿠아폴주20밀리리터(프로포폴) (20㎖)",
                		"prdSgtin": "",
                		"prdMinDistbQy": "1",
                		"prdTotPceQy": "5",
                		"prdValidDe": "99991231",
                		"fileCreatDt": "",
                		"mnfSeqInfos": []
                	  },
                	  {
                		"usrRptIdNo": null,
                		"usrRptLnIdNo": "",
                		"storgeNo": "S0001",
                		"mvmnTyCd": "1102",
                		"prductCd": "8806498014732",
                		"mnfNo": "0",
                		"mnfSeq": "002147483999",
                		"minDistbQy": "",
                		"prdMinDistbUnit": "",
                		"pceQy": "",
                		"prdPceUnit": "",
                		"prductNm": "명문인산코데인정 (20㎎)",
                		"prdSgtin": "",
                		"prdMinDistbQy": "1",
                		"prdTotPceQy": "100",
                		"prdValidDe": "99991231",
                		"fileCreatDt": "",
                		"mnfSeqInfos": []
                	  }
                	]
                }
                        """)
        })
    })
    @PostMapping("/createTgtDsuseRptData")
    public ApiBaseResponse<BizNimsAarDto.AarHeader> createTgtDsuseRptData(
        @RequestBody BizNimsAarDto.AarHeader dto
    ) {
        return ApiBaseResponse.of(bizNimsService.createTgtDsuseRptData(dto));
    }
     */
}
