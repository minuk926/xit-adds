package cokr.xit.adds.inf.nims.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cokr.xit.adds.core.model.ApiBaseResponse;
import cokr.xit.adds.inf.nims.model.NimsApiDto;
import cokr.xit.adds.inf.nims.model.NimsApiRequest;
import cokr.xit.adds.inf.nims.model.NimsApiResult;
import cokr.xit.adds.inf.nims.service.InfNimsService;
import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "InfNimsController", description = "마약류 관리 시스템 Interface API - Api Interface call Test(테스트용)")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/inf/nims/v1")
public class InfNimsController {
	private final InfNimsService infNimsService;

	@Operation(summary = "마약류 취급자 정보 조회", description = "마약류 취급자 정보 조회")
	@PostMapping("/getBsshInfoSt")
	public ApiBaseResponse<NimsApiResult.Response<NimsApiDto.BsshInfoSt>> getBsshInfoSt(
		@RequestBody NimsApiRequest.BsshInfoRequest dto
	) {
		return ApiBaseResponse.of(infNimsService.getBsshInfoSt(dto));
	}

	// @Operation(summary = "마약류 상품 정보 조회", description = "마약류 상품정보 조회")
	// @PostMapping("/getProductInfoKd")
	// public ApiBaseResponse<NimsApiResult.Response<NimsApiDto.ProductInfoKd>> getProductInfoKd(
	// 	@RequestBody NimsApiRequest.ProductInfoRequest dto
	// ) {
	// 	return ApiBaseResponse.of(infNimsService.getProductInfoKd(dto));
	// }
	//
	// @Operation(summary = "제조 일련 번호 정보 조회", description = "제조 일련 번호 정보 조회")
	// @PostMapping("/getMnfSeqInfo")
	// public ApiBaseResponse<NimsApiResult.Response<NimsApiDto.MnfSeqInfo>> getMnfSeqInfo(
	// 	@RequestBody NimsApiRequest.MnfSeqInfoRequest dto
	// ) {
	// 	return ApiBaseResponse.of(infNimsService.getMnfSeqInfo(dto));
	// }
	//
	// @Operation(summary = "관할 허가 관청 정보 조회", description = "관할 허가 관청 정보 조회")
	// @PostMapping("/getJurisdictionGovInfo")
	// public ApiBaseResponse<NimsApiResult.Response<NimsApiDto.JurisdictionGovInfo>> getJurisdictionGovInfo(
	// 	@RequestBody NimsApiRequest.JurisdictionGovInfoRequest dto
	// ) {
	// 	return ApiBaseResponse.of(infNimsService.getJurisdictionGovInfo(dto));
	// }
	//
	// @Operation(summary = "저장소 정보 조회", description = "저장소 정보 조회")
	// @PostMapping("/getStorageInfo")
	// public ApiBaseResponse<NimsApiResult.Response<NimsApiDto.StorageInfo>> getStorageInfo(
	// 	@RequestBody NimsApiRequest.StorageInfoRequest dto
	// ) {
	// 	return ApiBaseResponse.of(infNimsService.getStorageInfo(dto));
	// }

	@Operation(summary = "폐기 보고 정보 조회", description = "폐기 보고 정보 조회")
	@PostMapping("/getDsuseRptInfo")
	public ApiBaseResponse<NimsApiResult.Response<NimsApiDto.DsuseRptInfo>> getDsuseRptInfo(
		@RequestBody NimsApiRequest.DsuseRptInfoRequest dto
	) {
		return ApiBaseResponse.of(infNimsService.getDsuseRptInfo(dto));
	}
}
