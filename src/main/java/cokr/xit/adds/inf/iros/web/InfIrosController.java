package cokr.xit.adds.inf.iros.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cokr.xit.adds.core.model.ApiBaseResponse;
import cokr.xit.adds.inf.iros.model.DrugPrdtMcpnDtl;
import cokr.xit.adds.inf.iros.model.DrugPrdtMcpnDtlReq;
import cokr.xit.adds.inf.iros.model.IrosResponse;
import cokr.xit.adds.inf.iros.service.InfIrosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * <pre>
 * description : 
 *
 * packageName : cokr.xit.adds.inf.iros.web
 * fileName    : InfIrosController
 * author      : limju
 * date        : 2024-04-19
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-04-19    limju       최초 생성
 *
 * </pre>
 */
@Tag(name = "InfIrosController", description = "공공데이타포탈 Interface API - Api Interface call Test(테스트용)")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/inf/iros/v1")
public class InfIrosController {
    private final InfIrosService infIrosService;

    @Operation(summary = "의약 제품 주성분 상세 정보 조회", description = "의약 제품 주성분 상세 정보 조회")
    @PostMapping("/getDrugPrdtMcpnDtls")
    public ApiBaseResponse<IrosResponse<DrugPrdtMcpnDtl>> getDrugPrdtMcpnDtls(
        @RequestBody DrugPrdtMcpnDtlReq dto
    ) {
        return ApiBaseResponse.of(infIrosService.getDrugPrdtMcpnDtls(dto));
    }
}
