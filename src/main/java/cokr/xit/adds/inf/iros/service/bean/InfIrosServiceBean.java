package cokr.xit.adds.inf.iros.service.bean;

import java.util.Map;

import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;

import cokr.xit.adds.core.spring.exception.ApiCustomException;
import cokr.xit.adds.core.util.ApiUtil;
import cokr.xit.adds.inf.iros.model.DrugPrdtMcpnDtl;
import cokr.xit.adds.inf.iros.model.DrugPrdtMcpnDtlReq;
import cokr.xit.adds.inf.iros.model.IrosResponse;
import cokr.xit.adds.inf.iros.service.InfIrosService;
import cokr.xit.foundation.component.AbstractServiceBean;
import cokr.xit.foundation.data.JSON;
import cokr.xit.foundation.data.XML;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * description : 
 * packageName : cokr.xit.adds.inf.iros.service.bean
 * fileName    : InfIrosServiceBean
 * author      : limju
 * date        : 2024-04-29
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-04-29   limju       최초 생성
 *
 * </pre>
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class InfIrosServiceBean extends AbstractServiceBean implements InfIrosService {
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private static JSON json = new JSON();

    @Value("${app.inf.iros.url}")
    private String irosUrl;

    @Value("${app.inf.iros.api-key}")
    private String irosApiKey;

    @Value("${app.inf.iros.api.drugPrdtMcpnDtlInq}")
    private String drugPrdtMcpnDtlInq;

    @Override
    public IrosResponse<DrugPrdtMcpnDtl> getDrugPrdtMcpnDtls(DrugPrdtMcpnDtlReq dto) {
        dto.setServiceKey(irosApiKey);
        ApiUtil.validate(dto, null, validator);

        if(isEmpty(dto.getEntrpsPrmisnNo())
            && isEmpty(dto.getPrduct())
            && isEmpty(dto.getEntrps())
            && isEmpty(dto.getBizrno())
        ) throw ApiCustomException.create("업체허가번호, 제품명, 업체명, 사업자번호 중 하나는 필수 입니다");

        String params = String.format(
            "?serviceKey=%s&pageNo=%s&numOfRows=%s&type=%s&Entrps_prmisn_no=%s&Prduct=%s&Entrps=%s&Bizrno=%s",
            dto.getServiceKey(),
            dto.getPageNo(),
            dto.getNumOfRows(),
            dto.getType(),
            dto.getEntrpsPrmisnNo(),
            dto.getPrduct(),
            dto.getEntrps(),
            dto.getBizrno());

        String rslt = ApiUtil.callIrosApi(irosUrl + drugPrdtMcpnDtlInq, params);
        IrosResponse<DrugPrdtMcpnDtl> result = null;
        try {
            result = json.parse(rslt, new TypeReference<IrosResponse<DrugPrdtMcpnDtl>>() {});
        } catch (Exception e) {

            if(!isEmpty(e.getCause())){
                //System.out.println("JsonParseException" + je.getMessage());
                gatewayErrorParsing(rslt);
            }
            throw ApiCustomException.create(e.getMessage());

        }

        return result;
    }

    /**
     * <pre>
     * Gateway Error Parsing
     *
     * <OpenAPI ServiceResponse>
     *   <cmmMsgHeader>
     *     <errMsg>SERVICE ERROR</errMsg>
     *     <returnAuthMsg>SERVICE_KEY_IS_NOT_REGISTERED_ERROR</returnAuthMsg>
     *     <returnReasonCode>30</returnReasonCode>
     *   </cmmMsgHeader>
     * <OpenAPI ServiceResponse>
     * @param rslt
     * </pre>
     */
    private static void gatewayErrorParsing(String rslt) {
        XML xml = new XML();
        Map<String, Map<String, String>> map = xml.parse(rslt, Map.class);
        Map<String, String> hMap = map.get("cmmMsgHeader");
        if(!isEmpty(hMap)) {
            throw ApiCustomException.create(Integer.valueOf(hMap.get("returnReasonCode")),
                String.format("%s[%s]", hMap.get("errMsg"), hMap.get("returnAuthMsg")));
        }
    }
}
