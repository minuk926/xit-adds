package cokr.xit.adds.biz.iros.service;

import java.util.List;

import cokr.xit.adds.inf.iros.model.DrugPrdtMcpnDtl;
import cokr.xit.adds.inf.iros.model.DrugPrdtMcpnDtlRequest;

/**
 * <pre>
 * description : 
 * packageName : cokr.xit.adds.biz.iros.service
 * fileName    : BizIrosService
 * author      : limju
 * date        : 2024-04-29
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-04-29   limju       최초 생성
 *
 * </pre>
 */
public interface BizIrosService {
    List<DrugPrdtMcpnDtl> getDrugPrdtMcpnDtls(DrugPrdtMcpnDtlRequest dto);
}
