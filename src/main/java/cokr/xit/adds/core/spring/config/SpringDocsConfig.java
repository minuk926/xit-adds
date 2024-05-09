package cokr.xit.adds.core.spring.config;

import static org.egovframe.rte.fdl.string.EgovStringUtil.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SpringDocsConfig {
    @Value("${server.port}")
    private int SERVER_PORT;
    @Value("${server.http:0}")
    private int HTTP_PORT;
    @Value("${app.swagger.url:}")
    private String swaggerUrl;

    @Bean
    public OpenAPI openAPI(
        @Value("${springdoc.version:v1}") String version,
        @Value("${app.swagger.desc:}") String desc,
        @Value("${spring.application.name}") String name,
        @Value("${spring.profiles.active}") String active) {

        Info info = new Info()
            .title(String.format("%s : %s 서버( %s )", desc, name, active))  // 타이틀
            .version(version)           // 문서 버전
            .description("잘못된 부분이나 오류 발생 시 바로 말씀해주세요.") // 문서 설명
            .contact(new Contact()      // 연락처
                .name("관리자")
                .email("admin@xit.co.kr"));
                //.url("http://www.xerotech.co.kr/"));

        // https enabled
        List<Server> servers = new ArrayList<>();
        if(HTTP_PORT != 0){
            String httpUrl = isNotEmpty(swaggerUrl)? swaggerUrl : String.format("http://localhost:%d", HTTP_PORT);
            String httpsUrl = isNotEmpty(swaggerUrl)? swaggerUrl : String.format("https://localhost:%d", SERVER_PORT);
            servers.add(new Server().url(httpUrl).description(name + "(" + active + ")"));
            servers.add(new Server().url(httpsUrl).description(name + "(" + active + ")"));
        }else {
            String httpUrl = isNotEmpty(swaggerUrl)? swaggerUrl : String.format("http://localhost:%d", SERVER_PORT);
            servers.add(new Server().url(httpUrl).description(name + "(" + active + ")"));
        }

        // Security 스키마 설정
        SecurityScheme securityScheme = new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .in(SecurityScheme.In.HEADER)
            // .name(HttpHeaders.AUTHORIZATION);
            .name("Authorization");

        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
            // Security 인증 컴포넌트 설정
            .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
            .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
            // API 마다 Security 인증 컴포넌트 설정
            //.addSecurityItem(new SecurityRequirement().addList("JWT"))
            .security(Collections.singletonList(securityRequirement))
            .info(info)
            .servers(servers);
    }

    @Bean
    public GroupedOpenApi authentification() {
        return GroupedOpenApi.builder()
            .group("1. Common API")
            .pathsToMatch(
                "/api/auth/**",
                "/api/cmm/**"
            )
            .build();
    }

    @Bean
    public GroupedOpenApi bizApi() {
        return GroupedOpenApi.builder()
            .group("2. BIZ API interface")
            .pathsToMatch(
                "/api/biz/nims/**",
                "/api/biz/iros/**"
            )
            .build();
    }

    @Bean
    public GroupedOpenApi infApi() {
        return GroupedOpenApi.builder()
            .group("3. Interface API")
            .pathsToMatch(
                "/api/inf/nims/**",
                "/api/inf/iros/**"
            )
            .build();
    }
}
