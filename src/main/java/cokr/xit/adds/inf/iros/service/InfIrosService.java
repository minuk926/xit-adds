package cokr.xit.adds.inf.iros.service;

import cokr.xit.adds.inf.iros.model.DrugPrdtMcpnDtl;
import cokr.xit.adds.inf.iros.model.DrugPrdtMcpnDtlReq;
import cokr.xit.adds.inf.iros.model.IrosResponse;

/**
 * <pre>
 * description : 
 *
 * packageName : cokr.xit.adds.inf.iros.service
 * fileName    : InfIrosService
 * author      : limju
 * date        : 2024-04-19
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-04-19    limju       최초 생성
 *
 * </pre>
 */
public interface InfIrosService {
    IrosResponse<DrugPrdtMcpnDtl> getDrugPrdtMcpnDtls(DrugPrdtMcpnDtlReq dto);
}
