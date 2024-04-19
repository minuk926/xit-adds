package cokr.xit.adds.inf.iros;

import java.util.List;

import org.apache.http.Header;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import cokr.xit.adds.inf.iros.model.DrugPrdtMcpnDtl;
import cokr.xit.adds.inf.iros.model.DrugPrdtMcpnDtlRequest;
import cokr.xit.adds.inf.iros.model.IrosResponse;
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
    private static final String url = "http://apis.data.go.kr/1471000/DrugPrdtPrmsnInfoService05/getDrugPrdtMcpnDtlInq04";
    private static final String serviceKey = "0fTkYnZU6XjaAPAp6GzKW9Q6fYq8iaoSH9wKUZwz2PBjRXGM04aUgtO3a61xYGjL8nFZn4fjLGS9XErhMSXq+w==";

    TestRestTemplate testRestTemplate = new TestRestTemplate();

    @DisplayName("의약품 성분조회 테스트")
    @Test
    public void testDrugPrdtMcpnDtl() {
        final List<Header> headers = Lists.newArrayList(new BasicHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
        final CloseableHttpClient httpClient = HttpClients.custom().setDefaultHeaders(headers).build();
        testRestTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));

        final DrugPrdtMcpnDtlRequest request = DrugPrdtMcpnDtlRequest.builder()
            .serviceKey(serviceKey)
            .pageNo(1)
            .numOfRows(10)
            .type("json")
            .prduct("중외20%포도당주사액")
            .build();
        final String urlParam = String.format(
            "?serviceKey=%s&pageNo=%d&numOfRows=%d&type=%s&Prduct=%s",
            request.serviceKey(),
            request.pageNo(),
            request.numOfRows(),
            request.type(),
            request.prduct());
        final IrosResponse<DrugPrdtMcpnDtl> irosResponse = testRestTemplate.getForObject(url + urlParam, IrosResponse.class);
        Assertions.assertThat(irosResponse).isNotNull();
        Assertions.assertThat(irosResponse.header().resultCode()).isEqualTo("00");
        Assertions.assertThat(irosResponse.header().resultMsg()).isEqualTo("NORMAL SERVICE.");
        Assertions.assertThat(irosResponse.body().pageNo()).isEqualTo("1");
        Assertions.assertThat(irosResponse.body().numOfRows()).isEqualTo("10");
        Assertions.assertThat(irosResponse.body().totalCount()).isEqualTo("1");

        //HttpEntity<DrugPrdtMcpnDtlRequest> requestEntity = new HttpEntity<>(request);
        //ResponseEntity<IrosResponse<DrugPrdtMcpnDtl>> forEntity = testRestTemplate.exchange(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<IrosResponse<DrugPrdtMcpnDtl>>() {});
        //Assertions.assertThat(forEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

    }
}
