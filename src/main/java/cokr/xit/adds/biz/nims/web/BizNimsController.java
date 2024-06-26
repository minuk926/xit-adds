package cokr.xit.adds.biz.nims.web;

import java.io.IOException;
import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.zxing.NotFoundException;

import cokr.xit.adds.biz.nims.model.BizNimsRequest;
import cokr.xit.adds.biz.nims.model.BizNimsResponse;
import cokr.xit.adds.biz.nims.service.BizNimsService;
import cokr.xit.adds.core.model.ApiBaseResponse;
import cokr.xit.adds.core.util.XingUtils;
import cokr.xit.adds.inf.nims.model.NimsApiDto;
import cokr.xit.adds.inf.nims.model.NimsApiRequest;
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

    //------------------------------------------------------------------------------------------------------
    // NIMS API CALL
    //------------------------------------------------------------------------------------------------------
    @Operation(summary = "마약류 취급자 정보 조회(NIMS API)", description = "마약류 취급자 정보 조회<br><br>NIMS API 호출 결과를 DB에 저장후 결과 Return<br><br><strong>bi-사업자등록번호, hp-요양기관번호, bn-업체명, bc-취급자식별번호 중 하나는 필수<strong>")
    @PostMapping(value = "/getBsshInfoSt")
    public ApiBaseResponse<List<NimsApiDto.BsshInfoSt>> getBsshInfoSt(
        @RequestBody @Validated NimsApiRequest.BsshInfoReq dto
    ) {
        return ApiBaseResponse.of(bizNimsService.saveBsshInfoSt(dto));
    }

    @Operation(summary = "마약류 상품 정보 조회(NIMS API)", description = "마약류 상품정보 조회<br><br>NIMS API 호출 결과를 DB에 저장후 결과 Return")
    @PostMapping(value = "/getProductInfoKd")
    public ApiBaseResponse<List<NimsApiDto.ProductInfoKd>> getMnfSeqInfo(
        @RequestBody @Validated NimsApiRequest.ProductInfoReq dto
    ) {
        return ApiBaseResponse.of(bizNimsService.saveProductInfoKd(dto, false));
    }

    @Operation(summary = "마약류 상품 정보 & 제조번호 조회(NIMS API)", description = "마약류 상품정보 & 제조번호 조회<br><br>NIMS API 호출 결과를 DB에 저장후 결과 Return")
    @PostMapping(value = "/getProductInfoKdAndMnfSeqInfo")
    public ApiBaseResponse<List<NimsApiDto.ProductInfoKd>> getProductInfoKdAndMnfSeqInfo(
        @RequestBody @Validated NimsApiRequest.ProductInfoReq dto
    ) {
        return ApiBaseResponse.of(bizNimsService.saveProductInfoKd(dto, true));
    }

    @Operation(summary = "제조 일련 번호 정보 조회(NIMS API)", description = "제보 일련 번호 정보 조회<br><br>NIMS API 호출 결과 Return")
    @PostMapping(value = "/getMnfSeqInfo")
    public ApiBaseResponse<List<NimsApiDto.MnfSeqInfo>> getMnfSeqInfo(
        @RequestBody @Validated NimsApiRequest.MnfSeqInfoReq dto
    ) {
        return ApiBaseResponse.of(bizNimsService.getMnfSeqInfo(dto));
    }

    @Operation(summary = "폐기 보고 정보 목록 조회(NIMS API)", description = "업체의 폐기 보고 정보 목록 조회<br><br>NIMS API 호출 결과를 DB에 저장후 Return")
    @PostMapping("/getDsuseRptInfo")
    public ApiBaseResponse<List<NimsApiDto.DsuseRptInfo>> getDsuseRptInfo(
        @RequestBody @Validated NimsApiRequest.DsuseRptInfoReq dto
    ) {
        return ApiBaseResponse.of(bizNimsService.saveDsuseRptInfo(dto));
    }

    //------------------------------------------------------------------------------------------------------
    // NIMS BIZ
    //------------------------------------------------------------------------------------------------------
    @Operation(summary = "사고 마약류 폐기 관리 생성", description = "사고 마약류 폐기 관리 생성<br><br>폐기관리 데이타 생성후 폐기보고 정보 return")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = {
        @Content(mediaType = "application/json", examples = {
            @ExampleObject(value = """
                {
                   "userId": "api-user",
                   "prgrsSttsCd": "01",
                   "bsshCd": "H00008333",
                   "rndDtlRptCnt": 2,
                   "hdrDe": "20240301",
                   "rptDe": "20240305",
                   "dsuseSeCd": "1",
                   "dsusePrvCd": "04",
                   "dsuseMthCd": "3",
                   "dsuseLoc": "보건소소각장",
                   "dsuseDe": "20240306",
                   "dsuseMgtDtls": [
                     {
                       "prductCd": "8806717024900",
                       "prductNm": "베리콜시럽",
                       "minDistbQy": 1,
                       "pceQy": 1,
                       "mnfNo": "A1111",
                       "prdValidDe": "20300303",
                       "mnfSeq": "A11111111",
                       "dsuseQy": 10
                     },
                     {
                       "prductCd": "8806718050823",
                       "prductNm": "아쿠아폴주20밀리리터(프로포폴) (20㎖)",
                       "minDistbQy": 1,
                       "pceQy": 5,
                       "mnfNo": "A1111",
                       "prdValidDe": "20300303",
                       "mnfSeq": "A11111111",
                       "dsuseQy": 30
                     }
                   ]
                 }
                """)
        })
    })
    @PostMapping("/saveDsuseMgt")
    public ApiBaseResponse<BizNimsRequest.DsuseMgt> saveDsuseMgt(
        @RequestBody BizNimsRequest.DsuseMgt dto
    ) {
        return ApiBaseResponse.of(bizNimsService.saveDsuseMgt(dto));
    }

    @Operation(summary = "사고 마약류 폐기 관리 목록 조회", description = "사고 마약류 폐기 관리 목록 조회<br><br>사고마약류 관리대장, 결과통보서, 폐기현황등에 필요한 내용 조회")
    @PostMapping(value = "/getDsuseMgts")
    public ApiBaseResponse<List<BizNimsResponse.DsuseMgtRes>> getDsuseMgts(
        @RequestBody BizNimsRequest.DsuseMgtInq dto
    ) {
        return ApiBaseResponse.of(bizNimsService.getDsuseMgts(dto));
    }


    @Operation(summary = "barcode string 제품 제조 정보 조회", description = "barcode string 제품 제조 정보 조회<br><br>Barcode를 통한 제품 제조 정보 조회<br><br>ex) 01088065780457311717050110A1234210000000006")
    @GetMapping(value = "/getPrdMnfSeqInfoOfBarcode")
    public ApiBaseResponse<NimsApiDto.ProductInfoKd> getPrdMnfSeqInfoOfBarcode(
        final String barcodeStr
    ) {
        return ApiBaseResponse.of(bizNimsService.getPrdMnfSeqInfoOfBarcode(barcodeStr));
    }

    @Operation(summary = "barcode 이미지 제품 제조 정보 조회", description = "barcode 이미지 제품 제조 정보 조회<br><br>barcode 이미지를 통한 제품 제조 정보 조회")
    @PostMapping(value = "/getProductInfoByQrcodeImg", consumes = { "multipart/form-data" })
    //@PostMapping(value = "/api/biz/nims/v1/getQrcode")
    public ApiBaseResponse<List<NimsApiDto.ProductInfoKd>> getProductInfoByQrcodeImg(
        @RequestParam("uploadFiles")
        final List<MultipartFile> multipartFiles
    ) {
        List<NimsApiDto.ProductInfoKd> list = multipartFiles.stream().map(mf -> {
            //System.out.println(mf.getOriginalFilename());
            //System.out.println(mf.getSize());
            try {
                return bizNimsService.getPrdMnfSeqInfoOfBarcode(XingUtils.readQrcodeFromFile(XingUtils.convert(mf)));
            } catch (IOException | NotFoundException e) {
                throw new RuntimeException(e);
            }
        }).toList();

        return ApiBaseResponse.of(list);
    }





    @Operation(deprecated = true, summary = "사고 마약류 폐기 관리 목록 조회", description = "사고 마약류 폐기 관리 목록 조회<br><br>사고마약류 관리대장, 결과통보서, 폐기현황등에 필요한 내용 조회")
    @PostMapping(value = "/getDsuseMgts2")
    public ApiBaseResponse<List<BizNimsResponse.DsuseRptInfoRes>> getDsuseMgts2(
        @RequestBody BizNimsRequest.DsuseMgtInq dto
    ) {
        return null; //ApiBaseResponse.of(bizNimsService.getDsuseMgts(dto));
    }

    // @Operation(deprecated = true, summary = "사고 마약류 폐기 관리 생성", description = "사고 마약류 폐기 관리 생성<br><br>폐기관리 데이타 생성후 폐기보고 정보 return")
    // @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = {
    //     @Content(mediaType = "application/json", examples = {
    //         @ExampleObject(value = """
    //             [
    //                 {
    //                     "userId":"userid1",
    //                     "usrRptIdNo":"22222",
    //                     "orgUsrRptIdNo":"22222"
    //                 },
    //                 {
    //                     "userId":"userid1",
    //                     "usrRptIdNo":"44444",
    //                     "orgUsrRptIdNo":"44444"
    //                 },
    //                 {
    //                     "userId":"userid1",
    //                     "usrRptIdNo":"77777",
    //                     "orgUsrRptIdNo":"33333"
    //                 }
    //             ]
    //             """)
    //     })
    // })
    // @PostMapping("/saveDsuseMgts")
    // public ApiBaseResponse<List<BizNimsResponse.DsuseRptInfoResponse>> saveDsuseMgts(
    //     @RequestBody List<BizNimsRequest.DsuseMgt> dtos
    // ) {
    //     return ApiBaseResponse.of(bizNimsService.saveDsuseMgts(dtos));
    // }





    // @Operation(summary = "관할 허가 관청 정보 조회(NIMS API)", description = "관할 허가 관청 정보 조회<br><br>NIMS API 호출 결과 Return")
    // @PostMapping(value = "/getJurisdictionGovInfo")
    // public ApiBaseResponse<List<NimsApiDto.JurisdictionGovInfo>> getJurisdictionGovInfo(
    //     @RequestBody @Validated NimsApiRequest.JurisdictionGovInfoRequest dto
    // ) {
    //     return ApiBaseResponse.of(bizNimsService.getJurisdictionGovInfo(dto));
    // }
    //
    // @Operation(summary = "저장소 정보 조회(NIMS API)", description = "저장소 정보 조회<br><br>NIMS API 호출 결과를 DB에 저장후 Return")
    // @PostMapping(value = "/getStorageInfo")
    // public ApiBaseResponse<List<NimsApiDto.StorageInfo>> getStorageInfo(
    //     @RequestBody @Validated NimsApiRequest.StorageInfoRequest dto
    // ) {
    //     return ApiBaseResponse.of(bizNimsService.saveStorageInfo(dto));
    // }




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

}
