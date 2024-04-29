package cokr.xit.adds.biz.iros.web;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cokr.xit.adds.biz.iros.service.BizIrosService;
import cokr.xit.adds.core.model.ApiBaseResponse;
import cokr.xit.adds.inf.iros.model.DrugPrdtMcpnDtl;
import cokr.xit.adds.inf.iros.model.DrugPrdtMcpnDtlRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * <pre>
 * description : 
 * packageName : cokr.xit.adds.biz.iros.web
 * fileName    : BizIrosController
 * author      : limju
 * date        : 2024-04-29
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-04-29   limju       최초 생성
 *
 * </pre>
 */
@Tag(name = "BizIrosController", description = "공공데이타포탈 업무 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/biz/iros/v1")
public class BizIrosController {
    private final BizIrosService bizIrosService;

    @Operation(summary = "의약 제품 주성분 상세 정보 조회", description = "의약 제품 주성분 상세 정보 조회")
    @PostMapping("/getDrugPrdtMcpnDtls")
    public ApiBaseResponse<List<DrugPrdtMcpnDtl>> getDrugPrdtMcpnDtls(
        @RequestBody DrugPrdtMcpnDtlRequest dto
    ) {
        return ApiBaseResponse.of(bizIrosService.getDrugPrdtMcpnDtls(dto));
    }
}
