package cokr.xit.adds.core.auth.dao;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import cokr.xit.foundation.component.AbstractMapper;
import egovframework.com.cmm.model.LoginVO;

/**
 * <pre>
 * description :
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
//public interface IAuthApiMapper {
public interface IAuthApiMapper extends AbstractMapper {
    LoginVO actionLogin(LoginVO vo);
    // LoginVO searchId(LoginVO vo);
    // LoginVO searchPassword(LoginVO vo);
    // void updatePassword(LoginVO vo);
}
