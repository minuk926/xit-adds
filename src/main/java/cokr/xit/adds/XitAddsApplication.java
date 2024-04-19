package cokr.xit.adds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;

import cokr.xit.adds.core.spring.config.CustomBeanNameGenerator;
import cokr.xit.foundation.boot.CommonConfig;
import cokr.xit.foundation.boot.DatasourceConfig;
import cokr.xit.foundation.boot.MvcConfig;
import cokr.xit.foundation.boot.ServletConfig;
import cokr.xit.foundation.boot.TransactionConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = {"egovframework", "cokr.xit"})
@ComponentScan(
	nameGenerator = CustomBeanNameGenerator.class,
	basePackages = {"egovframework", "cokr.xit"}
)
@ImportAutoConfiguration({
	CommonConfig.class,
	ServletConfig.class,
	MvcConfig.class,
	DatasourceConfig.class,
	TransactionConfig.class
})
public class XitAddsApplication {

	static final List<String> basePackages = new ArrayList<>(
		Arrays.asList("egovframework", "kr.xit")
	);

	public static void main(String[] args) {
		final String line = "====================================================================";
		log.info(line);
		log.info("====    XitAddsApplication start :: active profiles - {}    ====", System.getProperty("spring.profiles.active"));
		if(Objects.isNull(System.getProperty("spring.profiles.active"))) {

			log.error(">>>>>>>>>>>>>>        Undefined start VM option       <<<<<<<<<<<<<<");
			log.error(">>>>>>>>>>>>>> -Dspring.profiles.active=local|dev|prd <<<<<<<<<<<<<<");
			log.error("============== XitAddsApplication start fail ===============");
			log.error(line);
			System.exit(-1);
		}
		log.info(line);

		// beanName Generator 등록 : API v1, v2 등으로 분류하는 경우
		// Bean 이름 식별시 풀패키지 명으로 식별 하도록 함
		final CustomBeanNameGenerator beanNameGenerator = new CustomBeanNameGenerator();
		beanNameGenerator.addBasePackages(basePackages);

		final SpringApplicationBuilder applicationBuilder = new SpringApplicationBuilder(XitAddsApplication.class);
		applicationBuilder.beanNameGenerator(beanNameGenerator);

		final SpringApplication application = applicationBuilder.build();
		application.setBannerMode(Banner.Mode.OFF);
		application.setLogStartupInfo(false);

		//TODO : 이벤트 실행 시점이 Application context 실행 이전인 경우 리스너 추가
		//PID(Process ID 작성)
		application.addListeners(new ApplicationPidFileWriter()) ;
		application.run(args);

		log.info("=========================================================================================");
		log.info("==========      XitAddsApplication load Complete :: active profiles - {}     ==========", System.getProperty("spring.profiles.active"));
		log.info("=========================================================================================");
	}

}
