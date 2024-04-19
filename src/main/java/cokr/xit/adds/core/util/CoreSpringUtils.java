package cokr.xit.adds.core.util;

import org.apache.logging.log4j.core.config.properties.PropertiesConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;

import com.fasterxml.jackson.databind.ObjectMapper;

import cokr.xit.adds.core.auth.jwt.JwtTokenProvider;
import cokr.xit.adds.core.auth.jwt.JwtVerification;
import cokr.xit.adds.core.spring.config.ApplicationContextProvider;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * description : Get Bean Object
 *               Filter / Interceptor 등에서 Bean 사용시 필요
 *               (Bean으로 등록되는 클래스 내에서만 @Autowired / @Resource 등이 동작)
 * author      : julim
 * date        : 2023-04-28
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2023-04-28    julim       최초 생성
 *
 * </pre>
 * @see
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CoreSpringUtils {
	public static ApplicationContext getApplicationContext() {
		return ApplicationContextProvider.getApplicationContext();
	}
	
	public static boolean containsBean(String beanName) {
		return getApplicationContext().containsBean(beanName);
	}
	
	public static Object getBean(String beanName) {
		return getApplicationContext().getBean(beanName);
	}
	
	public static Object getBean(Class<?> clazz) {
		return getApplicationContext().getBean(clazz);
	}

	/**
	 *
	 * @return MessageSourceAccessor
	 */
	public static MessageUtil getMessageUtil(){
		return (MessageUtil)getBean(MessageUtil.class);
	}

	/**
	 *
	 * @return MessageSourceAccessor
	 */
	public static MessageSource getMessageSource(){
		return (MessageSource)getBean(MessageSource.class);
	}

	/**
	 *
	 * @return JwtVerification
	 */
	public static JwtVerification getJwtVerification(){
		return (JwtVerification)getBean(JwtVerification.class);
	}

	/**
	 *
	 * @return EgovJwtTokenUtil
	 */
	public static JwtTokenProvider getJwtTokenProvider(){
		return (JwtTokenProvider)getBean(JwtTokenProvider.class);
	}

	/**
	 *
	 * @return PropertiesConfiguration
	 */
	public static PropertiesConfiguration getPropertiesConfiguration(){
		return (PropertiesConfiguration)getBean(PropertiesConfiguration.class);
	}

	public static Environment getEnvironment(){
		return (Environment)getBean(Environment.class);
	}

	public static ObjectMapper getObjectMapper(){
		return (ObjectMapper)getBean(ObjectMapper.class);
	}
}
