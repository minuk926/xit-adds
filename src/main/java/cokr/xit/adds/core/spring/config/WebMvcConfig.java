package cokr.xit.adds.core.spring.config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.AbstractXmlHttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cokr.xit.adds.core.Constants;
import cokr.xit.adds.core.spring.filter.ReadableRequestWrapperFilter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * logging exclude path
     */
    @Value("${app.log.request.exclude-patterns:}")
    private List<String> EXCLUDE_URL_REGEXS;

    /**
     * MappingJackson2XmlHttpMessageConverter 순서를 가장 후순위로 조정
     * @param converters
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //converters.forEach(System.out::println);
        //System.out.println("--------------------------------------------");;
        reorderXmlConvertersToEnd(converters);
        //converters.forEach(System.out::println);
    }

    /**
     * MappingJackson2XmlHttpMessageConverter 를 가장 후순위로 조정하는 메소드
     * @param converters
     */
    private void reorderXmlConvertersToEnd(List<HttpMessageConverter<?>> converters) {
        List<HttpMessageConverter<?>> xml = new ArrayList<>();
        for (Iterator<HttpMessageConverter<?>> iterator =
            converters.iterator(); iterator.hasNext();) {
            HttpMessageConverter<?> converter = iterator.next();
            if ((converter instanceof AbstractXmlHttpMessageConverter)
                || (converter instanceof MappingJackson2XmlHttpMessageConverter)) {
                xml.add(converter);
                iterator.remove();
            }
        }
        converters.addAll(xml);
    }

    @Bean
    public FilterRegistrationBean readableRequestWrapperFilter() {
        ReadableRequestWrapperFilter readableRequestWrapperFilter = new ReadableRequestWrapperFilter();

        //noinspection unchecked
        FilterRegistrationBean bean = new FilterRegistrationBean(readableRequestWrapperFilter);
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        bean.addUrlPatterns(Constants.API_URL_PATTERNS);
        return bean;
    }

    @ConditionalOnProperty(value = "spring.mvc.log-request-details", havingValue = "true", matchIfMissing = false)
    @Bean
    public FilterRegistrationBean requestLoggingFilter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter(){
            @Override
            protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
                String path = request.getServletPath();
                return EXCLUDE_URL_REGEXS.stream().anyMatch(regex -> path.matches(regex));
            }
        };
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeHeaders(false);
        loggingFilter.setBeforeMessagePrefix("\n//========================== Request(Before) ================================\n");
        loggingFilter.setBeforeMessageSuffix("\n//===========================================================================");

        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        loggingFilter.setMaxPayloadLength(1024* 1024);
        loggingFilter.setAfterMessagePrefix("\n//=========================== Request(After) ================================\n");
        loggingFilter.setAfterMessageSuffix("\n//===========================================================================");

        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>(loggingFilter);
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        bean.addUrlPatterns(Constants.API_URL_PATTERNS);
        return bean;
    }
}
