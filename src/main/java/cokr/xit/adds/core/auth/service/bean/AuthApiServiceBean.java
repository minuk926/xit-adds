package cokr.xit.adds.core.auth.service.bean;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cokr.xit.adds.core.auth.dao.IAuthApiMapper;
import cokr.xit.adds.core.auth.service.IAuthApiService;
import cokr.xit.foundation.component.AbstractServiceBean;
import cokr.xit.foundation.util.CharsEncoder;
import egovframework.com.cmm.model.LoginVO;

@Service
//public class AuthApiService extends EgovAbstractServiceImpl implements IAuthApiService {
public class AuthApiServiceBean extends AbstractServiceBean implements IAuthApiService {

	@Resource
	private IAuthApiMapper mapper;

	/**
	 * 일반 로그인 처리
	 * @param vo LoginVO
	 * @return LoginVO
	 */
	@Override
	public LoginVO actionLogin(LoginVO vo) {

		// 1. 입력한 비밀번호를 암호화한다.
		// fims framework 암호화 적용
		//String enpassword = EgovFileScrty.encryptPassword(vo.getPassword(), vo.getId());
		String enpassword = new CharsEncoder().encode(vo.getPassword());
		vo.setPassword(enpassword);

		// 2. 아이디와 암호화된 비밀번호가 DB와 일치하는지 확인한다.
		LoginVO loginVO = mapper.actionLogin(vo); //loginDAO.actionLogin(vo);

		// 3. 결과를 리턴한다.
		if (loginVO != null && !loginVO.getId().equals("") && !loginVO.getPassword().equals("")) {
			return loginVO;
		} else {
			loginVO = new LoginVO();
		}

		return loginVO;
	}
}
