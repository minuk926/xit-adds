package cokr.xit.adds.inf.mois.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cokr.xit.adds.inf.mois.model.MoisExchangeRequest;
import cokr.xit.adds.inf.mois.service.InfMoisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * <pre>
 * description : 
 * packageName : cokr.xit.adds.inf.mois.web
 * fileName    : InfMoisController
 * author      : limju
 * date        : 2024-05-10
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-05-10   limju       최초 생성
 *
 * </pre>
 */
@Tag(name = "InfMoisController", description = "온나라전자결재 Interface API - Api Interface call Test(테스트용)")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/inf/mois/v1")
public class InfMoisController {
    private final InfMoisService infMoisService;

    /**
     * 온나라 전자결재 송신
     * multipart/form-data
     * @param dto
     */
    @Operation(summary = "온나라 전자결재 송신", description = "온나라 전자결재 송신")
    @PostMapping(value = "/sendExchange", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void sendExchange(final MoisExchangeRequest dto) {

        infMoisService.sendExchange(dto);
    }

    /**
     * 온나라 전자 결재 결과 수신
     * 서버 시작후 1분후 10분마다 실행
     */
    @Operation(summary = "온나라 전자 결재 결과 수신", description = "온나라 전자 결재 결과 수신")
    @PostMapping(value = "/saveResultExchange")
    //@Scheduled(fixedRate = 600000, initialDelay = 60000)
    public void saveResultExchange() {

        infMoisService.saveResultExchange();
    }
}
