package cokr.xit.adds.core.auth.dao;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import cokr.xit.adds.core.auth.model.LoggingDTO;
import cokr.xit.foundation.component.AbstractMapper;

/**
 * <pre>
 * description : API db logging
 *
 * author      : limju
 * date        : 2024-04-08
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-04-08    limju       최초 생성
 *
 * </pre>
 */
@Mapper
//public interface IApiLoggingMapper {
public interface IApiLoggingMapper extends AbstractMapper {

    List<LoggingDTO> selectApiLogging(final LoggingDTO reqDTO);
    LoggingDTO selectApiLogging();

    void saveApiLogging(final LoggingDTO reqDTO);
    void updateApiLogging(final LoggingDTO reqDTO);
}
