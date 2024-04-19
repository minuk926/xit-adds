package cokr.xit.adds.core.auth.service.bean;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cokr.xit.adds.core.auth.dao.IApiLoggingMapper;
import cokr.xit.adds.core.auth.model.LoggingDTO;
import cokr.xit.adds.core.auth.service.IApiLoggingService;
import cokr.xit.foundation.component.AbstractServiceBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * description :
 *
 * author      : limju
 * date        : 2023-05-11
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2023-05-11    limju       최초 생성
 *
 * </pre>
 */

@Service
@Slf4j
@RequiredArgsConstructor
//public class ApiLoggingService extends EgovAbstractServiceImpl implements IApiLoggingService {
public class ApiLoggingServiceBean extends AbstractServiceBean implements IApiLoggingService {
    private final IApiLoggingMapper mapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void saveApiLogging(final LoggingDTO reqDTO){
        mapper.saveApiLogging(reqDTO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void modifyApiLogging(final LoggingDTO reqDTO){
        mapper.updateApiLogging(reqDTO);
    }

    @Override
    public List<LoggingDTO> findApiLogging(final LoggingDTO reqDTO) {
        return mapper.selectApiLogging(reqDTO);
    }

    @Override
    public LoggingDTO findApiLogging() {
        return mapper.selectApiLogging();
    }
}
