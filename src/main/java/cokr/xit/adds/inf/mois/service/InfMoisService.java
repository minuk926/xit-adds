package cokr.xit.adds.inf.mois.service;

import cokr.xit.adds.inf.mois.model.MoisExchangeRequest;

/**
 * <pre>
 * description : 
 * packageName : cokr.xit.adds.inf.mois.service
 * fileName    : InfMoisService
 * author      : limju
 * date        : 2024-05-08
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-05-08   limju       최초 생성
 *
 * </pre>
 */
public interface InfMoisService {
    void sendExchange(MoisExchangeRequest dto);
}
