package cokr.xit.adds.biz.iros.service.bean;

import java.util.List;

import org.springframework.stereotype.Service;

import cokr.xit.adds.biz.iros.service.BizIrosService;
import cokr.xit.adds.inf.iros.model.DrugPrdtMcpnDtl;
import cokr.xit.adds.inf.iros.model.DrugPrdtMcpnDtlRequest;
import cokr.xit.adds.inf.iros.model.IrosResponse;
import cokr.xit.adds.inf.iros.service.InfIrosService;
import cokr.xit.foundation.component.AbstractServiceBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * description : 
 * packageName : cokr.xit.adds.biz.iros.service.bean
 * fileName    : BizIrosServiceBean
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
public class BizIrosServiceBean extends AbstractServiceBean implements BizIrosService {
    private final InfIrosService infIrosService;

    @Override
    public List<DrugPrdtMcpnDtl> getDrugPrdtMcpnDtls(DrugPrdtMcpnDtlRequest dto) {
        IrosResponse<DrugPrdtMcpnDtl> rslt = infIrosService.getDrugPrdtMcpnDtls(dto);

        return rslt.getResultOrThrow();
    }
}
