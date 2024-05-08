package cokr.xit.adds.inf.iros;

import java.net.http.HttpResponse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.type.TypeReference;

import cokr.xit.adds.inf.iros.model.DrugPrdtMcpnDtl;
import cokr.xit.adds.inf.iros.model.DrugPrdtMcpnDtlReq;
import cokr.xit.adds.inf.iros.model.IrosResponse;
import cokr.xit.foundation.data.JSON;
import cokr.xit.foundation.web.WebClient;

/**
 * <pre>
 * description :
 *
 * packageName : cokr.xit.adds.inf.iros
 * fileName    : IrosRestfulTest
 * author      : limju
 * date        : 2024-03-20
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-03-20    limju       최초 생성
 *
 * </pre>
 */
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class IrosRestfulTest {
    private static final String uri = "http://apis.data.go.kr/1471000/DrugPrdtPrmsnInfoService05/getDrugPrdtMcpnDtlInq04";
    private static final String serviceKey = "0fTkYnZU6XjaAPAp6GzKW9Q6fYq8iaoSH9wKUZwz2PBjRXGM04aUgtO3a61xYGjL8nFZn4fjLGS9XErhMSXq%2Bw%3D%3D";

    @DisplayName("의약품 성분조회 테스트")
    @Test
    public void testDrugPrdtMcpnDtl() {

        final DrugPrdtMcpnDtlReq param = DrugPrdtMcpnDtlReq.builder()
            .serviceKey(serviceKey)
            .pageNo("1")
            .numOfRows("10")
            .type("json")
            .prduct("포도당주사액")
            .build();

        HttpResponse<String> rslt = new WebClient().get(request -> {
            String urlParam = String.format(
                "?serviceKey=%s&pageNo=%s&numOfRows=%s&type=%s&Prduct=%s",
                param.getServiceKey(),
                param.getPageNo(),
                param.getNumOfRows(),
                param.getType(),
                param.getPrduct()
            );
            request.header("Content-Type", "application/json");
            request.contentType(WebClient.Request.ContentType.FORM);
            request.uri(uri + urlParam);
        });

        IrosResponse<DrugPrdtMcpnDtl> parse = new JSON().parse(rslt.body(),
            new TypeReference<IrosResponse<DrugPrdtMcpnDtl>>() {
            });

        System.out.println(parse);
    }
}
